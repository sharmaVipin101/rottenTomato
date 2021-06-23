package com.example.rottentomato.Model

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.example.rottentomato.View.MovieAdapter

class NetworkCall(private val context: Context){

    private var category:String = "top_rated"
    private var API_KEY = "15cd9477eaf83ac445e728f78ba48196"
    private var url = "https://api.themoviedb.org/3/movie/"


    var isLoading = MutableLiveData<Boolean>()
    var movieList:MutableLiveData<ArrayList<Movie>> = MutableLiveData()

    var toprated: ArrayList<Movie> = ArrayList()
    var upcoming: ArrayList<Movie> = ArrayList()
    var popular: ArrayList<Movie> = ArrayList()
    var playing: ArrayList<Movie> = ArrayList()


    fun nextPage(page: Int){
        fetchDataFromServer(category,page)
    }

    private fun getURL(genre: String,page:Int):String{

        val URL = this.url+genre+"?api_key="+this.API_KEY+"&page="+page
        return URL
    }

    fun fetchDataFromServer(genre: String,page:Int){

        this.isLoading.value = true

        val url = getURL(genre,page)//


        val list = ArrayList<Movie>()

        val jsonObjectArray = JsonObjectRequest(
            Request.Method.GET,url,null,
            {

                val movieJsonArray = it.getJSONArray("results")

                for(i in 0 until movieJsonArray.length())
                {
                    val obj = movieJsonArray.getJSONObject(i)

                    val title = obj.getString("original_title")
                    val poster = "https://image.tmdb.org/t/p/original"+obj.getString("backdrop_path")
                    val thumbnail = "https://image.tmdb.org/t/p/original"+obj.getString("poster_path")
//                    val releaseDate = obj.getString("release_date")
                    val rating = obj.getString("vote_average").toFloat()
                    val overview = obj.getString("overview")

                    val tmp =  Movie(title,poster,thumbnail,rating,overview)

                    list.add(tmp)
                }

                this.isLoading.value = false

                if(genre=="top_rated"){
                    toprated.addAll(list)
                    updateMovieList(toprated)
                }else if(genre=="upcoming"){
                    upcoming.addAll(list)
                    updateMovieList(upcoming)
                }else if(genre=="popular"){
                    popular.addAll(list)
                    updateMovieList(popular)
                }else if(genre=="now_playing"){
                    playing.addAll(list)
                    updateMovieList(playing)
                }


            }, {
                error-> Toast.makeText(context,"${error.message}",Toast.LENGTH_LONG).show()
            })

        VolleySingleton.getInstance(context).addToRequestQueue(jsonObjectArray)
    }

    private fun updateMovieList(list:ArrayList<Movie>){
        movieList.value = list
    }


}
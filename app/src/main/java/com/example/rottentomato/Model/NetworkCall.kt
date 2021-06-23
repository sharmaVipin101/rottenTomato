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

    var movieList1: ArrayList<Movie> = ArrayList()//top rated
    var movieList2: ArrayList<Movie> = ArrayList()//upcoming
    var movieList3: ArrayList<Movie> = ArrayList()//popular
    var movieList4: ArrayList<Movie> = ArrayList()//now playing


    fun nextPage(page: Int){
        fetchDataFromServer(category,page)
    }

    private fun getURL(genre: String,page:Int):String{

        val URL = this.url+genre+"?api_key="+this.API_KEY+"&page="+page
        return URL
    }

    fun fetchDataFromServer(genre: String,page:Int){

        this.isLoading.value = true

        var URL = getURL(genre,page)//


        val list = ArrayList<Movie>()

        val jsonObjectArray = JsonObjectRequest(
            Request.Method.GET,URL,null,
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
                    movieList1.addAll(list)
                    getMovieList(movieList1)
                }else if(genre=="upcoming"){
                    movieList2.addAll(list)
                    getMovieList(movieList2)
                }else if(genre=="popular"){
                    movieList3.addAll(list)
                    getMovieList(movieList3)
                }else if(genre=="now_playing"){
                    movieList4.addAll(list)
                    getMovieList(movieList4)
                }


            }, {
                error-> Toast.makeText(context,"${error.message}",Toast.LENGTH_LONG).show()
            })

        VolleySingleton.getInstance(context).addToRequestQueue(jsonObjectArray)
    }

    private fun getMovieList(list:ArrayList<Movie>){
        movieList.value = list
    }


}

//logical part should be in repository
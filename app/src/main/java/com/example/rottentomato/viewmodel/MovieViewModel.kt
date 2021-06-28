package com.example.movieexplorer.viewmodel

import android.app.Application
import android.content.Context
import android.util.Log
import android.widget.ProgressBar
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide
import com.example.rottentomato.Model.Movie
import com.example.rottentomato.Model.NetworkCall
import com.example.rottentomato.View.MovieAdapter
import com.example.rottentomato.View.MovieViewHolder
import java.text.FieldPosition


class MovieViewModel(application: Application) :AndroidViewModel(application){

   private val networkCall:NetworkCall = NetworkCall(application.applicationContext)

    lateinit var MovieList: LiveData<ArrayList<Movie>>;

    lateinit var isLoading:LiveData<Boolean>;

    fun fetchDataFromServer(genre:String,page:Int){

        networkCall.fetchDataFromServer(genre,page)
        initializeVariables()
    }

    private fun initializeVariables(){
        MovieList = networkCall.movieList
        isLoading = networkCall.isLoading
    }

    fun nextPage(page: Int){
        networkCall.nextPage(page)
    }

    fun getList():LiveData<ArrayList<Movie>>{
        return MovieList
    }

    fun getProgress():LiveData<Boolean>{
        return isLoading
    }
}
//
/////////////////
//mutable and live
//mvp vs mvvm
//
//----------
// m v p

// model view presenter

//customer = view
// presenter = waiter
//model =  chef
package com.example.movieexplorer.viewmodel

import android.app.Application
import android.content.Context
import android.widget.ProgressBar
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rottentomato.Model.Movie
import com.example.rottentomato.Model.NetworkCall
import com.example.rottentomato.View.MovieAdapter


class MovieViewModel(application: Application) :AndroidViewModel(application){

   private val networkCall:NetworkCall = NetworkCall(application.applicationContext)

    val MovieList: LiveData<ArrayList<Movie>> = networkCall.movieList

    val isLoading:MutableLiveData<Boolean> = networkCall.isLoading

    fun fetchDataFromServer(genre:String,page:Int){
        networkCall.fetchDataFromServer(genre,page)
    }

    fun nextPage(page: Int){
        networkCall.nextPage(page)
    }


}
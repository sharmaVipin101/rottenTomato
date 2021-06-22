package com.example.rottentomato.View

import com.example.rottentomato.Model.Movie

interface ClickListener {
    fun onMovieClick(movie: Movie)
}
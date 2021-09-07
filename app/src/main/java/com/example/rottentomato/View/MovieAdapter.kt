package com.example.rottentomato.View

import android.app.Application
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieexplorer.viewmodel.MovieViewModel
import com.example.rottentomato.MainActivity
import com.example.rottentomato.Model.Movie
import com.example.rottentomato.R


class MovieAdapter(private val listener:ClickListener):RecyclerView.Adapter<MovieViewHolder>() {

    private val MovieList = ArrayList<Movie>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_view,parent,false)
        val viewHolder = MovieViewHolder(view)

        view.setOnClickListener {
            listener.onMovieClick(MovieList[viewHolder.adapterPosition])
        }

        return viewHolder

    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {

//        val curr = MovieList[position]
//
//        holder.rating.text = curr.rating.toString()
//        Glide.with(holder.itemView.context).load(curr.thumbnail).into(holder.thumbnail)

        MovieViewHolder(holder.itemView).setTitle(holder,MovieList[position].title.toString())
        MovieViewHolder(holder.itemView).setRating(holder,MovieList[position].rating.toString())
        MovieViewHolder(holder.itemView).setImage(holder,MovieList[position].thumbnail.toString())
    }

    override fun getItemCount(): Int {
        return MovieList.size
    }

    fun updateList(list:ArrayList<Movie>){
        MovieList.clear()
        MovieList.addAll(list)
        notifyDataSetChanged()
    }

}

class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),MovieRowView {

    var thumbnail: ImageView = itemView.findViewById(R.id.thumbnail)
    var title: TextView = itemView.findViewById(R.id.title)
    var rating: TextView = itemView.findViewById(R.id.rating)

    override fun setTitle(holder: MovieViewHolder,title: String) {
        holder.title.text = title
    }

    override fun setRating(holder: MovieViewHolder,rating: String) {
        holder.rating.text = "${rating}/10"
    }

    override fun setImage(holder: MovieViewHolder,url: String) {
        Glide.with(holder.itemView.context).load(url).into(holder.thumbnail)
    }
}

interface MovieRowView{

    fun setTitle(movieViewHolder: MovieViewHolder,title:String)
    fun setRating(movieViewHolder: MovieViewHolder,rating:String)
    fun setImage(movieViewHolder: MovieViewHolder,url:String)

}
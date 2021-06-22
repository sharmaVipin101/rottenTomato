package com.example.rottentomato.View

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.rottentomato.Model.Movie
import com.example.rottentomato.R


class MovieAdapter(private val listener:ClickListener):RecyclerView.Adapter<MovieViewHolder>() {

    private var MovieList = ArrayList<Movie>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_view,parent,false)
        val viewHolder = MovieViewHolder(view)

        view.setOnClickListener {
            listener.onMovieClick(MovieList[viewHolder.adapterPosition])
        }

        return viewHolder

    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {

        val curr = MovieList[position]
        holder.title.text = curr.title
        holder.rating.text = curr.rating.toString()
        Glide.with(holder.itemView.context).load(curr.thumbnail).into(holder.thumbnail)

        //logic part should be done by viewholder
        //
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



class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val thumbnail: ImageView = itemView.findViewById(R.id.thumbnail)
    val title: TextView = itemView.findViewById(R.id.title)
    var rating: TextView = itemView.findViewById(R.id.rating)
}
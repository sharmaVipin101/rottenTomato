package com.example.rottentomato.View

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieexplorer.viewmodel.MovieViewModel
import com.example.rottentomato.Model.Movie
import com.example.rottentomato.R
import com.google.android.material.bottomsheet.BottomSheetDialog

class TopRatedFrag : Fragment(), ClickListener {

    lateinit var viewModel: MovieViewModel
    lateinit var adapter: MovieAdapter
    lateinit var recyclerView: RecyclerView
    lateinit var progressBar: ProgressBar
    lateinit var manager: LinearLayoutManager
    var isLoading = false
    var category = "top_rated"
    var page = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_top_rated, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        progressBar = view.findViewById(R.id.progressbar3)
        recyclerView = view.findViewById(R.id.recyclerView3)
        adapter = MovieAdapter(this)

        manager = LinearLayoutManager(activity)
        recyclerView.layoutManager = manager
        recyclerView.adapter = adapter

        val factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return activity?.let { MovieViewModel(it.application) } as T
            }
        }

        viewModel = ViewModelProvider(this, factory).get(MovieViewModel::class.java)

        viewModel.fetchDataFromServer(category,page)

        activity?.let {
            viewModel.MovieList.observe(
                it, { list ->
                    list?.let{
                        adapter.updateList(it)

                    }

                })
        }

        activity?.let {
            viewModel.isLoading.observe(
                it, {
                    if(it){
                        progressBar.visibility = View.VISIBLE
                    }else{
                        progressBar.visibility = View.GONE
                    }
                })
        }

            recyclerView.addOnScrollListener(object: RecyclerView.OnScrollListener(){

                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)

                    if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                        isLoading = true
                    }
                }//while scrolling

                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    if(isLoading && manager.itemCount == manager.childCount + manager.findFirstVisibleItemPosition()){
                        isLoading = false
                        page++
                        viewModel.nextPage(page)

                    }
                }//while scrolling done
            })

    }

    override fun onMovieClick(movie: Movie) {

        var dia = activity?.let { BottomSheetDialog(it) }

        var view = layoutInflater.inflate(R.layout.bottomsheet,null,false)

        view.findViewById<TextView>(R.id.releaseDate).text = "Release Date: ${movie.release_Date}"
        view.findViewById<TextView>(R.id.overview).text = movie.overview
        activity?.let { Glide.with(it).load(movie.poster).into(view.findViewById(R.id.poster)) }

        if (dia != null) {
            dia.setContentView(view)
            dia.show()
        }

    }
}
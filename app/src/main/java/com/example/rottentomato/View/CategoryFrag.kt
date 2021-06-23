package com.example.rottentomato.View

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieexplorer.viewmodel.MovieViewModel
import com.example.rottentomato.Model.Movie
import com.example.rottentomato.R
import com.example.rottentomato.databinding.BottomsheetBinding
import com.example.rottentomato.databinding.FragmentCategoryBinding
import com.example.rottentomato.databinding.ItemViewBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class CategoryFrag(private val category:String) : Fragment(), ClickListener {


    private lateinit var binding: FragmentCategoryBinding
    private lateinit var bindingBottomSheet:BottomsheetBinding
    lateinit var viewModel: MovieViewModel
    lateinit var adapter: MovieAdapter
    lateinit var manager: LinearLayoutManager
    var isLoading = false
    var page = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_category, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentCategoryBinding.bind(view)

        adapter = MovieAdapter(this)
        manager = LinearLayoutManager(activity)
        binding.recyclerView.layoutManager = manager
        binding.recyclerView.adapter = adapter


        setupViewModel()
        callToGetData()
        observeData()
        handlePagination()

    }

    private fun setupViewModel(){

        val factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return activity?.let { MovieViewModel(it.application) } as T
            }
        }

        viewModel = ViewModelProvider(this, factory).get(MovieViewModel::class.java)

    }

    private fun callToGetData(){
        viewModel.fetchDataFromServer(category,page)
    }

    private fun observeData(){

        activity?.let {
            viewModel.MovieList.observe(
                it, { list ->
                    list?.let {
                        adapter.updateList(it)
                    }

                })

        }
        activity?.let {
            viewModel.isLoading.observe(
                it, {
                    if(it) binding.progressbar.visibility = View.VISIBLE else binding.progressbar.visibility = View.GONE
                })
        }

    }

    private fun handlePagination(){

        binding.recyclerView.addOnScrollListener(object: RecyclerView.OnScrollListener(){

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

        val dialog = activity?.let { BottomSheetDialog(it) }

        val view = layoutInflater.inflate(R.layout.bottomsheet,null,false)

        bindingBottomSheet = BottomsheetBinding.bind(view)

        bindingBottomSheet.releaseDate.text = "Title: ${movie.title}"
        bindingBottomSheet.overview.text = movie.overview
        activity?.let { Glide.with(it).load(movie.poster).into(bindingBottomSheet.poster) }

        if (dialog != null) {
            dialog.setContentView(view)
            dialog.show()
        }else Toast.makeText(activity,"Error displaying data",Toast.LENGTH_SHORT).show()

    }

}
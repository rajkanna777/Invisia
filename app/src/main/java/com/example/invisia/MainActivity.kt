package com.example.invisia

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.invisia.databinding.ActivityMainBinding
import com.example.invisia.model.Movie
import com.example.invisia.ui.adapter.MovieAdapter
import com.example.invisia.viewmodel.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), MovieAdapter.OnMovieItemClickListener {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MovieViewModel by viewModels()
    private lateinit var movieAdapter: MovieAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        lifecycle.addObserver(viewModel)
        setupRecyclerView()
        updateUI()
        onClickListener()
    }

    private fun onClickListener() {
        binding.imgFavorite.setOnClickListener {
            startActivity(Intent(this,FavoritesActivity::class.java))
        }
    }

    private fun updateUI() {
        lifecycleScope.launch {
            viewModel.movieList.collect {
                movieAdapter.setMovies(it)
            }
        }

    }
    private fun setupRecyclerView() {
        movieAdapter = MovieAdapter(this, false)
        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(this@MainActivity, 2)
            adapter = movieAdapter
        }
        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as GridLayoutManager
                val totalItemCount = layoutManager.itemCount
                val lastVisibleItem = layoutManager.findLastVisibleItemPosition()

                if (dy > 0) {
                    if (lastVisibleItem == totalItemCount - 1) {
                         viewModel.loadNextPage()
                    }
                }
            }
        })
    }

    override fun onItemClick(movie: Movie) {

    }
    override fun onFavoriteClick(movie: Movie) {
        lifecycleScope.launch {
            movie.isFavorite = !movie.isFavorite
            viewModel.updateMovie(movie)
        }
    }
}
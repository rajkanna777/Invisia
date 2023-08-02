package com.example.invisia

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.invisia.databinding.ActivityMainBinding
import com.example.invisia.model.Movie
import com.example.invisia.ui.adapter.MovieAdapter
import com.example.invisia.viewmodel.FavoritesListViewModel
import com.example.invisia.viewmodel.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoritesActivity : AppCompatActivity(), MovieAdapter.OnMovieItemClickListener {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: FavoritesListViewModel by viewModels()
    private lateinit var movieAdapter: MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupRecyclerView()
        updateUI()
    }

    private fun updateUI() {
        binding.imgBack.visibility = View.VISIBLE
        binding.imgFavorite.visibility = View.GONE
        binding.title.text = resources.getString(R.string.fav_list)
        lifecycleScope.launch {
            viewModel.movieList.collect {
                movieAdapter.setMovies(it)
            }
        }
        binding.imgBack.setOnClickListener { onBackPressed() }
    }

    private fun setupRecyclerView() {
        movieAdapter = MovieAdapter(this, false)
        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(this@FavoritesActivity, 2)
            adapter = movieAdapter
        }

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
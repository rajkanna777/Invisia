package com.example.invisia.viewmodel

import androidx.lifecycle.*
import com.example.invisia.model.Movie
import com.example.invisia.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(private val repository: MovieRepository) : ViewModel(),
    LifecycleObserver {

    private val _movieList = MutableStateFlow<List<Movie>>(emptyList())
    private val result = mutableListOf<Movie>()
    val movieList: StateFlow<List<Movie>> get() = _movieList
    private val pageSize = 10
    private var currentPage = 0
    private var isPageCompleted = false

    init {
        loadPopularMovies()
    }

    private fun loadPopularMovies() {
        viewModelScope.launch {
            val movies = repository.getPopularMovies()
            movies.let {
                repository.insertMovieMovie(it.movies.take(40))
                if (!isPageCompleted) pagination()
            }
        }
    }

    private fun pagination() {
        val offset = currentPage * pageSize
        viewModelScope.launch {
            repository.getMoviesWithPagination(pageSize, offset).collect {
                if (!isPageCompleted) {
                    if (it.isNotEmpty()) {
                        result.addAll(it)
                        _movieList.emit(result)
                    } else {
                        isPageCompleted = true
                    }
                }
            }
        }
    }


    fun updateMovie(movie: Movie) {
        viewModelScope.launch {
            repository.updateMovie(movie)
            _movieList.emit(emptyList())
            result.forEachIndexed { index, item ->
                if (item.id == movie.id) {
                    result.get(index).copy(isFavorite = movie.isFavorite)
                }
            }
            _movieList.emit(result)
        }
    }

    fun loadNextPage() {
        currentPage++
        if (!isPageCompleted) pagination()
    }


}

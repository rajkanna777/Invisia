package com.example.invisia.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.invisia.model.Movie
import com.example.invisia.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesListViewModel @Inject constructor(private val repository: MovieRepository) :
    ViewModel() {

    private val _movieList = MutableStateFlow<List<Movie>>(emptyList())
    val movieList: StateFlow<List<Movie>> get() = _movieList


    init {
        loadFavoritesMovies()
    }

    private fun loadFavoritesMovies() {
        viewModelScope.launch {
            repository.getFavoriteMovies().collect {
                _movieList.emit(it)
            }
        }
    }

    fun updateMovie(movie: Movie) {
        viewModelScope.launch {
            repository.updateMovie(movie)
            _movieList.emit(emptyList())
            repository.getFavoriteMovies().collect {
                _movieList.emit(it)
            }
        }
    }


}

package com.example.invisia.repository

import com.example.invisia.api.MovieApiService
import com.example.invisia.db.MovieDao
import com.example.invisia.model.Movie
import com.example.invisia.model.MovieResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val movieApiService: MovieApiService,
    private val movieDao: MovieDao
) {
    suspend fun getPopularMovies(): MovieResponse {
        return movieApiService.getPopularMovies("69d66957eebff9666ea46bd464773cf0")
    }

    fun getAllMovies(): Flow<List<Movie>> {
        return movieDao.getAllMovies()
    }

    suspend fun updateMovie(movie: Movie) {
        movieDao.updateMovie(movie)
    }

    suspend fun insertMovieMovie(movies: List<Movie>) {
        movieDao.insertMovies(movies)
    }

    suspend fun getMoviesWithPagination(pageSize: Int=0, offset: Int=0): Flow<List<Movie>>{
         return movieDao.getMoviesWithPagination(pageSize,offset)
    }

    fun getFavoriteMovies(): Flow<List<Movie>> {
        return movieDao.getFavoriteMovies()
    }

}
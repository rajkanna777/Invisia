package com.example.invisia.db

import androidx.room.*
import com.example.invisia.model.Movie
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMovies(movies: List<Movie>)

    @Query("SELECT * FROM movies")
    fun getAllMovies(): Flow<List<Movie>>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateMovie(movie: Movie)


    @Query("SELECT * FROM movies ORDER BY id LIMIT :pageSize OFFSET :offset")
     fun getMoviesWithPagination(pageSize: Int, offset: Int): Flow<List<Movie>>


    @Query("SELECT * FROM movies WHERE isFavorite = 1 ORDER BY id DESC")
    fun getFavoriteMovies(): Flow<List<Movie>>
}
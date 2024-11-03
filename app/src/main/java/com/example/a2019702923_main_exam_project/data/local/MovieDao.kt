package com.example.a2019702923_main_exam_project.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
/*
Bokang Ntovana
Main Exam Project
 */

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertMovieList(movieList: List<MovieEntity>)

    @Query("SELECT * FROM MovieEntity WHERE id = :id")
    suspend fun getMovieById(id: Int): MovieEntity?

    @Query("SELECT * FROM MovieEntity WHERE category = :category")
    suspend fun getMovieListByCategory(category: String): List<MovieEntity>

    @Query("SELECT * FROM MovieEntity WHERE title LIKE '%' || :query || '%'")
    suspend fun searchMovies(query: String): List<MovieEntity>

    @Query("SELECT * FROM MovieEntity WHERE isFavorite = 1")
    suspend fun getFavoriteMovies(): List<MovieEntity>

    @Update
    suspend fun updateMovie(movie: MovieEntity)
}

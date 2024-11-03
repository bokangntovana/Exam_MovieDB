package com.example.a2019702923_main_exam_project.domain.repository

import com.example.a2019702923_main_exam_project.data.local.MovieEntity
import com.example.a2019702923_main_exam_project.domain.model.Movie
import com.example.a2019702923_main_exam_project.util.Resource
import kotlinx.coroutines.flow.Flow

/*
Bokang Ntovana
Main Exam Project
 */
interface MovieListRepository {
    suspend fun getMovieList(
        forceFetchFromRemote: Boolean,
        category: String,
        page: Int
    ): Flow<Resource<List<Movie>>>

    suspend fun getMovie(id: Int): Flow<Resource<Movie>>

    suspend fun searchMovies(query: String): Flow<Resource<List<Movie>>>

    suspend fun getFavoriteMovies(): Flow<Resource<List<Movie>>>

    suspend fun updateMovie(movie: MovieEntity)
}



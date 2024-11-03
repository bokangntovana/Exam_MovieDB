package com.example.a2019702923_main_exam_project.data.mappers

import com.example.a2019702923_main_exam_project.data.local.MovieEntity
import com.example.a2019702923_main_exam_project.data.remote.MovieApi
import com.example.a2019702923_main_exam_project.data.remote.respond.MovieDto
import com.example.a2019702923_main_exam_project.domain.model.Movie
/*
Bokang Ntovana
Main Exam Project
 */

fun MovieDto.toMovieEntity(
    category: String
): MovieEntity {
    return MovieEntity(
        adult = adult ?: false,
        backdrop_path = backdrop_path ?: "",
        original_language = original_language ?: "",
        overview = overview ?: "",
        poster_path = poster_path?.let { MovieApi.IMAGE_BASE_URL + it } ?: "", // Add base URL here
        release_date = release_date ?: "",
        title = title ?: "",
        vote_average = vote_average ?: 0.0,
        popularity = popularity ?: 0.0,
        vote_count = vote_count ?: 0,
        id = id ?: -1,
        original_title = original_title ?: "",
        video = video ?: false,

        category = category,

        genre_ids = try {
            genre_ids?.joinToString(",") ?: "-1,-2"
        } catch (e: Exception) {
            "-1,-2"
        }
    )
}

// Extension function to convert Movie to MovieEntity
fun Movie.toMovieEntity(): MovieEntity {
    return MovieEntity(
        adult = this.adult,
        backdrop_path = this.backdrop_path,
        genre_ids = this.genre_ids.joinToString(","),
        original_language = this.original_language,
        original_title = this.original_title,
        overview = this.overview,
        popularity = this.popularity,
        poster_path = this.poster_path,
        release_date = this.release_date,
        title = this.title,
        video = this.video,
        vote_average = this.vote_average,
        vote_count = this.vote_count,
        id = this.id,
        category = this.category,
        isFavorite = this.isFavorite // Ensure to include the isFavorite field
    )
}


fun MovieEntity.toMovie(
    category: String
): Movie {
    return Movie(
        backdrop_path = backdrop_path,
        original_language = original_language,
        overview = overview,
        poster_path = poster_path, // Already has full URL
        release_date = release_date,
        title = title,
        vote_average = vote_average,
        popularity = popularity,
        vote_count = vote_count,
        video = video,
        id = id,
        adult = adult,
        original_title = original_title,

        category = category,

        genre_ids = try {
            genre_ids.split(",").map { it.toInt() }
        } catch (e: Exception) {
            listOf(-1, -2)
        },
        isFavorite = isFavorite // Added for favorite movies
    )
}


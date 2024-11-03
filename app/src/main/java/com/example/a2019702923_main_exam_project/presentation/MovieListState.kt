package com.example.a2019702923_main_exam_project.presentation

import com.example.a2019702923_main_exam_project.domain.model.Movie
/*
Bokang Ntovana
Main Exam Project
 */
data class MovieListState(
    val isLoading: Boolean = false,
    val popularMovieListPage: Int = 1,
    val upcomingMovieListPage: Int = 1,
    val isCurrentPopularScreen: Boolean = true,
    val isCurrentUpcomingScreen: Boolean = false,
    val isCurrentFavouritesScreen: Boolean = false,
    val isCurrentSearchScreen: Boolean = false,
    val isMapScreen: Boolean = false,
    val popularMovieList: List<Movie> = emptyList(),
    val upcomingMovieList: List<Movie> = emptyList(),
    val searchResults: List<Movie> = emptyList(),
    val favoriteMovies: List<Movie> = emptyList()
)


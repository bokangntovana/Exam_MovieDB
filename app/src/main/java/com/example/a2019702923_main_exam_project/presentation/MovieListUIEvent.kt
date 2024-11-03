package com.example.a2019702923_main_exam_project.presentation

import com.example.a2019702923_main_exam_project.data.local.MovieEntity
import com.example.a2019702923_main_exam_project.domain.model.Movie

/*
Bokang Ntovana
Main Exam Project
 */
sealed interface MovieListUiEvent {
    data class Paginate(val category: String) : MovieListUiEvent
    object Navigate : MovieListUiEvent
    data class Search(val query: String) : MovieListUiEvent
    object NavigateToFavorite : MovieListUiEvent
    data class ToggleFavorite(val movie: Movie) : MovieListUiEvent
}


package com.example.a2019702923_main_exam_project.Details

import com.example.a2019702923_main_exam_project.domain.model.Movie

/*
Bokang Ntovana
Main Exam Project
 */
data class DetailsState(
    val isLoading: Boolean = false,
    val movie: Movie? = null
)

package com.example.a2019702923_main_exam_project.data.remote.respond
/*
Bokang Ntovana
Main Exam Project
 */
data class MovieListDto(
    val page: Int,
    val results: List<MovieDto>,
    val total_pages: Int,
    val total_results: Int
)
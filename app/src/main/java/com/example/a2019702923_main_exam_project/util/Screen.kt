package com.example.a2019702923_main_exam_project.util
/*
Bokang Ntovana
Main Exam Project
 */
sealed class Screen(val rout: String) {
    object Home : Screen("main")
    object PopularMovieList : Screen("popularMovie")
    object UpcomingMovieList : Screen("upcomingMovie")
    object Details : Screen("details")
    object Search : Screen("search")
    object Favorites: Screen("favorites")
    object Map : Screen("map")
}
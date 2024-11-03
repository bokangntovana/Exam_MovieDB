package com.example.a2019702923_main_exam_project.di


import com.example.a2019702923_main_exam_project.data.repository.MovieListRepositoryImpl
import com.example.a2019702923_main_exam_project.domain.repository.MovieListRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/*
Bokang Ntovana
Main Exam Project
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindMovieListRepository(
        movieListRepositoryImpl: MovieListRepositoryImpl
    ): MovieListRepository

}
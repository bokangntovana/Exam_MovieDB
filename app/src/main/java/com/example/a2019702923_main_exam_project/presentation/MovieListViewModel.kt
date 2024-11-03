package com.example.a2019702923_main_exam_project.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a2019702923_main_exam_project.data.local.MovieEntity
import com.example.a2019702923_main_exam_project.data.mappers.toMovie
import com.example.a2019702923_main_exam_project.data.mappers.toMovieEntity
import com.example.a2019702923_main_exam_project.domain.model.Movie
import com.example.a2019702923_main_exam_project.domain.repository.MovieListRepository
import com.example.a2019702923_main_exam_project.util.Category
import com.example.a2019702923_main_exam_project.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/*
Bokang Ntovana
Main Exam Project
 */
@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val movieListRepository: MovieListRepository
) : ViewModel() {

    private var _movieListState = MutableStateFlow(MovieListState())
    val movieListState: StateFlow<MovieListState> = _movieListState

    init {
        getPopularMovieList(false)
        getUpcomingMovieList(false)
    }

    fun onEvent(event: MovieListUiEvent)
    {
        when (event) {
            MovieListUiEvent.Navigate -> {
                _movieListState.update {
                    it.copy(
                        isCurrentPopularScreen = !movieListState.value.isCurrentPopularScreen
                    )
                }
            }

            is MovieListUiEvent.Paginate -> {
                if (event.category == Category.POPULAR) {
                    getPopularMovieList(true)
                } else if (event.category == Category.UPCOMING) {
                    getUpcomingMovieList(true)
                }
            }

            is MovieListUiEvent.Search -> {
                searchMovies(event.query)
            }

            MovieListUiEvent.NavigateToFavorite -> {
                getFavoriteMovies()
            }

            is MovieListUiEvent.ToggleFavorite -> {
                toggleFavorite(event.movie)
            }

            else -> {}
        }
    }

    private fun getPopularMovieList(forceFetchFromRemote: Boolean) {
        viewModelScope.launch {
            _movieListState.update {
                it.copy(isLoading = true)
            }

            movieListRepository.getMovieList(
                forceFetchFromRemote,
                Category.POPULAR,
                movieListState.value.popularMovieListPage
            ).collectLatest { result ->
                when (result) {
                    is Resource.Error -> {
                        _movieListState.update {
                            it.copy(isLoading = false)
                        }
                    }

                    is Resource.Success -> {
                        result.data?.let { popularList ->
                            _movieListState.update {
                                it.copy(
                                    popularMovieList = movieListState.value.popularMovieList
                                            + popularList.shuffled(),
                                    popularMovieListPage = movieListState.value.popularMovieListPage + 1
                                )
                            }
                        }
                    }

                    is Resource.Loading -> {
                        _movieListState.update {
                            it.copy(isLoading = result.isLoading)
                        }
                    }

                    else -> {}
                }
            }
        }
    }

    private fun getUpcomingMovieList(forceFetchFromRemote: Boolean) {
        viewModelScope.launch {
            _movieListState.update {
                it.copy(isLoading = true)
            }

            movieListRepository.getMovieList(
                forceFetchFromRemote,
                Category.UPCOMING,
                movieListState.value.upcomingMovieListPage
            ).collectLatest { result ->
                when (result) {
                    is Resource.Error -> {
                        _movieListState.update {
                            it.copy(isLoading = false)
                        }
                    }

                    is Resource.Success -> {
                        result.data?.let { upcomingList ->
                            _movieListState.update {
                                it.copy(
                                    upcomingMovieList = movieListState.value.upcomingMovieList
                                            + upcomingList.shuffled(),
                                    upcomingMovieListPage = movieListState.value.upcomingMovieListPage + 1
                                )
                            }
                        }
                    }

                    is Resource.Loading -> {
                        _movieListState.update {
                            it.copy(isLoading = result.isLoading)
                        }
                    }

                    else -> {}
                }
            }
        }
    }

    private fun searchMovies(query: String) {
        viewModelScope.launch {
            _movieListState.update { it.copy(isLoading = true) }

            movieListRepository.searchMovies(query).collectLatest { result ->
                when (result) {
                    is Resource.Error -> {
                        _movieListState.update { it.copy(isLoading = false) }
                    }
                    is Resource.Success -> {
                        result.data?.let { searchResults ->
                            _movieListState.update { it.copy(searchResults = searchResults) }
                        }
                    }
                    is Resource.Loading -> {
                        _movieListState.update { it.copy(isLoading = result.isLoading) }
                    }

                    else -> {}
                }
            }
        }
    }

    private fun getFavoriteMovies() {
        viewModelScope.launch {
            _movieListState.update { it.copy(isLoading = true) }

            movieListRepository.getFavoriteMovies().collectLatest { result ->
                when (result) {
                    is Resource.Error -> {
                        _movieListState.update { it.copy(isLoading = false) }
                    }
                    is Resource.Success -> {
                        result.data?.let { favoriteMovies ->
                            _movieListState.update { it.copy(favoriteMovies = favoriteMovies) }
                        }
                    }
                    is Resource.Loading -> {
                        _movieListState.update { it.copy(isLoading = result.isLoading) }
                    }

                    else -> {}
                }
            }
        }
    }

    private fun toggleFavorite(movie: Movie) {
        viewModelScope.launch {
            val movieEntity = movie.toMovieEntity().copy(isFavorite = !movie.isFavorite)
            movieListRepository.updateMovie(movieEntity)
            // Update the favorite movies list
            getFavoriteMovies()
        }
    }
}
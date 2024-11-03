package com.example.a2019702923_main_exam_project.data.repository
/*
Bokang Ntovana
Main Exam Project
 */
import com.example.a2019702923_main_exam_project.data.local.MovieDatabase
import com.example.a2019702923_main_exam_project.data.local.MovieEntity
import com.example.a2019702923_main_exam_project.data.mappers.toMovie
import com.example.a2019702923_main_exam_project.data.mappers.toMovieEntity
import com.example.a2019702923_main_exam_project.data.remote.MovieApi
import com.example.a2019702923_main_exam_project.domain.model.Movie
import com.example.a2019702923_main_exam_project.domain.repository.MovieListRepository
import com.example.a2019702923_main_exam_project.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

class MovieListRepositoryImpl @Inject constructor(
    private val movieApi: MovieApi,
    private val movieDatabase: MovieDatabase
) : MovieListRepository {
    override suspend fun getMovieList(
        forceFetchFromRemote: Boolean,
        category: String,
        page: Int
    ): Flow<Resource<List<Movie>>> {
        return flow {
            emit(Resource.Loading(true))

            val localMovieList = movieDatabase.movieDao.getMovieListByCategory(category)

            val shouldLoadLocalMovie = localMovieList.isNotEmpty() && !forceFetchFromRemote

            if (shouldLoadLocalMovie) {
                emit(Resource.Success(
                    data = localMovieList.map { movieEntity ->
                        movieEntity.toMovie(category)
                    }
                ))

                emit(Resource.Loading(false))
                return@flow
            }

            val movieListFromApi = try {
                movieApi.getMoviesList(category, page)
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error(message = "Error loading movies"))
                return@flow
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error(message = "Error loading movies"))
                return@flow
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Resource.Error(message = "Error loading movies"))
                return@flow
            }

            val movieEntities = movieListFromApi.results.let {
                it.map { movieDto ->
                    movieDto.toMovieEntity(category)
                }
            }

            movieDatabase.movieDao.upsertMovieList(movieEntities)

            emit(Resource.Success(
                movieEntities.map { it.toMovie(category) }
            ))
            emit(Resource.Loading(false))
        }
    }

    override suspend fun getMovie(id: Int): Flow<Resource<Movie>> {
        return flow {
            emit(Resource.Loading(true))

            val movieEntity = movieDatabase.movieDao.getMovieById(id)

            if (movieEntity != null) {
                emit(
                    Resource.Success(movieEntity.toMovie(movieEntity.category))
                )

                emit(Resource.Loading(false))
                return@flow
            }

            emit(Resource.Error("Error no such movie"))

            emit(Resource.Loading(false))
        }
    }

    override suspend fun searchMovies(query: String): Flow<Resource<List<Movie>>> {
        return flow {
            emit(Resource.Loading(true))
            val localMovieList = movieDatabase.movieDao.searchMovies(query)
            emit(Resource.Success(data = localMovieList.map { it.toMovie("") }))
            emit(Resource.Loading(false))
        }
    }

    override suspend fun getFavoriteMovies(): Flow<Resource<List<Movie>>> {
        return flow {
            emit(Resource.Loading(true))
            val localFavoriteMovies = movieDatabase.movieDao.getFavoriteMovies()
            emit(Resource.Success(data = localFavoriteMovies.map { it.toMovie("") }))
            emit(Resource.Loading(false))
        }
    }

    override suspend fun updateMovie(movie: MovieEntity) {
        movieDatabase.movieDao.updateMovie(movie)
    }
}

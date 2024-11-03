package com.example.a2019702923_main_exam_project.corePresentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AddLocation
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Movie
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.Upcoming
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.a2019702923_main_exam_project.R
import com.example.a2019702923_main_exam_project.presentation.FavoriteMoviesScreen
import com.example.a2019702923_main_exam_project.presentation.MapScreen
import com.example.a2019702923_main_exam_project.presentation.MovieListUiEvent
import com.example.a2019702923_main_exam_project.presentation.MovieListViewModel
import com.example.a2019702923_main_exam_project.presentation.PopularMoviesScreen
import com.example.a2019702923_main_exam_project.presentation.SearchScreen
import com.example.a2019702923_main_exam_project.presentation.UpcomingMoviesScreen
import com.example.a2019702923_main_exam_project.util.Screen
/*
Bokang Ntovana
Main Exam Project
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController) {

    val movieListViewModel = hiltViewModel<MovieListViewModel>()
    val movieListState = movieListViewModel.movieListState.collectAsState().value
    val bottomNavController = rememberNavController()

    Scaffold(bottomBar = {
        BottomNavigationBar(
            bottomNavController = bottomNavController, onEvent = movieListViewModel::onEvent
        )
    },) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            NavHost(
                navController = bottomNavController,
                startDestination = Screen.PopularMovieList.rout
            ) {
                composable(Screen.PopularMovieList.rout) {
                    PopularMoviesScreen(
                        navController = navController,
                        movieListState = movieListState,
                        onEvent = movieListViewModel::onEvent
                    )
                }
                composable(Screen.UpcomingMovieList.rout) {
                    UpcomingMoviesScreen(
                        navController = navController,
                        movieListState = movieListState,
                        onEvent = movieListViewModel::onEvent
                    )
                }
                composable(Screen.Search.rout) {
                    SearchScreen(
                        navController = navController,
                        movieListState = movieListState,
                        onEvent = movieListViewModel::onEvent
                    )
                }
                composable(Screen.Favorites.rout) {
                    FavoriteMoviesScreen(
                        navController = navController,
                        movieListState = movieListState,
                        onEvent = movieListViewModel::onEvent
                    )
                }
                composable(Screen.Map.rout) {
                    MapScreen()
                }
            }
        }
    }
}

@Composable
fun BottomNavigationBar(
    bottomNavController: NavHostController, onEvent: (MovieListUiEvent) -> Unit
) {
    val items = listOf(
        BottomItem(
            title = stringResource(R.string.popular),
            icon = Icons.Rounded.Movie
        ),
        BottomItem(
            title = stringResource(R.string.upcoming),
            icon = Icons.Rounded.Upcoming
        ),
        BottomItem(
            title = stringResource(R.string.search),
            icon = Icons.Rounded.Search
        ),
        BottomItem(
            title = stringResource(R.string.favorites),
            icon = Icons.Rounded.Favorite
        ),
        BottomItem(
            title = stringResource(R.string.map),
            icon = Icons.Rounded.AddLocation
        )
    )

    val selected = rememberSaveable {
        mutableStateOf(0)
    }

    NavigationBar {
        Row(
            modifier = Modifier.background(MaterialTheme.colorScheme.inverseOnSurface)
        ) {
            items.forEachIndexed { index, bottomItem ->
                NavigationBarItem(selected = selected.value == index, onClick = {
                    selected.value = index
                    when (index) {
                        0 -> {
                            onEvent(MovieListUiEvent.Navigate)
                            bottomNavController.navigate(Screen.PopularMovieList.rout)
                        }
                        1 -> {
                            onEvent(MovieListUiEvent.Navigate)
                            bottomNavController.navigate(Screen.UpcomingMovieList.rout)
                        }
                        2 -> {
                            onEvent(MovieListUiEvent.Navigate)
                            bottomNavController.navigate(Screen.Search.rout)
                        }
                        3 -> {
                            onEvent(MovieListUiEvent.Navigate)
                            bottomNavController.navigate(Screen.Favorites.rout)
                        }
                        4 -> {
                            onEvent(MovieListUiEvent.Navigate)
                            bottomNavController.navigate(Screen.Map.rout)
                        }
                    }
                }, icon = {
                    Icon(
                        imageVector = bottomItem.icon,
                        contentDescription = bottomItem.title,
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }, label = {
                    Text(
                        text = bottomItem.title, color = MaterialTheme.colorScheme.onBackground
                    )
                })
            }
        }
    }
}

data class BottomItem(
    val title: String, val icon: ImageVector
)
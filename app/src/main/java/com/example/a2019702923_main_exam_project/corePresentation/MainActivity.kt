package com.example.a2019702923_main_exam_project.corePresentation

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.a2019702923_main_exam_project.Details.DetailsScreen
import com.example.a2019702923_main_exam_project.presentation.FavoriteMoviesScreen
import com.example.a2019702923_main_exam_project.presentation.MapScreen
import com.example.a2019702923_main_exam_project.presentation.MovieListViewModel
import com.example.a2019702923_main_exam_project.presentation.SearchScreen
import com.example.a2019702923_main_exam_project.ui.theme._2019702923_Main_Exam_ProjectTheme
import com.example.a2019702923_main_exam_project.util.Screen
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint
/*
Bokang Ntovana
Main Exam Project
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            _2019702923_Main_Exam_ProjectTheme {
                SetBarColor(color = MaterialTheme.colorScheme.inverseOnSurface)
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    MainNavigation(navController = navController)
                }
            }
        }

        // Request location permissions
        requestLocationPermissions()
    }

    private fun requestLocationPermissions() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
            && ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        }
    }

    @Composable
    private fun SetBarColor(color: Color) {
        val systemUiController = rememberSystemUiController()
        LaunchedEffect(key1 = color) {
            systemUiController.setSystemBarsColor(color)
        }
    }

    @Composable
    fun MainNavigation(navController: NavHostController) {
        NavHost(
            navController = navController,
            startDestination = Screen.Home.rout
        ) {
            composable(Screen.Home.rout) {
                HomeScreen(navController)
            }
            composable(
                Screen.Details.rout + "/{movieId}",
                arguments = listOf(
                    navArgument("movieId") { type = NavType.IntType }
                )
            ) { backStackEntry ->
                DetailsScreen()
            }
            composable(Screen.Search.rout) {
                val movieListViewModel: MovieListViewModel = hiltViewModel()
                SearchScreen(
                    navController = navController,
                    movieListState = movieListViewModel.movieListState.collectAsState().value,
                    onEvent = movieListViewModel::onEvent
                )
            }
            composable(Screen.Favorites.rout) {
                val movieListViewModel: MovieListViewModel = hiltViewModel()
                FavoriteMoviesScreen(
                    navController = navController,
                    movieListState = movieListViewModel.movieListState.collectAsState().value,
                    onEvent = movieListViewModel::onEvent
                )
            }
            composable(Screen.Map.rout) {
                MapScreen()
            }
        }
    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }
}

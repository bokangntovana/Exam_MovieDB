package com.example.a2019702923_main_exam_project.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.a2019702923_main_exam_project.presentation.components.MovieItem
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.a2019702923_main_exam_project.R
import com.example.a2019702923_main_exam_project.corePresentation.BottomNavigationBar

/*
Bokang Ntovana
Main Exam Project
 */
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteMoviesScreen(
    navController: NavHostController,
    movieListState: MovieListState,
    onEvent: (MovieListUiEvent) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.favorites),
                        fontSize = 20.sp
                    )
                },
                modifier = Modifier.shadow(2.dp),
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    MaterialTheme.colorScheme.inverseOnSurface
                )
            )
        },
        content = {
            Spacer(modifier = Modifier.padding(top = 200.dp))
            LazyColumn {
                items(movieListState.favoriteMovies) { movie ->
                    MovieItem(movie = movie, onEvent = onEvent,navController = navController)
                }
            }
        }
    )
}

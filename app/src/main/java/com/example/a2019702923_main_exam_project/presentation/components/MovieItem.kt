package com.example.a2019702923_main_exam_project.presentation.components

import android.content.Context
import android.location.Location
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.example.a2019702923_main_exam_project.domain.model.Movie
import com.example.a2019702923_main_exam_project.presentation.MovieListUiEvent
import com.example.a2019702923_main_exam_project.util.LocationUtils
import kotlinx.coroutines.launch
/*
Bokang Ntovana
Main Exam Project
 */
@Composable
fun MovieItem(
    movie: Movie,
    onEvent: (MovieListUiEvent) -> Unit,
    navController: NavHostController? = null // Optional NavHostController parameter
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val locationUtils = LocationUtils(context)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable {
                scope.launch {
                    val location = locationUtils.getCurrentLocation()
                    location?.let {
                        showLocation(context, it)
                    }
                }
                onEvent(MovieListUiEvent.ToggleFavorite(movie))
                navController?.navigate("details/${movie.id}") // Navigate to details screen if navController is provided
            }
    ) {
        Image(
            painter = rememberAsyncImagePainter(
                ImageRequest.Builder(LocalContext.current).data(data = movie.poster_path)
                    .apply {
                        crossfade(true)
                    }.build()
            ),
            contentDescription = movie.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(100.dp)
                .padding(end = 16.dp)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            Text(text = movie.title, style = MaterialTheme.typography.titleMedium)
            Text(text = movie.release_date, style = MaterialTheme.typography.bodyMedium)
            Text(text = movie.overview, style = MaterialTheme.typography.bodySmall, maxLines = 2)
        }

        Icon(
            imageVector = if (movie.isFavorite) Icons.Rounded.Favorite else Icons.Rounded.FavoriteBorder,
            contentDescription = if (movie.isFavorite) "Remove from favorites" else "Add to favorites",
            modifier = Modifier
                .size(24.dp)
                .padding(8.dp)
        )
    }
}
private fun showLocation(context: Context, location: Location) {
    Toast.makeText(context, "Location: ${location.latitude}, ${location.longitude}", Toast.LENGTH_LONG).show()
}
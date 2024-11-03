package com.example.a2019702923_main_exam_project.presentation

import android.Manifest
import android.annotation.SuppressLint
import android.location.Location
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.*
import com.example.a2019702923_main_exam_project.R
import com.example.a2019702923_main_exam_project.presentation.components.MovieItem
import com.example.a2019702923_main_exam_project.util.LocationUtils
import com.google.accompanist.permissions.*
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
/*
Bokang Ntovana
Main Exam Project
 */
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterial3Api::class)
@Composable
fun MapScreen() {
    val context = LocalContext.current
    val locationUtils = LocationUtils(context)
    var currentLocation by remember { mutableStateOf<Location?>(null) }

    val permissionsState = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    )

    LaunchedEffect(Unit) {
        if (!permissionsState.allPermissionsGranted) {
            permissionsState.launchMultiplePermissionRequest()
        } else {
            currentLocation = locationUtils.getCurrentLocation()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.map),
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
            Column(modifier = Modifier.fillMaxSize()) {
                Spacer(modifier = Modifier.padding(top = 50.dp))
                if (currentLocation != null) {
                    val location = LatLng(currentLocation!!.latitude, currentLocation!!.longitude)

                    GoogleMap(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(400.dp),
                        cameraPositionState = rememberCameraPositionState {
                            position = CameraPosition.fromLatLngZoom(location, 15f)
                        }
                    ) {
                        Marker(
                            state = rememberMarkerState(position = location),
                            title = "Your Location is...",
                            snippet = "Right here"
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "You are watching from here...",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(16.dp)
                    )
                    Spacer(modifier = Modifier.weight(1f))

                    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.globe))
                    val progress by animateLottieCompositionAsState(
                        composition = composition,
                        iterations = LottieConstants.IterateForever
                    )

                    LottieAnimation(
                        composition = composition,
                        progress = progress,
                        modifier = Modifier
                            .size(200.dp)
                            //.padding(32.dp)
                    )
                } else {
                    Text(
                        text = "Fetching your location...",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    )
}
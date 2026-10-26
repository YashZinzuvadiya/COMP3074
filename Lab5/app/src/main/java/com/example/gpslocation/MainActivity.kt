package com.example.gpslocation

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {

    private lateinit var locationManager: LocationManager

    private var location by mutableStateOf<Location?>(null)
    private var hasPermission by mutableStateOf(false)

    private val locationListener = LocationListener { newLocation ->
        location = newLocation
    }

    private val permissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) {
        hasPermission = isFineLocationGranted()
        if (hasPermission) startLocationUpdates()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        locationManager = getSystemService(LOCATION_SERVICE) as LocationManager

        hasPermission = isFineLocationGranted()
        if (!hasPermission) requestLocationPermission()

        setContent {
            MaterialTheme {
                Scaffold { padding ->
                    Column(
                        modifier = Modifier.fillMaxSize().padding(padding).padding(24.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("Current Location", style = MaterialTheme.typography.headlineMedium)

                        val current = location
                        when {
                            !hasPermission -> {
                                Text("Location permission is required.", Modifier.padding(top = 16.dp))
                                Button(
                                    onClick = { requestLocationPermission() },
                                    modifier = Modifier.padding(top = 16.dp)
                                ) { Text("Grant permission") }
                            }
                            current == null ->
                                Text("Waiting for GPS fix...", Modifier.padding(top = 16.dp))
                            else -> {
                                Text("Latitude: %.6f".format(current.latitude), Modifier.padding(top = 16.dp))
                                Text("Longitude: %.6f".format(current.longitude), Modifier.padding(top = 8.dp))
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        if (hasPermission) startLocationUpdates()
    }

    override fun onStop() {
        super.onStop()
        locationManager.removeUpdates(locationListener)
    }

    private fun isFineLocationGranted() =
        checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED

    private fun requestLocationPermission() {
        permissionLauncher.launch(
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
        )
    }

    @SuppressLint("MissingPermission")
    private fun startLocationUpdates() {
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000L, 0f, locationListener)
    }
}

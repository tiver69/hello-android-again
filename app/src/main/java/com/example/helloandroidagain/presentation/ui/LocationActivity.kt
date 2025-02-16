package com.example.helloandroidagain.presentation.ui

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.example.helloandroidagain.R
import com.example.helloandroidagain.databinding.LocationActivityBinding
import com.example.helloandroidagain.presentation.LocationService
import com.example.helloandroidagain.presentation.LocationWorkManagerHelper
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LocationActivity : AppCompatActivity() {

    private lateinit var binding: LocationActivityBinding
    private val viewModel: LocationViewModel by viewModels()

    @Inject
    lateinit var locationWorkManagerHelper: LocationWorkManagerHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LocationActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        locationPermissionRequest.launch(
            arrayOf(
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
        if (checkSelfPermission(android.Manifest.permission.ACCESS_BACKGROUND_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                showSettingsDialog(R.string.background_permission_required_text)
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                backgroundLocationPermissionRequest.launch(
                    android.Manifest.permission.ACCESS_BACKGROUND_LOCATION
                )
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        stopService(Intent(this, LocationService::class.java))
    }

    private val locationPermissionRequest =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            when {
                permissions.getOrDefault(
                    android.Manifest.permission.ACCESS_FINE_LOCATION,
                    false
                ) -> {
                    showToastWithPermission("Fine")
                    launchLocationTracking()
                }

                permissions.getOrDefault(
                    android.Manifest.permission.ACCESS_COARSE_LOCATION,
                    false
                ) -> {
                    showSettingsDialog(R.string.permission_welcomed_text)
                    showToastWithPermission("Coarse")
                    launchLocationTracking()
                }

                else -> {
                    showSettingsDialog(R.string.permission_required_text)
                }
            }
        }


    private val backgroundLocationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { _ -> }

    private fun launchLocationTracking() {
        checkLocationServiceAvailability()
        observeLocation()
        ContextCompat.startForegroundService(
            this,
            Intent(this, LocationService::class.java)
        )
        locationWorkManagerHelper.scheduleDailyWork()
    }

    private fun observeLocation() {
        lifecycleScope.launch {
            viewModel.getLocationFlow().collect { location ->
                location?.let {
                    binding.liveLocation.text =
                        getString(R.string.location_format, it.latitude, it.longitude)
                } ?: run {
                    checkLocationServiceAvailability()
                }
            }
        }
    }

    private fun checkLocationServiceAvailability() {
        val locationManager =
            applicationContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            binding.liveLocation.text = getString(R.string.waiting_for_location_service)
        }
    }

    private fun showSettingsDialog(messageId: Int) {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.permission_required_title))
            .setMessage(getString(messageId))
            .setPositiveButton(getString(R.string.settings_dialog_positive)) { _, _ -> openAppSettings() }
            .setNegativeButton(getString(R.string.cancel)) { dialog, _ -> dialog.dismiss() }
            .show()
    }

    private fun openAppSettings() {
        val intent = Intent(
            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
            Uri.fromParts("package", packageName, null)
        )
        startActivity(intent)
    }

    private fun showToastWithPermission(permission: String) {
        Toast.makeText(
            this,
            getString(R.string.permission_granted_format, permission),
            Toast.LENGTH_SHORT
        ).show()
    }
}
package com.example.studenthub.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import com.example.studenthub.R
import com.example.studenthub.databinding.ActivityMainBinding
import com.example.studenthub.ui.auth.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

/**
 * 🔹 MainActivity
 *
 * The main Activity of the application.
 * - All fragments are hosted inside this Activity using NavHostFragment.
 * - On startup, it checks whether the user is logged in and sets the start destination accordingly.
 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    // ViewBinding
    private lateinit var binding: ActivityMainBinding

    // AuthViewModel to check the user's login state
    private val authViewModel: AuthViewModel by viewModels()

    /**
     * 🔹 Called when the Activity is created
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 🔹 Inflate the layout
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get NavController
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        // Inflate the navigation graph
        val graph = navController.navInflater.inflate(R.navigation.main_nav_graph)

        // Determine the start destination (check if the user is logged in or not)
        lifecycleScope.launch {
            val username = authViewModel.currentUserFlow.firstOrNull() //Current user’s username

            // 🔹 If no user exists, navigate to loginFragment; otherwise, go to homeFragment
            graph.setStartDestination(
                if (username.isNullOrEmpty()){
                    R.id.loginFragment
                }else{
                    R.id.homeFragment
                }
            )

            // 🔹 Set the updated graph to the NavController
            navController.graph = graph
        }
    }
}
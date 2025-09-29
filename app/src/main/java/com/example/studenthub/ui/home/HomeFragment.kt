package com.example.studenthub.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.studenthub.R
import com.example.studenthub.databinding.FragmentHomeBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment: Fragment(){

    // ViewModel injected via Hilt
    private val viewModel: HomeViewModel by viewModels()

    // ViewBinding nullable backing property
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    /**
     * Creates the fragment view.
     * Inflates the layout and initializes the binding.
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        saveInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    /**
     * Called after the fragment view is created.
     * Sets up click listeners and navigation controller.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        // Show logout confirmation dialog when logout button is clicked
        binding.logout.setOnClickListener {
            showLogoutDialog()
        }

        // Nested navigation (nav host fragment inside Home)
        val navHostFragment = childFragmentManager
            .findFragmentById(R.id.home_nav_host_fragment) as NavHostFragment

        val navController = navHostFragment.navController

//        // Connect BottomNavigationView with navController
        binding.bottomNavigation.setupWithNavController(navController)
    }

    /**
     * Shows a logout confirmation dialog.
     * If confirmed, performs logout and navigates back to the login screen.
     */
    private fun showLogoutDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Logout")
            .setMessage("Are you sure you want to log out?")
            .setPositiveButton("Yes") { _, _ ->
                viewModel.logout()  // perform logout
                findNavController().navigate(
                    R.id.loginFragment,
                    null,
                    NavOptions.Builder()
                        .setPopUpTo(R.id.main_nav_graph, inclusive = true) // clear back stack
                        .build()
            )
        }
            .setNegativeButton("No", null) // dismiss the dialog if canceled
            .show()
    }

    /**
     * Clears the binding when the view is destroyed
     * to avoid memory leaks.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


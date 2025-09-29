package com.example.studenthub.ui.splash

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.studenthub.R
import com.example.studenthub.ui.auth.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/**
 * 🔹 SplashFragment
 *
 * This fragment is launched when the app starts.
 * It checks whether the user is logged in or not and navigates accordingly:
 * - If the user is not logged in → navigates to LoginFragment
 * - If the user is logged in → navigates to HomeFragment
 */
@AndroidEntryPoint
class SplashFragment : Fragment() {

    // 🔹 AuthViewModel – used to check the current user login state
    private val viewModel: AuthViewModel by viewModels()

    /**
     * Inflates the layout for this fragment.
     * Connects the fragment to its XML layout (fragment_splash.xml).
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    /**
     * Inflates the layout for this fragment.
     * Connects the fragment to its XML layout (fragment_splash.xml).
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Launch coroutine inside lifecycleScope to observe user state
        viewLifecycleOwner.lifecycleScope.launch {
            // Collect login state from AuthViewModel
            viewModel.currentUserFlow.collect { username ->
                if (username.isNullOrBlank()) {
                    // If no user is logged in → navigate to LoginFragment
                    findNavController().navigate(R.id.loginFragment)
                } else {
                    // If user is logged in → navigate to HomeFragment
                    findNavController().navigate(R.id.homeFragment)
                }
            }
        }
    }

}
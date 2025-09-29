package com.example.studenthub.ui.auth


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.studenthub.R
import com.example.studenthub.databinding.FragmentLoginBinding
import com.example.studenthub.databinding.LayoutBottomSheet1Binding
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint


/**
 * LoginFragment — screen for user authentication (login).
 * Handles UI interactions and delegates login logic to AuthViewModel.
 */
@AndroidEntryPoint
class LoginFragment : Fragment() {

    // ViewBinding for accessing UI elements
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    // AuthViewModel injected via Hilt
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the Fragment layout with ViewBinding
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // ========================
        // 🔹 Login button listener
        // ========================
        binding.btnLogin.setOnClickListener {
            val username = binding.etUsername.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            // Validate form input
            if (username.isEmpty() || password.length < 8) {
                showBottomSheet("Invalid username or password!")
                return@setOnClickListener
            }

            //Show progress bar
            binding.progressBar.visibility = View.VISIBLE

            // Trigger login via ViewModel
            viewModel.login(username, password)
        }

        // ========================
        // 🔹 Navigate to Signup screen
        // ========================
        binding.tvGoToSignup.setOnClickListener {
            // Navigate to SignupFragment
            findNavController().navigate(R.id.action_loginFragment_to_signupFragment)
        }

        // ========================
        // 🔹 Observe authentication state
        // ========================
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.authState.collect { state ->
                // Hide progress bar once login result is received
                binding.progressBar.visibility = View.GONE

                when (state) {
                    // ✅ Successful login
                    is AuthState.Success -> {
                        findNavController().navigate(
                            R.id.homeFragment, //Navigate to HomeFragment
                            null,
                            NavOptions.Builder()
                                .setPopUpTo(
                                    R.id.loginFragment,
                                    inclusive = true
                                )
                                .build()
                        )
                    }
                    // Error state
                    is AuthState.Error -> showBottomSheet(state.message)

                    // Idle state (do nothing)
                    AuthState.Idle -> Unit
                }
            }
        }
    }

    /**
     * 🔹 Show a BottomSheet dialog with a message
     */
    private fun showBottomSheet(message: String) {
        val bottomSheet = BottomSheetDialog(requireContext())

        // Use ViewBinding for layout_bottom_sheet1.xml
        val sheetBinding = LayoutBottomSheet1Binding.inflate(layoutInflater)

        // Set error or info message
        sheetBinding.tvMessage.text = message

        // OK button dismisses the BottomSheet
        sheetBinding.btnOk.setOnClickListener { bottomSheet.dismiss() }

        // Set the content and show
        bottomSheet.setContentView(sheetBinding.root)
        bottomSheet.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Avoid memory leaks by clearing binding
    }
}



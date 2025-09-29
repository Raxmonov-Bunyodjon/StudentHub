package com.example.studenthub.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.studenthub.R
import com.example.studenthub.databinding.FragmentSignupBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest


/**
 * SignupFragment — UI screen for user registration.
 * Handles user input validation and communicates with AuthViewModel
 * to perform the signup process.
 */
@AndroidEntryPoint
class SignupFragment : Fragment() {

    // ViewBinding to access UI components
    private var _binding: FragmentSignupBinding? = null
    private val binding get() = _binding!!

    // Inject AuthViewModel using Hilt
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the Fragment layout with ViewBinding
        _binding = FragmentSignupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // ========================
        // 🔹 Input validation
        // ========================
        // Validate First Name, Last Name, Username, and Password
        binding.etFirstName.addTextChangedListener(SimpleTextWatcher {
            binding.tilFirstName.error = if (it.isNullOrEmpty()) "First name is required" else null
            checkInputs()
        })
        binding.etLastName.addTextChangedListener(SimpleTextWatcher {
            binding.tilLastName.error = if (it.isNullOrEmpty()) "Last name is required" else null
            checkInputs()
        })
        binding.etUsername.addTextChangedListener(SimpleTextWatcher {
            binding.tilUsername.error = if (it.isNullOrEmpty()) "Username cannot be empty" else null
            checkInputs()
        })
        binding.etPassword.addTextChangedListener(SimpleTextWatcher { text ->
            val password = text?.toString()?.trim() ?: ""
            binding.tilPassword.error = when {
                password.isEmpty() -> "Password is required"
                password.length < 8 -> "Password must be at least 8 characters"
                else -> null
            }
            checkInputs()
        })

        // ========================
        // 🔹 Signup button listener
        // ========================
        binding.btnSignup.setOnClickListener {
            val firstName = binding.etFirstName.text.toString().trim()
            val lastName = binding.etLastName.text.toString().trim()
            val username = binding.etUsername.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            // Show progress bar
            binding.progressBar.visibility = View.VISIBLE

            // Call signup function from ViewModel
            viewModel.signup(firstName, lastName, username, password)
        }

        // ========================
        // 🔹 Navigate back to Login screen
        // ========================
        binding.tvGoToLogin.setOnClickListener {
            findNavController().navigate(R.id.action_signupFragment_to_loginFragment)
        }

        // ========================
        // 🔹 Observe authentication state
        // ========================
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.authState.collectLatest { state ->
                binding.progressBar.visibility = View.GONE

                when (state) {
                    // ✅ Signup success
                    is AuthState.Success -> {
                        showSnackbar(state.message, true)
                        // After successful signup, navigate back to login screen
                        findNavController().navigate(R.id.action_signupFragment_to_loginFragment)
                    }
                    // ❌ Signup error
                    is AuthState.Error -> showSnackbar(state.message, false)

                    // Idle state (do nothing)
                    AuthState.Idle -> Unit
                }
            }
        }

        // Initial check for enabling/disabling the button
        checkInputs()
    }

    /**
     * 🔹 Validate input fields and enable/disable Signup button
     */
    private fun checkInputs() {
        val firstName = binding.etFirstName.text.toString().trim()
        val lastName = binding.etLastName.text.toString().trim()
        val username = binding.etUsername.text.toString().trim()
        val password = binding.etPassword.text.toString().trim()

        binding.btnSignup.isEnabled =
            firstName.isNotEmpty() && lastName.isNotEmpty() &&
                    username.isNotEmpty() && password.length >= 8
    }

    /**
     * 🔹 Show Snackbar with success or error message
     */
    private fun showSnackbar(message: String, isSuccess: Boolean) {
        val snackbar = Snackbar.make(requireView(), message, Snackbar.LENGTH_SHORT)
        if (isSuccess) {
            snackbar.setBackgroundTint(resources.getColor(R.color.teal_700, null))
        } else {
            snackbar.setBackgroundTint(resources.getColor(R.color.red, null))
        }
        snackbar.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Avoid memory leaks by clearing binding
    }
}


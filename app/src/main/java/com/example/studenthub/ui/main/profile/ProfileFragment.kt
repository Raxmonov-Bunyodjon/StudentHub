package com.example.studenthub.ui.main.profile

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import com.example.studenthub.databinding.FragmentProfileBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


/**
 * 🔹 ProfileFragment
 *
 * Displays the user profile and allows updating the avatar.
 * Observes user data via Flow from the ViewModel.
 */
@AndroidEntryPoint
class ProfileFragment : Fragment() {

    // 🔹 ViewBinding nullable property
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    // 🔹 ViewModel injected using Hilt
    private val viewModel: ProfileViewModel by viewModels()

    // 🔹 Image picker launcher using ActivityResult API
    private val pickImageLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                // 🔹 Load the selected image into ImageView
                Glide.with(this)
                    .load(it)
                    .circleCrop() // show as circle
                    .into(binding.ivProfile)

                // 🔹 Save avatar through ViewModel
                viewModel.saveAvatar(it.toString())
            }
        }

    /**
     * 🔹 onCreateView
     * Inflates the fragment layout and sets up binding.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    /**
     * 🔹 onViewCreated
     * Sets up UI and interactions:
     * - Collects user data
     * - Handles avatar change button
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 🔹 Collect StateFlow with LifecycleScope
        viewLifecycleOwner.lifecycleScope.launch {
            // Collect userFlow when fragment is in STARTED state
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.userFlow.collect { user ->
                    user?.let {
                        // 🔹 Set user data to UI
                        binding.tvFirstName.text = it.firstName
                        binding.tvLastName.text = it.lastName
                        binding.tvUsername.text = it.username

                        // 🔹 Load avatar if available
                        it.avatar?.let { avatarUri ->
                            Glide.with(this@ProfileFragment)
                                .load(avatarUri)
                                .circleCrop()
                                .into(binding.ivProfile)
                        }
                    }
                }
            }
        }
        // 🔹 Change avatar button click listener
        binding.btnChangeAvatar.setOnClickListener {
            pickImageLauncher.launch("image/*") // open image picker
        }
    }

    /**
     * 🔹 onDestroyView
     * Clear binding to avoid memory leaks.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}





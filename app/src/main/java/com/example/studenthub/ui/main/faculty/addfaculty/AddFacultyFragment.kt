package com.example.studenthub.ui.main.faculty.addfaculty

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.studenthub.databinding.FragmentAddFacultyBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/**
 * 🔹 AddFacultyFragment
 *   - UI for adding or editing a faculty
 *   - Edit or Add mode is determined via arguments
 */
@AndroidEntryPoint
class AddFacultyFragment : Fragment() {

    private var _binding: FragmentAddFacultyBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AddFacultyViewModel by viewModels()

    private var facultyId: Long = -1  // 🔹 Add: -1, Edit: existing ID

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // 🔹 Inflate the layout using ViewBinding
        _binding = FragmentAddFacultyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 🔹 Get facultyId from NavGraph arguments
        facultyId = arguments?.getLong("facultyId", -1) ?: -1

        // 🔹 Load existing faculty if in Edit mode
        if (facultyId != -1L) {
            viewLifecycleOwner.lifecycleScope.launch {
                val faculty = viewModel.getFacultyById(facultyId)
                faculty?.let {
                    // 🔹 Set the EditText with existing faculty name
                    binding.etFacultyName.setText(it.name)

                    // 🔹 Add listener for search while typing
                    binding.etFacultyName.addTextChangedListener { editable ->
                        val query = editable.toString()
                    }
                }
            }
        }

        // 🔹 Set button text depending on Add or Update mode
        binding.btnSaveFaculty.text = if (facultyId == -1L) "Add" else "Update"

        // 🔹 Enable/disable Save button based on EditText content
        binding.btnSaveFaculty.isEnabled = binding.etFacultyName.text.toString().isNotBlank()
        binding.etFacultyName.addTextChangedListener {
            binding.btnSaveFaculty.isEnabled = it.toString().isNotBlank()
        }

        // 🔹 Handle Save button click
        binding.btnSaveFaculty.setOnClickListener {
            val name = binding.etFacultyName.text.toString().trim()
            if (name.isNotEmpty()) {
                if (facultyId == -1L) {
                    viewModel.addFaculty(name)
                } else {
                    viewModel.updateFaculty(facultyId, name)
                }
                Toast.makeText(requireContext(), "Faculty saved ✅", Toast.LENGTH_SHORT).show()
                findNavController().popBackStack()
            } else {
                Snackbar.make(binding.root, "Please enter a faculty name", Snackbar.LENGTH_SHORT).show()
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // 🔹 Prevent memory leaks
    }
}

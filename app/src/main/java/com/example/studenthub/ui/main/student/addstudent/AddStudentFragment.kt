package com.example.studenthub.ui.main.student.addstudent

import android.R
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.studenthub.data.local.entity.StudentEntity
import com.example.studenthub.databinding.FragmentAddStudentBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

/**
 * 🔹 AddStudentFragment
 *
 * Fragment for adding (ADD) or editing (EDIT) a student.
 * Allows selecting faculty via Spinner and inputting first name, last name, and direction.
 * Save button is enabled only when all required fields are filled.
 */
@AndroidEntryPoint
class AddStudentFragment : Fragment() {

    // ---------------------------
    // 🔹 ViewBinding
    // ---------------------------
    private var _binding: FragmentAddStudentBinding? = null
    private val binding get() = _binding!!

    // ---------------------------
    // 🔹 ViewModel (injected via Hilt)
    // ---------------------------
    private val viewModel: AddStudentViewModel by viewModels()

    // ---------------------------
    // 🔹 Selected faculty ID from Spinner
    // ---------------------------
    private var selectedFacultyId: Long? = null

    // ---------------------------
    // 🔹 Inflate layout
    // ---------------------------
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddStudentBinding.inflate(inflater, container, false)
        return binding.root
    }

    // ---------------------------
    // 🔹 Runs when UI is ready
    // ---------------------------
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 🔹 Attach faculties to Spinner
        setupFacultySpinner()

        // 🔹 studentId from arguments → for EDIT mode
        val studentId = arguments?.getLong("studentId", -1L) ?: -1L
        if (studentId != -1L) {
            viewLifecycleOwner.lifecycleScope.launch {
                val student = viewModel.getStudentById(studentId)
                student?.let {
                    binding.etFirstName.setText(it.firstName)
                    binding.etLastName.setText(it.lastName)
                    selectedFacultyId = it.facultyId
                    binding.etFacultyDirection.setText(it.direction)
                }
            }
        }

        // 🔹 Disable Save button initially
        updateSaveButtonState()

        // 🔹 Update button state using TextWatchers
        val textWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) { updateSaveButtonState() }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        }
        binding.etFirstName.addTextChangedListener(textWatcher)
        binding.etLastName.addTextChangedListener(textWatcher)

        // 🔹 Handle Save button click → ADD or EDIT
        binding.btnSaveStudent.setOnClickListener {
            if (studentId == -1L) saveStudent()
            else updateStudent(studentId)
        }
    }

    // ---------------------------
    // 🔹 Enable/disable Save button based on input fields
    // ---------------------------
    private fun updateSaveButtonState() {
        val enabled = binding.etFirstName.text.toString().isNotBlank() &&
                binding.etLastName.text.toString().isNotBlank() &&
                selectedFacultyId != null

        binding.btnSaveStudent.isEnabled = enabled
        binding.btnSaveStudent.alpha = if (enabled) 1f else 0.5f
    }

    // ---------------------------
    // 🔹 Update existing student (EDIT mode)
    // ---------------------------
    private fun updateStudent(id: Long) {
        val firstName = binding.etFirstName.text.toString().trim()
        val lastName = binding.etLastName.text.toString().trim()
        val direction = binding.etFacultyDirection.text.toString().trim()

        val student = StudentEntity(
            id = id,
            firstName = firstName,
            lastName = lastName,
            facultyId = selectedFacultyId!!,
            direction = direction,
            avatar = null
        )

        viewModel.updateStudent(student)
        Toast.makeText(requireContext(), "Student updated ✅", Toast.LENGTH_SHORT).show()
        findNavController().popBackStack()
    }

    // ---------------------------
    // 🔹 Attach faculties to Spinner
    // ---------------------------
    private fun setupFacultySpinner() {
        viewLifecycleOwner.lifecycleScope.launch {
            val faculties = viewModel.faculties.first()

            val facultyNames = mutableListOf("No faculty selected")
            facultyNames.addAll(faculties.map { it.name })

            val adapter = ArrayAdapter(
                requireContext(),
                R.layout.simple_spinner_item,
                facultyNames
            )
            adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
            binding.spinnerFaculty.adapter = adapter

            // 🔹 Spinner listener
            binding.spinnerFaculty.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>, view: View?, position: Int, id: Long
                    ) {
                        selectedFacultyId = if (position == 0) null else faculties[position - 1].id
                        updateSaveButtonState()
                    }

                    override fun onNothingSelected(parent: AdapterView<*>) {
                        selectedFacultyId = null
                        updateSaveButtonState()
                    }
                }
        }
    }

    // ---------------------------
    // 🔹 Save new student (ADD mode)
    // ---------------------------
    private fun saveStudent() {
        val firstName = binding.etFirstName.text.toString().trim()
        val lastName = binding.etLastName.text.toString().trim()
        val direction = binding.etFacultyDirection.text.toString().trim()

        val student = StudentEntity(
            firstName = firstName,
            lastName = lastName,
            facultyId = selectedFacultyId!!,
            direction = direction,
            avatar = null
        )

        viewModel.addStudent(student)
        Toast.makeText(requireContext(), "Student saved ✅", Toast.LENGTH_SHORT).show()
        findNavController().popBackStack()
    }

    // ---------------------------
    // 🔹 Prevent memory leaks
    // ---------------------------
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

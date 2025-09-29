package com.example.studenthub.ui.main.student

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.studenthub.R
import com.example.studenthub.databinding.FragmentStudentBinding
import com.example.studenthub.utils.showSnackbar
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/**
 * 🔹 StudentFragment
 *
 * Displays the list of students with capabilities to search, add, edit, and delete.
 */
@AndroidEntryPoint
class StudentFragment : Fragment() {

    private var _binding: FragmentStudentBinding? = null
    private val binding get() = _binding!!

    // 🔹 ViewModel for student data
    private val viewModel: StudentViewModel by viewModels()

    // 🔹 RecyclerView Adapter
    private lateinit var adapter: StudentAdapter

    /**
     * 🔹 onCreateView
     * Inflates layout using ViewBinding
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStudentBinding.inflate(inflater, container, false)
        return binding.root
    }

    /**
     * 🔹 onViewCreated
     * Sets up RecyclerView, Adapter, FAB, and SearchView
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 🔹 Initialize adapter and attach listeners
        adapter = StudentAdapter(
            onClick = { student ->
                binding.root.showSnackbar("${student.firstName} ${student.lastName} selected")
            },
            onEditClick = { student ->
                val parentNav = requireActivity().findNavController(R.id.nav_host_fragment)
                val bundle = Bundle().apply { putLong("studentId", student.id) }
                parentNav.navigate(R.id.addStudentFragment, bundle)
            },
            onDeleteClick = { student ->
                viewModel.deleteStudent(student.id)
                Snackbar.make(binding.root, "${student.firstName} deleted", Snackbar.LENGTH_SHORT).show()
            }
        )

        // 🔹 Setup RecyclerView
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        // 🔹 FloatingActionButton to add a new student
        binding.fabAddFaculty.setOnClickListener {
            val parentNav = requireActivity().findNavController(R.id.nav_host_fragment)
            val bundle = Bundle().apply { putLong("facultyId", -1L) }
            parentNav.navigate(R.id.addStudentFragment, bundle)
        }

        // 🔹 Listen to text changes in TextInputEditText for search
        binding.searchView.addTextChangedListener { editable ->
            val query = editable.toString()
            viewModel.searchStudents(query) // search as user types
        }

        // 🔹 Observe student list and submit to adapter
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.students.collect { list ->
                adapter.submitList(list)
            }
        }
    }

    /**
     * 🔹 onDestroyView
     * Clear binding to prevent memory leaks
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

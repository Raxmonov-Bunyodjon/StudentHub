package com.example.studenthub.ui.main.faculty

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
import com.example.studenthub.databinding.FragmentFacultyBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * 🔹 FacultyFragment
 *   - Displays the list of faculties
 *   - Handles add, edit, delete actions
 *   - Supports search with SearchView
 */
@AndroidEntryPoint
class FacultyFragment : Fragment() {

    private var _binding: FragmentFacultyBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FacultyViewModel by viewModels()
    private lateinit var adapter: FacultyAdapter

    /**
     * 🔹 Inflate the layout and initialize binding
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFacultyBinding.inflate(inflater, container, false)
        return binding.root
    }

    /**
     * 🔹 Setup UI elements and attach listeners
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 🔹 Create adapter and attach callbacks
        adapter = FacultyAdapter(
            onItemClick = { faculty ->
                Snackbar.make(binding.root, "${faculty.name} selected", Snackbar.LENGTH_SHORT).show()
            },
            onEditClick = { faculty ->
                val parentNav = requireActivity().findNavController(R.id.nav_host_fragment)
                val bundle = Bundle().apply { putLong("facultyId", faculty.id) }
                parentNav.navigate(R.id.addFacultyFragment, bundle)
            },
            onDeleteClick = { faculty ->
                viewModel.deleteFaculty(faculty.id)
                Snackbar.make(binding.root, "${faculty.name} deleted", Snackbar.LENGTH_SHORT).show()
            }
        )

        // 🔹 Setup RecyclerView
        binding.recyclerViewFaculty.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewFaculty.adapter = adapter

        // 🔹 FAB click to add new faculty
        binding.fabAddFaculty.setOnClickListener {
            val parentNav = requireActivity().findNavController(R.id.nav_host_fragment)
            val bundle = Bundle().apply { putLong("facultyId", -1L) }
            parentNav.navigate(R.id.addFacultyFragment, bundle)
        }

        // 🔹 TextInputEditText listener for search
        binding.searchView.addTextChangedListener { editable ->
            val query = editable.toString()
            viewModel.searchFaculties(query) // perform search based on typed text
        }

        // 🔹 Observe faculties list and submit to adapter
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.faculties.collectLatest { list ->
                adapter.submitList(list)
            }
        }
    }
    /**
     * 🔹 Clear binding to prevent memory leaks
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

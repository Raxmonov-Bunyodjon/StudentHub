package com.example.studenthub.ui.main.faculty

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.studenthub.databinding.ItemFacultyBinding
import com.example.studenthub.domain.model.Faculty

/**
 * 🔹 FacultyAdapter
 *   - ListAdapter for RecyclerView
 *   - Displays the list of faculties
 *   - Supports Click, Edit, and Delete callbacks
 */
class FacultyAdapter(
    private val onItemClick: (Faculty) -> Unit,   // 🔹 Callback for item click
    private val onEditClick: (Faculty) -> Unit,   // 🔹 Callback for edit button click
    private val onDeleteClick: (Faculty) -> Unit  // 🔹 Callback for delete button click
) : ListAdapter<Faculty, FacultyAdapter.FacultyViewHolder>(DIFF_CALLBACK) {

    companion object {
        // 🔹 DiffUtil.ItemCallback – optimizes RecyclerView updates
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Faculty>() {
            // 🔹 Items are the same if their IDs match
            override fun areItemsTheSame(oldItem: Faculty, newItem: Faculty) = oldItem.id == newItem.id

            // 🔹 Contents are the same if all fields match
            override fun areContentsTheSame(oldItem: Faculty, newItem: Faculty) = oldItem == newItem
        }
    }

    /**
     * 🔹 ViewHolder – binds the UI for each item
     */
    inner class FacultyViewHolder(private val binding: ItemFacultyBinding) :
        RecyclerView.ViewHolder(binding.root) {

        /**
         * 🔹 Bind the item data
         * @param faculty – Faculty model
         */
        fun bind(faculty: Faculty) {
            // 🔹 Set faculty name to TextView
            binding.tvFacultyName.text = faculty.name

            // 🔹 Item click callback
            binding.root.setOnClickListener { onItemClick(faculty) }

            // 🔹 Edit button click callback
            binding.btnEdit.setOnClickListener { onEditClick(faculty) }

            // 🔹 Delete button click callback
            binding.btnDelete.setOnClickListener { onDeleteClick(faculty) }
        }
    }

    /**
     * 🔹 Create ViewHolder and inflate layout
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FacultyViewHolder {
        val binding = ItemFacultyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FacultyViewHolder(binding)
    }

    /**
     * 🔹 Bind data to ViewHolder
     */
    override fun onBindViewHolder(holder: FacultyViewHolder, position: Int) {
        holder.bind(getItem(position)) // 🔹 Get item from ListAdapter
    }
}

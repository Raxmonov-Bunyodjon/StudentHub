package com.example.studenthub.ui.main.student

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.studenthub.data.local.StudentWithFaculty
import com.example.studenthub.databinding.ItemStudentBinding

/**
 * 🔹 StudentAdapter
 *
 * RecyclerView Adapter to display a list of students along with their faculty and direction.
 * Provides three callbacks: item click, edit click, and delete click.
 */
class StudentAdapter(
    private val onClick: (StudentWithFaculty) -> Unit,
    private val onEditClick: (StudentWithFaculty) -> Unit,
    private val onDeleteClick: (StudentWithFaculty) -> Unit
) : ListAdapter<StudentWithFaculty, StudentAdapter.StudentViewHolder>(DiffCallback) {

    /**
     * 🔹 ViewHolder class
     * Binds each item to the UI using view binding.
     */
    inner class StudentViewHolder(val binding: ItemStudentBinding) :
        RecyclerView.ViewHolder(binding.root)

    /**
     * 🔹 onCreateViewHolder
     * Inflates the item layout and creates the ViewHolder
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val binding = ItemStudentBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return StudentViewHolder(binding)
    }

    /**
     * 🔹 onBindViewHolder
     * Binds the student data to the UI elements for each item
     */
    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val student = getItem(position)

        // 🔹 Set student full name
        holder.binding.tvStudentName.text = "${student.firstName} ${student.lastName}"
        // 🔹 Set faculty name
        holder.binding.tvStudentFaculty.text = student.facultyName
        // 🔹 Set student direction
        holder.binding.tvStudentDirection.text = student.direction

        // 🔹 Item click listener
        holder.itemView.setOnClickListener { onClick(student) }
        // 🔹 Edit button click listener
        holder.binding.btnEditStudent.setOnClickListener { onEditClick(student) }
        // 🔹 Delete button click listener
        holder.binding.btnDeleteStudent.setOnClickListener { onDeleteClick(student) }
    }

    /**
     * 🔹 DiffCallback
     * DiffUtil.ItemCallback for optimizing RecyclerView updates
     * - areItemsTheSame → compares only IDs
     * - areContentsTheSame → compares the entire object
     */
    companion object DiffCallback : DiffUtil.ItemCallback<StudentWithFaculty>() {
        override fun areItemsTheSame(oldItem: StudentWithFaculty, newItem: StudentWithFaculty): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: StudentWithFaculty, newItem: StudentWithFaculty): Boolean =
            oldItem == newItem
    }
}

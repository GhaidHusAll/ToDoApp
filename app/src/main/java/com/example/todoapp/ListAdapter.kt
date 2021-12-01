package com.example.todoapp

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.text.InputType
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.databinding.ToDoRowsBinding


class ListAdapter (private var list: ArrayList<Task>,private val theContext: Context): RecyclerView.Adapter<ListAdapter.ItemViewHolder>() {

    class ItemViewHolder(val binding: ToDoRowsBinding): RecyclerView.ViewHolder(binding.root)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            ToDoRowsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }
    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val task = list[position]
        holder.binding.apply {
            tvTask.text = task.taskName
            if (task.isDone){
                ckIsDone.isChecked = true
                tvTask.setTextColor(Color.GRAY)
            }else {
                ckIsDone.isChecked = false
                tvTask.setTextColor(Color.BLACK)

            }
        }
        holder.binding.ckIsDone.setOnClickListener {
           task.isDone =  !task.isDone
            this.notifyDataSetChanged()
        }
        holder.itemView.setOnClickListener {
            val alertBuilder = android.app.AlertDialog.Builder(theContext)
            val taskInput = EditText(theContext)
            taskInput.setText(task.taskName)
            taskInput.hint = "Edit Task"
            taskInput.inputType = InputType.TYPE_CLASS_TEXT
            alertBuilder.setView(taskInput)
            alertBuilder.setPositiveButton("Edit") { _, _ ->
                task.taskName = taskInput.text.toString()
                this.notifyDataSetChanged()
                Toast.makeText(theContext, "Task Edited!", Toast.LENGTH_LONG).show()

            }
            alertBuilder.setNeutralButton("Delete") { _, _ ->
                list.removeAt(position)
                this.notifyDataSetChanged()
                Toast.makeText(theContext, "Task Deleted!", Toast.LENGTH_LONG).show()

            }
            alertBuilder.setNegativeButton("Close") { dialog, _ ->
                dialog.cancel()
            }
            val alert = alertBuilder.create()
            alert.setTitle("Add New Task")
            alert.show()

        }
    }
    override fun getItemCount() = list.size
}
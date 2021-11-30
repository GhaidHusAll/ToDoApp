package com.example.todoapp

import android.app.AlertDialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todoapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
 private lateinit var adapter : ListAdapter
 private lateinit var list : ArrayList<Task>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        list = arrayListOf()
        list.add(Task("shopping",true))
        list.add(Task("shopping2",false))

        Toast.makeText(this, "Hello there!", Toast.LENGTH_LONG).show()
        adapter = ListAdapter(list,this)
        binding.rvToDo.adapter = adapter
        binding.rvToDo.layoutManager = LinearLayoutManager(this)
        binding.floatingActionButton.setOnClickListener {
          AlertDialog(adapter)
        }
    }
    private fun AlertDialog(adapter:ListAdapter){
        val alertBuilder = AlertDialog.Builder(this)
        val taskInput = EditText(this)
        taskInput.hint = "Enter New Task"
        taskInput.inputType = InputType.TYPE_CLASS_TEXT
        alertBuilder.setView(taskInput)
        alertBuilder.setPositiveButton("Add", DialogInterface.OnClickListener { _, _ ->
            list.add(Task(taskInput.text.toString(),false))
            adapter?.notifyDataSetChanged()

        })
        alertBuilder.setNegativeButton("Close", DialogInterface.OnClickListener {
                dialog, _ -> dialog.cancel()
        })
        val alert = alertBuilder.create()
        alert.setTitle("Add New Task")
        alert.show()
    }

}
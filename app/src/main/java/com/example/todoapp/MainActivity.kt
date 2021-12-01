package com.example.todoapp

import android.annotation.SuppressLint
import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todoapp.databinding.ActivityMainBinding

import java.util.ArrayList


class MainActivity : AppCompatActivity() {
 private lateinit var adapter : ListAdapter
 private lateinit var list : ArrayList<Task>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        list = arrayListOf()
        list.add(Task("1",false))
        list.add(Task("2",true))
        list.add(Task("3",false))
        list.add(Task("4",true))

        Toast.makeText(this, "Hello there!", Toast.LENGTH_LONG).show()
        adapter = ListAdapter(list,this)
        binding.rvToDo.adapter = adapter
        binding.rvToDo.layoutManager = LinearLayoutManager(this)
        binding.floatingActionButton.setOnClickListener {
          alertDialog(adapter)
        }
    }


    @SuppressLint("NotifyDataSetChanged")
    private fun alertDialog(adapter:ListAdapter){
        val alertBuilder = AlertDialog.Builder(this)
        val taskInput = EditText(this)
        taskInput.hint = "Enter New Task"
        taskInput.inputType = InputType.TYPE_CLASS_TEXT
        alertBuilder.setView(taskInput)
        alertBuilder.setPositiveButton("Add") { _, _ ->
            list.add(Task(taskInput.text.toString(), false))
            adapter.notifyDataSetChanged()
            Toast.makeText(this, "Task Added!", Toast.LENGTH_LONG).show()


        }
        alertBuilder.setNegativeButton("Close") { dialog, _ ->
            dialog.cancel()
        }
        val alert = alertBuilder.create()
        alert.setTitle("Add New Task")
        alert.show()
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.imDelete -> {
               deleteAllCheckedItem()
                return true
            }

        }
        return super.onOptionsItemSelected(item)
    }
    @SuppressLint("NotifyDataSetChanged")
    private fun deleteAllCheckedItem(){
        var count = 0
        for (item in  list){
            if (item.isDone){
                count++
            }
        }
        list.removeAll{item -> item.isDone}
        if (count > 0) {
            Toast.makeText(this, "Deleted $count Tasks!", Toast.LENGTH_LONG).show()
        }else{
            Toast.makeText(this, "No Tasks to Delete!", Toast.LENGTH_LONG).show()

        }
        adapter.notifyDataSetChanged()

    }
}
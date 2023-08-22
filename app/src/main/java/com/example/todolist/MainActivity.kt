package com.example.todolist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.databinding.ActivityMainBinding
import com.example.todolist.databinding.TaskBinding
import com.example.todolist.model.Task

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityMainBinding
    private val tasks = mutableListOf<Task>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        handleList()
        listeners()
    }

    override fun onClick(view: View) {
        if (view.id == R.id.image_button_add) {
            // handleAddTask()
        }
    }

    private fun listeners() {
        binding.imageButtonAdd.setOnClickListener(this)
    }

    private fun handleList() {
        binding.recyclerTasks.layoutManager = LinearLayoutManager(this)
        binding.recyclerTasks.adapter = TasksAdapter(tasks)


        // Lista vazia
        // adapter.updatedTasks(items)
        // val emptyObserver = EmptyObserver(binding.recyclerTasks, binding.emptyContainer.root)
        // adapter.registerAdapterDataObserver(emptyObserver)
    }

    private inner class TasksAdapter(
        private val tasks: List<Task>
    ) : RecyclerView.Adapter<TasksAdapter.TaskViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
            val view = TaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)

            return TaskViewHolder(view)
        }

        override fun getItemCount(): Int {
            return 60
        }

        override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
            //val task = tasks[position]
            //holder.bind(task)
        }

        inner class TaskViewHolder(private val bind: TaskBinding) : RecyclerView.ViewHolder(bind.root) {
            fun bind(task: Task) {
                bind.checkboxTask.text = task.description
            }
        }
    }
}
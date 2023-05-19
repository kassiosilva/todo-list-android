package com.example.todolist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.databinding.TaskBinding
import com.example.todolist.viewholder.TaskViewHolder

class TasksAdapter : RecyclerView.Adapter<TaskViewHolder>() {
    private var tasksList: List<String> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val item = TaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return TaskViewHolder(item)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(tasksList[position])
    }

    override fun getItemCount(): Int {
        return tasksList.count()
    }

    fun updatedTasks(list: List<String>) {
        tasksList = list
        notifyDataSetChanged()
    }
}
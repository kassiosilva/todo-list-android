package com.example.todolist.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.databinding.TaskBinding

class TaskViewHolder(private val bind: TaskBinding) : RecyclerView.ViewHolder(bind.root) {
    fun bind(task: String) {
        bind.checkboxTask.text = task
    }
}
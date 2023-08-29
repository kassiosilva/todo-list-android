package com.example.todolist

import android.content.Context
import android.graphics.Paint
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.ShapeDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.databinding.ActivityMainBinding
import com.example.todolist.databinding.TaskBinding
import com.example.todolist.model.Task

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: TasksAdapter
    private val tasks = mutableListOf<Task>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        handleList()
        listeners()
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.image_button_add -> handleAddTask()
        }
    }

    private fun listeners() {
        binding.imageButtonAdd.setOnClickListener(this)

        val editNewTask = binding.editNewTask

        editNewTask.setOnEditorActionListener { _, actionId, _ ->
            return@setOnEditorActionListener when (actionId) {
                EditorInfo.IME_ACTION_DONE -> {
                    handleAddTask()
                    true
                }

                else -> false
            }
        }
    }

    private fun handleList() {
        adapter = TasksAdapter(tasks)

        binding.recyclerTasks.layoutManager = LinearLayoutManager(this)
        binding.recyclerTasks.adapter = adapter


        // Lista vazia
        // adapter.updatedTasks(items)
        // val emptyObserver = EmptyObserver(binding.recyclerTasks, binding.emptyContainer.root)
        // adapter.registerAdapterDataObserver(emptyObserver)
    }

    private fun handleTasksCompleted() {
        val tasksCompleted = tasks.filter { it.isCompleted }.size

        binding.textCompletedTasksValue.text = tasksCompleted.toString()
    }

    private fun handleAddTask() {
        val editNewTask = binding.editNewTask
        val description = editNewTask.text.toString()

        if (description.isEmpty()) {
            Toast.makeText(this, "Preencha o campo", Toast.LENGTH_SHORT).show()
            return
        }

        val task = Task(description)

        tasks.add(task)
        adapter.notifyDataSetChanged()

        val keyboardService = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        keyboardService.hideSoftInputFromWindow(currentFocus?.windowToken, 0)

        editNewTask.text.clear()
        editNewTask.clearFocus()
        binding.textTasksCreatedValue.text = tasks.size.toString()
    }

    private fun handleRemove(item: TaskBinding, position: Int) {
        tasks.removeAt(position)
        adapter.notifyItemRemoved(position)

        binding.textTasksCreatedValue.text = tasks.size.toString()

        if (item.checkboxTask.isChecked) {
            handleTasksCompleted()

            item.checkboxTask.paintFlags =
                item.checkboxTask.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            item.checkboxTask.setTextColor(ContextCompat.getColor(this, R.color.gray_100))

            val backgroundDrawable = ContextCompat.getDrawable(this, R.drawable.task_item_container)
            item.root.background = backgroundDrawable?.mutate()
        }
    }

    private fun handleChecked(item: TaskBinding, position: Int) {
        val checkBox = item.checkboxTask

        tasks[position].isCompleted = checkBox.isChecked

        handleTasksCompleted()

        val backgroundDrawable = ContextCompat.getDrawable(this, R.drawable.task_item_container)
        backgroundDrawable?.mutate()

        if (checkBox.isChecked) {
            backgroundDrawable?.setTint(ContextCompat.getColor(this, R.color.gray_500))

            item.root.background = backgroundDrawable
            checkBox.paintFlags = checkBox.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG;
            checkBox.setTextColor(ContextCompat.getColor(this, R.color.gray_300))

            return
        }

        item.root.background = backgroundDrawable
        checkBox.paintFlags = checkBox.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
        checkBox.setTextColor(ContextCompat.getColor(this, R.color.gray_100))
    }

    private inner class TasksAdapter(
        private val tasks: List<Task>
    ) : RecyclerView.Adapter<TasksAdapter.TaskViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
            val view = TaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)

            return TaskViewHolder(view)
        }

        override fun getItemCount(): Int {
            return tasks.size
        }

        override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
            val task = tasks[position]
            holder.bind(task)
        }

        inner class TaskViewHolder(private val bind: TaskBinding) :
            RecyclerView.ViewHolder(bind.root) {

            fun bind(task: Task) {
                val checkboxTask = bind.checkboxTask

                checkboxTask.text = task.description
                checkboxTask.isChecked = task.isCompleted

                bind.imageButtonDelete.setOnClickListener {
                    handleRemove(bind, adapterPosition)
                }

                checkboxTask.setOnClickListener {
                    handleChecked(bind, adapterPosition)
                }
            }
        }
    }
}
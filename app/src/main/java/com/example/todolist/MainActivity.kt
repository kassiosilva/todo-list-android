package com.example.todolist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.adapter.TasksAdapter
import com.example.todolist.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private var adapter = TasksAdapter()
    private lateinit var binding: ActivityMainBinding
    private val items = listOf<String>("TESTE")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        handleList()

        listeners()
    }

    override fun onClick(view: View) {
        if (view.id == R.id.image_button_add) {
        }
    }

    private fun listeners() {
        binding.imageButtonAdd.setOnClickListener(this)
    }

    private fun handleList() {
        binding.recyclerTasks.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        binding.recyclerTasks.adapter = adapter

        adapter.updatedTasks(items)

        val emptyObserver = EmptyObserver(binding.recyclerTasks, binding.emptyContainer.root)

        adapter.registerAdapterDataObserver(emptyObserver)
    }
}
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
import com.example.todolist.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var adapter: TmpAdapter
    private lateinit var binding: ActivityMainBinding
    private val items = arrayListOf("Olá", "Mundo", "Hello", "World")

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
            handleAddTask()
        }
    }

    private fun listeners() {
        binding.imageButtonAdd.setOnClickListener(this)
    }

    private fun handleAddTask() {
        items.clear()
        adapter.notifyDataSetChanged()
        Toast.makeText(this, "TESTE", Toast.LENGTH_SHORT).show()
    }

    private fun handleList() {
        val recyclerView = binding.recyclerTasks
        val emptyList = binding.emptyContainer

        recyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        adapter = TmpAdapter(items)
        recyclerView.adapter = adapter

        val emptyObserver = EmptyObserver(recyclerView, emptyList.root)

        adapter.registerAdapterDataObserver(emptyObserver)
    }

    private class TmpAdapter(val items: List<String>) : RecyclerView.Adapter<TmpAdapter.TmpView>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TmpView {
            val view = LayoutInflater.from(parent.context)
                .inflate(android.R.layout.simple_list_item_1, parent, false)

            return TmpView(view)
        }

        override fun getItemCount(): Int {
            return items.size
        }

        override fun onBindViewHolder(holder: TmpView, position: Int) {
            (holder.itemView as TextView).text = items[position]
        }

        private class TmpView(itemView: View) : RecyclerView.ViewHolder(itemView) {

        }
    }
}
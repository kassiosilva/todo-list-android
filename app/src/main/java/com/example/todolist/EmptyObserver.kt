package com.example.todolist

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.databinding.EmptyListBinding

class EmptyObserver(
    private val recyclerView: RecyclerView,
    private val emptyView: View
) : RecyclerView.AdapterDataObserver() {

    init {
        checkEmpty()
    }

    private fun checkEmpty() {
        if (recyclerView.adapter!!.itemCount == 0) {
            emptyView.visibility = View.VISIBLE
            recyclerView.visibility = View.GONE
        } else {
            emptyView.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
        }
    }

    override fun onChanged() {
        super.onChanged()
        checkEmpty()
    }
}
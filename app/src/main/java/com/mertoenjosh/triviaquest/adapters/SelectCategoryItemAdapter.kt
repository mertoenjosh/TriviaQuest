package com.mertoenjosh.triviaquest.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mertoenjosh.triviaquest.R

class SelectCategoryItemAdapter(private val context: Context, private val list: ArrayList<String>):
    RecyclerView.Adapter<SelectCategoryItemAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_select_category, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val category = list[position];

        holder.bind(position, category)
    }

    override fun getItemCount(): Int = list.size

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {

        fun bind(position: Int, category: String) {
            TODO("Bind the image button with images")
        }

    }
}

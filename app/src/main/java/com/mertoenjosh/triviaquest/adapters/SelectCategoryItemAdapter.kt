package com.mertoenjosh.triviaquest.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mertoenjosh.triviaquest.R

class SelectCategoryItemAdapter(
    private val context: Context,
    private val list: ArrayList<String>,
    private val icons: ArrayList<Int>,
):
    RecyclerView.Adapter<SelectCategoryItemAdapter.ViewHolder>(){

    private var onItemClickListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(position: Int, category: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_select_category, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val category = list[position]
        val icon = icons[position]
        holder.bind(position, category, icon)
    }

    override fun getItemCount(): Int = list.size

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val tvCategoryName: TextView = view.findViewById(R.id.tvCategoryName)
        private val ivCategory: ImageView = view.findViewById(R.id.ivCategory)

        fun bind(position: Int, category: String, icon: Int) {
            tvCategoryName.text = category
            ivCategory.setImageResource(icon)

            this@ViewHolder.itemView.setOnClickListener {
                if (onItemClickListener != null) {
                    onItemClickListener!!.onItemClick(position, category)
                }
            }
        }

    }
}

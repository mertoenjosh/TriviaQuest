package com.mertoenjosh.triviaquest.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mertoenjosh.triviaquest.R
import com.mertoenjosh.triviaquest.adapters.SelectCategoryItemAdapter
import com.mertoenjosh.triviaquest.utils.Constants

class CategoryActivity : BaseActivity() {
    private lateinit var toolbarCategories: Toolbar
    private lateinit var rvSelectCategory: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)
        toolbarCategories = findViewById(R.id.toolbarCategories)
        setupToolbar(toolbarCategories, getString(R.string.title_categories))
        rvSelectCategory = findViewById(R.id.rvSelectCategory)
        setupRV(rvSelectCategory, categories())
    }

    private fun setupRV(rv: RecyclerView, categories: ArrayList<String>) {
        rv.layoutManager = GridLayoutManager(this, 2)
        val adapter = SelectCategoryItemAdapter(this, categories)
        rv.adapter = adapter

        adapter.setOnItemClickListener( object : SelectCategoryItemAdapter.OnItemClickListener {
            override fun onItemClick(position: Int, category: String) {
                val intent = Intent(this@CategoryActivity, DifficultyActivity::class.java)
                intent.putExtra(Constants.EXTRA_CATEGORY, category)
                startActivity(intent)
            }
        })
    }

    private fun categories(): ArrayList<String> {
        val categories: ArrayList<String> = ArrayList()
        categories.add("Arts & Literature")
        categories.add("Film & TV")
        categories.add("Food & Drink")
        categories.add("General & Knowledge")
        categories.add("Geography")
        categories.add("History")
        categories.add("Music")
        categories.add("Science")
        categories.add("Society and Culture")
        categories.add("Sport and Leisure")
        return categories
    }
}
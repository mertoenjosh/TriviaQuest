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
        setupRV(rvSelectCategory, categories(), icons())
    }

    private fun setupRV(rv: RecyclerView, categories: ArrayList<String>, icons: ArrayList<Int>) {
        rv.layoutManager = GridLayoutManager(this, 2)
        val adapter = SelectCategoryItemAdapter(this, categories, icons)
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
        categories.add(0,"Arts & Literature")
        categories.add(1, "Film & TV")
        categories.add(2, "Food & Drink")
        categories.add(3, "General Knowledge")
        categories.add(4, "Geography")
        categories.add(5, "History")
        categories.add(6, "Music")
        categories.add(7, "Science")
        categories.add(8, "Society & Culture")
        categories.add(9, "Sport & Leisure")
        return categories
    }

    private fun icons(): ArrayList<Int> {
        val icons: ArrayList<Int> = ArrayList()

        icons.add(0, R.drawable.ic_book_24)
        icons.add(1, R.drawable.ic_movie_24)
        icons.add(2, R.drawable.ic_foods_24)
        icons.add(3, R.drawable.ic_general_24)
        icons.add(4, R.drawable.ic_geo_24)
        icons.add(5, R.drawable.ic_history_edu_24)
        icons.add(6, R.drawable.ic_music_note_24)
        icons.add(7, R.drawable.ic_biotech_24)
        icons.add(8, R.drawable.ic_culture_24)
        icons.add(9, R.drawable.ic_sports_24)

        return icons
    }
}
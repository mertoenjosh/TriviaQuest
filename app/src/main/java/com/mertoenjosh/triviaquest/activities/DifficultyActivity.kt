package com.mertoenjosh.triviaquest.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import com.mertoenjosh.triviaquest.R
import com.mertoenjosh.triviaquest.utils.Constants

class DifficultyActivity : BaseActivity(), View.OnClickListener {
    private lateinit var toolbarDifficulty: Toolbar
    private lateinit var tvDifficultyEasy: TextView
    private lateinit var tvDifficultyMedium: TextView
    private lateinit var tvDifficultyHard: TextView
    private lateinit var category: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_difficulty)
        toolbarDifficulty = findViewById(R.id.toolbarDifficulty)
        category = intent?.getStringExtra(Constants.EXTRA_CATEGORY) ?: getString(R.string.title_difficulty)
        setupToolbar(toolbarDifficulty, category)

        tvDifficultyEasy = findViewById(R.id.tvDifficultyEasy)
        tvDifficultyMedium = findViewById(R.id.tvDifficultyMedium)
        tvDifficultyHard = findViewById(R.id.tvDifficultyHard)

        tvDifficultyEasy.setOnClickListener(this)
        tvDifficultyMedium.setOnClickListener(this)
        tvDifficultyHard.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        val intent = Intent(this, FetchActivity::class.java)
        intent.putExtra(Constants.EXTRA_CATEGORY, category)

        when (v) {
            tvDifficultyEasy -> {
                intent.putExtra(Constants.EXTRA_DIFFICULTY, Constants.EASY)
                startActivity(intent)
            }

            tvDifficultyMedium -> {
                intent.putExtra(Constants.EXTRA_DIFFICULTY, Constants.MEDIUM)
                startActivity(intent)
            }

            tvDifficultyHard -> {
                intent.putExtra(Constants.EXTRA_DIFFICULTY, Constants.HARD)
                startActivity(intent)
            }
        }
    }

}
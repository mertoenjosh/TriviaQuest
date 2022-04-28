package com.mertoenjosh.triviaquest.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.widget.Toolbar
import com.mertoenjosh.triviaquest.R
import com.mertoenjosh.triviaquest.data.FetchQuestionsFromAPI
import com.mertoenjosh.triviaquest.utils.Constants

class FetchActivity : BaseActivity() {
    private lateinit var toolbarFetchActivity: Toolbar
    private lateinit var btnStartQuiz: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fetch)
        toolbarFetchActivity = findViewById(R.id.toolbarFetchActivity)
        btnStartQuiz = findViewById(R.id.btnStartQuiz)
        setupToolbar(toolbarFetchActivity,"BEGIN")
        val category = intent.getStringExtra(Constants.EXTRA_CATEGORY) ?: Constants.DEFAULT_CATEGORY
        val difficulty = intent.getStringExtra(Constants.EXTRA_DIFFICULTY) ?: Constants.EASY

        FetchQuestionsFromAPI(this, makeString(category), difficulty).execute()

        val intent = Intent(this, QuizActivity::class.java).apply {
            putExtra(Constants.EXTRA_CATEGORY, category)
        }
        btnStartQuiz.setOnClickListener { startActivity(intent); finish() }
    }

    private fun makeString(str: String): String = str.lowercase()
            .split(" ")
            .joinToString("_")
            .replace("&", "and")

}
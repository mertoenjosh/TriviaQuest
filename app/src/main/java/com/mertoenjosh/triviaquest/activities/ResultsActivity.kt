package com.mertoenjosh.triviaquest.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.mertoenjosh.triviaquest.R
import com.mertoenjosh.triviaquest.utils.Constants

class ResultsActivity : AppCompatActivity() {
    private lateinit var tvDisplayResults: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_results)
        tvDisplayResults = findViewById(R.id.tvDisplayResults)
        val result = intent.getIntExtra(Constants.EXTRA_CORRECT_ANSWERS, -1)
        tvDisplayResults.text = "You scored $result out of  ${Constants.QUESTIONS.size} questions"

    }
}
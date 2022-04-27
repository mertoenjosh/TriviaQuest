package com.mertoenjosh.triviaquest.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.mertoenjosh.triviaquest.R

class MainActivity : AppCompatActivity() {
    private lateinit var btnStart: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnStart = findViewById(R.id.btnStart)

        val intent = Intent(this, CategoryActivity::class.java)
        btnStart.setOnClickListener {
            startActivity(intent)
        }

        // FetchQuestionsFromAPI(this).execute()
    }
}
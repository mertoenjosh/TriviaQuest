package com.mertoenjosh.triviaquest.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.mertoenjosh.triviaquest.R
import com.mertoenjosh.triviaquest.utils.Constants

class IntroActivity : AppCompatActivity() {
    private lateinit var btnStart: Button
    private lateinit var etName: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)
        btnStart = findViewById(R.id.btnStart)
        etName = findViewById(R.id.etName)

        // DEV hack
        startActivity(Intent(this, CategoryActivity::class.java))


        val intent = Intent(this, CategoryActivity::class.java)
        btnStart.setOnClickListener {
            if (etName.text.toString().isNotEmpty()) {
                Constants.PLAYER_NAME = etName.text.toString()
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, getString(R.string.enter_name_error), Toast.LENGTH_SHORT).show()
            }

        }

        // FetchQuestionsFromAPI(this).execute()
    }
}
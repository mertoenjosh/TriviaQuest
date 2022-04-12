package com.mertoenjosh.triviaquest.activities

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.mertoenjosh.triviaquest.FetchQuestionsFromAPI
import com.mertoenjosh.triviaquest.R

class MainActivity : AppCompatActivity() {
    private lateinit var progressDialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        FetchQuestionsFromAPI(this).execute()
    }

    fun showProgressDialog(text: String) {
        progressDialog = Dialog(this)

        progressDialog.setContentView(R.layout.custom_progress_dialog)
        progressDialog.findViewById<TextView>(R.id.tvPleaseWait).text = text

        progressDialog.show()
    }

    fun hideProgressDialog() {
        progressDialog.cancel()
        progressDialog.dismiss()
    }

}
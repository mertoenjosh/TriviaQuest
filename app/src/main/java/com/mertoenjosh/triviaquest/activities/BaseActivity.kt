package com.mertoenjosh.triviaquest.activities

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import com.mertoenjosh.triviaquest.R

open class BaseActivity : AppCompatActivity() {
    private lateinit var progressDialog: Dialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    fun setupToolbar(toolbar: Toolbar, title: String?, icon: Int? = null) {
        setSupportActionBar(toolbar)

        when (val drawable = icon ?: R.drawable.ic_back) {
            R.drawable.ic_back -> {
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
                supportActionBar?.setHomeAsUpIndicator(drawable)
                toolbar.setNavigationOnClickListener { onBackPressed() }
            }
        }
        supportActionBar?.title = title
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
package com.mertoenjosh.triviaquest.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.widget.Toolbar
import com.mertoenjosh.triviaquest.R
import com.mertoenjosh.triviaquest.models.Question
import com.mertoenjosh.triviaquest.network.QuestService
import com.mertoenjosh.triviaquest.utils.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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

        getQuestions(makeString(category), 5, difficulty)

        val intent = Intent(this, QuizActivity::class.java).apply {
            putExtra(Constants.EXTRA_CATEGORY, category)
        }
        btnStartQuiz.setOnClickListener { startActivity(intent); finish() }
    }

    private fun makeString(str: String): String = str.lowercase()
            .split(" ")
            .joinToString("_")
            .replace("&", "and")

    private fun getQuestions(categories: String, limit: Int, difficulty: String) {
        val isNetworkIsAvailable = true

        // a retrofit object
        // TODO: Check for network connection util
        if (isNetworkIsAvailable) {
            val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            // create a service for your interface
            val service: QuestService = retrofit.create(QuestService::class.java)

            // call the method in the service interface
            val listCall = service.getQuiz(categories = categories, limit = limit, difficulty = difficulty)
            this.showProgressDialog("Please wait ...")
            listCall.enqueue(object : Callback<ArrayList<Question>> {
                override fun onResponse(
                    call: Call<ArrayList<Question>>,
                    response: Response<ArrayList<Question>>
                ) {
                    this@FetchActivity.hideProgressDialog()
                    if (response.isSuccessful) {
                        val questions = response.body()
                        Log.i(TAG, "onResponse: $questions")
                        Constants.QUESTIONS = questions!!
                    } else {
                        val rc = response.code()
                        val rm = response.message()

                        when (rc) {
                            400 -> Log.e("Error 400", rm)
                            404 -> Log.e("Error 404", rm)

                            else -> Log.e("Error $rc", rm)
                        }
                    }
                }

                override fun onFailure(call: Call<ArrayList<Question>>, t: Throwable) {
                    this@FetchActivity.hideProgressDialog()
                    Log.e("ERRORRRRR", t.message.toString())
                }


            })

        } else {
            // TODO: Handle offline situations
        }

    }
    companion object {
        private const val TAG = "FetchActivityTAG"
    }
}
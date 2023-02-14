package com.mertoenjosh.triviaquest.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.mertoenjosh.triviaquest.R
import com.mertoenjosh.triviaquest.models.Question
import com.mertoenjosh.triviaquest.services.QuestionService
import com.mertoenjosh.triviaquest.services.ServiceBuilder
import com.mertoenjosh.triviaquest.utils.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException


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

        val isFetchComplete: Boolean = getQuestions(makeString(category), 5, difficulty)

        val intent = Intent(this, QuizActivity::class.java).apply {
            putExtra(Constants.EXTRA_CATEGORY, category)
        }
        btnStartQuiz.setOnClickListener {
            if (isFetchComplete) {
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this@FetchActivity,
                    "Something went wrong",
                    Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun makeString(str: String): String = str.lowercase()
            .split(" ")
            .joinToString("_")
            .replace("&", "and")

    // TODO Handle errors properly and return a boolean
    private fun getQuestions(categories: String, limit: Int, difficulty: String): Boolean {
        if (Constants.isNetworkAvailable(this)) {
            // create a service for your interface
            val service = ServiceBuilder().buildService(QuestionService::class.java)
            // call the method in the service interface
//            val listCall = service.getQuestions(categories = categories, limit = limit, difficulty = difficulty)
            val filters = HashMap<String, Any>()
            filters["categories"] = categories
            filters["limit"] = limit
            filters["difficulty"] = difficulty
            val listCall = service.getQuestions(filters)
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
                    if (t is IOException) {
                        Toast.makeText(this@FetchActivity, "A connection error occurred",Toast.LENGTH_SHORT).show()
                        Log.e("ERRORRRRR", t.message.toString())
                    } else {
                        Log.e("ERRORRRRR", t.message.toString())
                    }
                    return
                }
            })
        } else {
            Toast.makeText(this@FetchActivity,
                "Looks like you are offline. Please check your internet connection",
                Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }
    companion object {
        private const val TAG = "FetchActivityTAG"
    }
}
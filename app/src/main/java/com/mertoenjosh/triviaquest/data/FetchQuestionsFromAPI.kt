package com.mertoenjosh.triviaquest

import android.os.AsyncTask
import android.util.Log
import android.widget.Toast
import com.google.gson.Gson
import com.mertoenjosh.triviaquest.activities.MainActivity
import com.mertoenjosh.triviaquest.models.Question
import com.mertoenjosh.triviaquest.utils.Constants
import org.json.JSONArray
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.lang.Exception
import java.lang.StringBuilder
import java.net.HttpURLConnection
import java.net.SocketTimeoutException
import java.net.URL

class FetchQuestionsFromAPI(private val activity: MainActivity)
    : AsyncTask<Void, Any, String>() {
    companion object {
        const val TAG = "FetchQuestionsFrom"
    }
    override fun onPreExecute() {
        super.onPreExecute()
        activity.showProgressDialog("Please wait")
    }

    override fun doInBackground(vararg params: Void?): String {
        var result: String
        var connection: HttpURLConnection? = null

        try {
            val url = URL(Constants.URL_END_POINT)

            connection = url.openConnection() as HttpURLConnection
            result = readDataFromEndPoint(connection)

        }catch (e: SocketTimeoutException) {
            result = "Connection Timeout"
        }catch (e: Exception) {
            result = "Error: ${e.message}"
        }finally {
            connection?.disconnect()
        }

        return result
    }

    override fun onPostExecute(result: String?) {
        super.onPostExecute(result)
        activity.hideProgressDialog()

        if (result != null)
            returnedQuestions(result)
        else
            TODO ("implement a function to handle")
            Toast.makeText(activity, "data not received", Toast.LENGTH_SHORT).show()
    }

    private fun returnedQuestions(data: String): ArrayList<Question> {
        val questions: ArrayList<Question> = ArrayList()

        val questionsArray = JSONArray(data)

        (0 until questionsArray.length()).forEach {
            val obj =questionsArray.optJSONObject(it)
            val question = Gson().fromJson(obj.toString(), Question::class.java)
            questions.add(question)
            Log.d(TAG, "$question")
        }

        return questions
    }

    private fun readDataFromEndPoint(conn: HttpURLConnection): String {
        // set some properties for the connection
        conn.doOutput = true
        conn.doInput = true

        val httpResult = conn.responseCode

        if (httpResult == HttpURLConnection.HTTP_OK) {
            val inputStream = conn.inputStream

            val reader = BufferedReader(InputStreamReader(inputStream))

            val stringBuilder = StringBuilder()
            var line: String?

            try {
                while (reader.readLine().also { line = it } != null) {
                    stringBuilder.append("$line\n")
                }
            }catch (e: IOException) {
                e.printStackTrace()
            } finally {
                try {
                    inputStream.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
            return stringBuilder.toString()
        } else {
            return conn.responseMessage
        }
    }
}
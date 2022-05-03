package com.mertoenjosh.triviaquest.activities


import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.content.res.ResourcesCompat
import com.mertoenjosh.triviaquest.R
import com.mertoenjosh.triviaquest.utils.Constants

class QuizActivity : BaseActivity(), View.OnClickListener {
    private lateinit var toolbarQuiz: Toolbar
    private lateinit var tvQuestion: TextView
    private lateinit var tvChoiceOne: TextView
    private lateinit var tvChoiceTwo: TextView
    private lateinit var tvChoiceThree: TextView
    private lateinit var tvChoiceFour: TextView
    private lateinit var btnQuizSubmit: Button
    private lateinit var pbTimeOut: ProgressBar

    private  var answerTimer: CountDownTimer? = null

    private val questionsList = Constants.QUESTIONS
    private var currentQuestionIndex = 0
    private var selectedOption = -1
    private var indexOfCorrect = -1
    private var correctAnswers = 0

    private var countDownInterval = 40 // smoothness of the timeout progress
    private var timerDuration = 10
    private var maxProgress = (timerDuration * 1000) / countDownInterval

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)
        toolbarQuiz = findViewById(R.id.toolbarQuiz)
        tvQuestion = findViewById(R.id.tvQuestion)
        tvChoiceOne = findViewById(R.id.tvChoiceOne)
        tvChoiceTwo = findViewById(R.id.tvChoiceTwo)
        tvChoiceThree = findViewById(R.id.tvChoiceThree)
        tvChoiceFour = findViewById(R.id.tvChoiceFour)
        btnQuizSubmit = findViewById(R.id.btnQuizSubmit)
        pbTimeOut = findViewById(R.id.pbTimeOut)

        val category = intent.getStringExtra(Constants.EXTRA_CATEGORY) ?: Constants.DEFAULT_CATEGORY
        setupToolbar(toolbarQuiz, "$category challenge")

        if (!questionsList.isNullOrEmpty()) {
            setupQuiz()
            tvChoiceOne.setOnClickListener(this)
            tvChoiceTwo.setOnClickListener(this)
            tvChoiceThree.setOnClickListener(this)
            tvChoiceFour.setOnClickListener(this)
            btnQuizSubmit.setOnClickListener(this)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        resetTimer()
    }

    override fun onClick(v: View?) {
        when(v) {
            tvChoiceOne -> selectedOptionView(tvChoiceOne, 0)
            tvChoiceTwo -> selectedOptionView(tvChoiceTwo, 1)
            tvChoiceThree -> selectedOptionView(tvChoiceThree, 2)
            tvChoiceFour -> selectedOptionView(tvChoiceFour, 3)

            btnQuizSubmit -> {
                if (selectedOption == indexOfCorrect) {
                    correctAnswers++
                    Toast.makeText(this, "Correct", Toast.LENGTH_SHORT).show()
                    Log.d(TAG, "TEST: Answer: $indexOfCorrect, Choice: $selectedOption")
                    moveToNextQuestion()
                } else {
                    Toast.makeText(this, "Wrong", Toast.LENGTH_SHORT).show()
                    Log.d(TAG, "TEST: Answer: $indexOfCorrect, Choice: $selectedOption")
                    moveToNextQuestion()
                }
            }
        }
    }

    private fun startTimer() {
        pbTimeOut.max = maxProgress
        answerTimer = object : CountDownTimer(timerDuration * 1000L, countDownInterval.toLong()) {
            override fun onTick(millisUntilFinished: Long) {
                pbTimeOut.progress = (millisUntilFinished / countDownInterval).toInt()
                Log.d(TAG, "${millisUntilFinished / countDownInterval}")
            }

            override fun onFinish() {
                Toast.makeText(this@QuizActivity, "Time Out", Toast.LENGTH_SHORT).show()
                moveToNextQuestion()
            }

        }.start()
    }

    private fun resetTimer() {
        answerTimer?.cancel()
        answerTimer = null
        pbTimeOut.progress = maxProgress
    }

    private fun moveToNextQuestion() {
        resetTimer()
        currentQuestionIndex++
        Log.d(TAG, "PRE: Answer: $indexOfCorrect, Choice: $selectedOption")
        selectedOption = -1
        indexOfCorrect = -1
        Log.d(TAG, "POST: Answer: $indexOfCorrect, Choice: $selectedOption")
        if (currentQuestionIndex < questionsList.size) {
            if (currentQuestionIndex == questionsList.size - 1)
                btnQuizSubmit.text = getString(R.string.btn_final_question_text)
            setupQuiz()
        } else {
            val intent = Intent(this, ResultsActivity::class.java).apply {
                putExtra(Constants.EXTRA_CORRECT_ANSWERS, correctAnswers)
            }
            startActivity(intent)
            finish()
        }
    }

    private fun selectedOptionView(tv: TextView, choice: Int) {
        defaultOptionsView()
        selectedOption = choice

        tv.setTextColor(ResourcesCompat.getColor(resources, R.color.primary_text_color, null))
        tv.setTypeface(tv.typeface, Typeface.BOLD)
    }

    private fun setupQuiz() {
        startTimer()
        val question = questionsList[currentQuestionIndex]
        val list = question.incorrectAnswers + question.correctAnswer
        val choices = list.shuffled()
        indexOfCorrect = choices.indexOf(question.correctAnswer)
        defaultOptionsView()

        tvQuestion.text = question.question

        tvChoiceOne.text = choices[0]
        tvChoiceTwo.text = choices[1]
        tvChoiceThree.text = choices[2]
        tvChoiceFour.text = choices[3]

    }

    private fun defaultOptionsView() {
        val options = ArrayList<TextView>()

        options.add(tvChoiceOne)
        options.add(tvChoiceTwo)
        options.add(tvChoiceThree)
        options.add(tvChoiceFour)

        for (op in options) {
            // op.setTextColor(Color.parseColor((R.color.secondary_text_color).toString()))
            op.setTextColor(ResourcesCompat.getColor(resources, R.color.secondary_text_color, null))
            op.typeface = Typeface.DEFAULT
            // TODO set BG
        }
    }

    companion object {
        const val TAG = "QuizActivityTAG"
    }
}
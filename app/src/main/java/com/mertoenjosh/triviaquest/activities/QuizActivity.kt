package com.mertoenjosh.triviaquest.activities


import android.app.Dialog
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
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
    private lateinit var options: ArrayList<TextView> // Find a better way to do this

    private  var answerTimer: CountDownTimer? = null

    private val questionsList = Constants.QUESTIONS
    private var currentQuestionIndex = 0
    private var selectedOption = -1
    private var indexOfCorrect = -1
    private var correctAnswers = 0

    private var countDownInterval = 40 // smoothness of the timeout progress
    private var timerDuration = 15
    private var pauseOffset = 0
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
        confirmCancel(getString(R.string.confirm_cancel), getString(R.string.confirm_cancel_text))
        // super.onBackPressed()
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
                submitAnswer(getString(R.string.answer_wrong))
            }
        }
    }


    private fun setupQuiz() {
        Log.d(TAG, "setupQuiz: Setup Quiz")
        options = ArrayList()
        options.add(tvChoiceOne)
        options.add(tvChoiceTwo)
        options.add(tvChoiceThree)
        options.add(tvChoiceFour)

        btnQuizSubmit.isEnabled = true
        for (btn in options)
            btn.isEnabled = true

        pbTimeOut.visibility = View.VISIBLE
        startTimer(timerDuration)
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

    private fun moveToNextQuestion() {
        Log.d(TAG, "moveToNextQuestion: called")
        resetTimer()
        pbTimeOut.visibility = View.INVISIBLE
        selectedOption = -1
        indexOfCorrect = -1

        btnQuizSubmit.isEnabled = false
        for (btn in options)
            btn.isEnabled = false

        Handler().postDelayed({
            currentQuestionIndex++
            if (currentQuestionIndex < questionsList.size) {
                if (currentQuestionIndex == questionsList.size - 1)
                    btnQuizSubmit.text = getString(R.string.btn_final_question_text)

                setupQuiz()
                Log.d(TAG, "POST: Answer: $indexOfCorrect, Choice: $selectedOption")
            } else {
                val intent = Intent(this, ResultsActivity::class.java).apply {
                    putExtra(Constants.EXTRA_CORRECT_ANSWERS, correctAnswers)
                }
                startActivity(intent)
                finish()
            }
        }, 1500)
    }

    private fun submitAnswer(message: String) {
        Log.d(TAG, "submitAnswer: Submit")
        if (selectedOption == indexOfCorrect) {
            handleCorrect()
        } else {
            handleWrong(message)
        }
    }

    private fun handleWrong(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        if (selectedOption >= 0)
            options[selectedOption].background = ContextCompat.getDrawable(this, R.drawable.shape_wrong_option_background)
        Log.d(TAG, "TEST: Answer: $indexOfCorrect, Choice: $selectedOption")
        moveToNextQuestion()
    }

    private fun handleCorrect() {
        Log.d(TAG, "handleCorrect: Correct")
        correctAnswers++
        options[selectedOption].background = ContextCompat.getDrawable(this, R.drawable.shape_correct_option_background)
        Toast.makeText(this, getString(R.string.answer_correct), Toast.LENGTH_SHORT).show()
        Log.d(TAG, "TEST: Answer: $indexOfCorrect, Choice: $selectedOption")
        moveToNextQuestion()
    }



    private fun startTimer(offSet: Int) {
        pbTimeOut.max = maxProgress
        answerTimer = object : CountDownTimer(offSet * 1000L, countDownInterval.toLong()) {
            override fun onTick(millisUntilFinished: Long) {
                pbTimeOut.progress = (millisUntilFinished / countDownInterval).toInt()
                Log.d(TAG, "${millisUntilFinished / countDownInterval}")
            }

            override fun onFinish() {
                submitAnswer(getString(R.string.answer_timeout))
            }

        }.start()
    }


    private fun resetTimer() {
        Log.d(TAG, "resetTimer: Reset Timer")
        answerTimer?.cancel()
        answerTimer = null
        pbTimeOut.progress = maxProgress
    }


    private fun defaultOptionsView() {
        Log.d(TAG, "defaultOptionsView: Reset views")


        for (op in options) {
            // op.setTextColor(Color.parseColor((R.color.secondary_text_color).toString()))
            op.setTextColor(ResourcesCompat.getColor(resources, R.color.secondary_text_color, null))
            op.typeface = Typeface.DEFAULT
            op.background = ContextCompat.getDrawable(this, R.drawable.shape_option_border_background)
        }
    }

    private fun selectedOptionView(tv: TextView, choice: Int) {
        defaultOptionsView()
        Log.d(TAG, "selectedOptionView: selected $choice")
        selectedOption = choice
        tv.setTextColor(ResourcesCompat.getColor(resources, R.color.primary_text_color, null))
        tv.setTypeface(tv.typeface, Typeface.BOLD)
        tv.background = ContextCompat.getDrawable(this, R.drawable.shape_selected_option_border_background)
    }

    private fun confirmCancel(title: String, text: String) {
        val customDialog = Dialog(this)

        customDialog.setContentView(R.layout.dialog_confirm_cancel)
        val cancel = customDialog.findViewById<Button>(R.id.btnNo)
        val confirm = customDialog.findViewById<Button>(R.id.btnYes)
        val tvTitle = customDialog.findViewById<TextView>(R.id.tvTitle)
        val tvText = customDialog.findViewById<TextView>(R.id.tvText)

        tvTitle.text = title
        tvText.text = text

        confirm.setOnClickListener {
            finish()
            customDialog.cancel()
        }

        cancel.setOnClickListener {
            customDialog.cancel()
        }

        customDialog.show()
    }


    companion object {
        const val TAG = "QuizActivityTAG"
    }
}

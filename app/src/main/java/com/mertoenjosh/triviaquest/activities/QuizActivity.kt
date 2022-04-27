package com.mertoenjosh.triviaquest.activities


import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
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

    private val questionsList = Constants.QUESTIONS
    private var currentQuestionIndex = 0
    private var selectedOption = 0

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

    override fun onClick(v: View?) {
        when(v) {
            tvChoiceOne -> selectedOptionView(tvChoiceOne, 1)
            tvChoiceTwo -> selectedOptionView(tvChoiceTwo, 2)
            tvChoiceThree -> selectedOptionView(tvChoiceThree, 3)
            tvChoiceFour -> selectedOptionView(tvChoiceFour, 4)

            btnQuizSubmit -> {

            }
        }
    }

    private fun selectedOptionView(tv: TextView, choice: Int) {
        defaultOptionsView()
        selectedOption = choice

        tv.setTextColor(ResourcesCompat.getColor(resources, R.color.primary_text_color, null))
        tv.setTypeface(tv.typeface, Typeface.BOLD)
    }
    private fun setupQuiz() {
        val question = questionsList[currentQuestionIndex]
        val list = question.incorrectAnswers + question.correctAnswer
        val choices = list.shuffled()
        defaultOptionsView()

        tvQuestion.text = question.question

        tvChoiceOne.text = choices[0]
        tvChoiceTwo.text = choices[1]
        tvChoiceThree.text = choices[2]
        tvChoiceFour.text = choices[3]

        Log.i(TAG, "$choices")
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
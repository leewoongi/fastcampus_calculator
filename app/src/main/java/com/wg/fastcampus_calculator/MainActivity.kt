package com.wg.fastcampus_calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    private lateinit var expressionTextView: TextView
    private lateinit var resultTextView: TextView
    private var isOperator = false
    private var hasOperator = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initialized()
    }

    private fun initialized() {
        expressionTextView = findViewById(R.id.tv_expression)
        resultTextView = findViewById(R.id.tv_calculator_result)
    }

    fun clickedButton(view: View) {
        when(view.id){
            R.id.btn_zero -> clickedNumberButton("0")
            R.id.btn_one -> clickedNumberButton("1")
            R.id.btn_two -> clickedNumberButton("2")
            R.id.btn_three -> clickedNumberButton("3")
            R.id.btn_four -> clickedNumberButton("4")
            R.id.btn_five -> clickedNumberButton("5")
            R.id.btn_six -> clickedNumberButton("6")
            R.id.btn_seven -> clickedNumberButton("7")
            R.id.btn_eight -> clickedNumberButton("8")
            R.id.btn_nine-> clickedNumberButton("9")
            R.id.btn_minus -> clickedOperatorButton("-")
            R.id.btn_plus -> clickedOperatorButton("+")
            R.id.btn_multi -> clickedOperatorButton("*")
            R.id.btn_divider -> clickedOperatorButton("/")
            R.id.btn_remainder -> clickedOperatorButton("%")
        }
    }

    private fun clickedNumberButton(number: String){

        if(isOperator){
            expressionTextView.append(" ")
        }

        isOperator = false

        val expressionText = expressionTextView.text.split(" ")
        if(expressionText.isNotEmpty() && isOverExpressionTextSize(expressionText) ){
            Toast.makeText(this, "15자리 까지만 사용할 수 있습니다.", Toast.LENGTH_LONG).show()
            return
        }

        if(expressionText.last().isEmpty() && number == "0"){
            Toast.makeText(this, "0은 제일 앞에 올 수 없습니다.", Toast.LENGTH_LONG).show()
            return
        }

        expressionTextView.append(number)
    }

    private fun isOverExpressionTextSize(expressionText: List<String>): Boolean{
        if(expressionText.last().length >= 15){
            return true
        }
        return false
    }

    private fun clickedOperatorButton(operator: String){
        if(expressionTextView.text.isEmpty()){
            return
        }

        when{
            isOperator ->{
                val text = expressionTextView.text.toString()
                expressionTextView.text = text.dropLast(1)
            }

            hasOperator ->{
                Toast.makeText(this, "연산자는 한 번만 사용할 수 있습니다.", Toast.LENGTH_LONG).show()
            }
            else -> {
                expressionTextView.append(" $operator")
            }
        }

        val ssb = SpannableStringBuilder(expressionTextView.text)
        ssb.setSpan(
                ForegroundColorSpan(ContextCompat.getColor(this, R.color.sushi)),
                expressionTextView.text.length -1, expressionTextView.text.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        expressionTextView.text = ssb
        isOperator = true
        hasOperator = true
    }

    fun clickedClearButton(view: View) {

    }

    fun clickedCalculatorHistoryButton(view: View) {

    }

    fun clickedCalculatorResultButton(view: View) {

    }
}
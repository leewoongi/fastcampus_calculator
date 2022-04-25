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
import androidx.core.view.isVisible
import java.lang.NumberFormatException

class MainActivity : AppCompatActivity() {

    private lateinit var expressionTextView: TextView
    private lateinit var resultTextView: TextView
    private lateinit var historyLayout: View
    private lateinit var historyLinearLayout: View
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
        historyLayout = findViewById(R.id.cl_historyLayout)
        historyLinearLayout = findViewById(R.id.ll_historyLinearLayout)
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
        resultTextView.text = calculateExpression()

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
        expressionTextView.text = ""
        resultTextView.text = ""
        isOperator = false
        hasOperator = false
    }

    fun clickedCalculatorHistoryButton(view: View) {
        historyLayout.isVisible = true
        // 디비에서 모든 기록 가져오기
        // 뷰에 모든 기록 할당 하기

    }

    fun clickedCloseHistoryButton(view: View) {
        // 히스토리 레이아웃 만 삭제
        historyLayout.isVisible = false
    }

    fun clickedClearHistoryButton(view: View) {
        // 디비에 모든 기록 삭제
        // 뷰에서 모든 기록 삭제
    }

    fun clickedCalculatorResultButton(view: View) {
        val expressionTexts = expressionTextView.text.split(" ")

        if(expressionTextView.text.isEmpty() || expressionTexts.size == 1){
            return
        }

        if(expressionTexts.size != 3 && hasOperator){
            Toast.makeText(this, "아직 완성되지 않은 수식입니다.", Toast.LENGTH_LONG).show()
            return
        }

        if(expressionTexts[0].isNumber().not() || expressionTexts[2].isNumber().not()){
            Toast.makeText(this, "오류가 발생했습니다.", Toast.LENGTH_LONG).show()
            return
        }

        val expressionText = expressionTextView.text.toString()
        val resultText = calculateExpression()

        resultTextView.text = ""
        expressionTextView.text = resultText

        isOperator = false
        hasOperator = false
    }

    private fun calculateExpression(): String {
        val expressionTexts = expressionTextView.text.split(" ")
        if(hasOperator.not() || expressionTexts.size != 3){
            return ""
        } else if(expressionTexts[0].isNumber().not() || expressionTexts[2].isNumber().not()){
            return ""
        }

        val exp1 = expressionTexts[0].toBigInteger()
        val exp2 = expressionTexts[2].toBigInteger()
        val op = expressionTexts[1]

        return when(op){
            "+" -> (exp1 + exp2).toString()
            "-" -> (exp1 - exp2).toString()
            "x" -> (exp1 * exp2).toString()
            "/" -> (exp1 / exp2).toString()
            "%" -> (exp1 % exp2).toString()
            else -> ""

        }
    }
}

//확장 함수
fun String.isNumber(): Boolean{
    return try{
        this.toBigInteger()
        true
    } catch (e: NumberFormatException){
        false
    }
}
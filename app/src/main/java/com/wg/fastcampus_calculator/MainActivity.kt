package com.wg.fastcampus_calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun clickedNumberButton(view: View) {

    }

    fun clickedClearButton(view: View) {}
    fun clickedCalculatorHistoryButton(view: View) {}
    fun clickedCalculatorResultButton(view: View) {}
}
package com.example.acalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import net.objecthunter.exp4j.ExpressionBuilder

private val TAG = MainActivity::class.java.simpleName

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()

        button_1.setOnClickListener {
            Log.i(TAG, "Click on button 1")
            if (text_visor.text == "0") {
                text_visor.text = "1"
            } else {
                text_visor.append("1")
            }
        }

        button_plus.setOnClickListener {
            Log.i(TAG, "Click on button +")
            text_visor.append("+")
        }

        button_equals.setOnClickListener {
            Log.i(TAG, "Click on button =")
            val expression = ExpressionBuilder(text_visor.text.toString()).build()
            val result = expression.evaluate().toString()
            val resultParts = result.split(".")
            text_visor.text = if (resultParts[1].toInt() == 0) resultParts[0] else result
            Log.i(TAG, "The expression result is ${text_visor.text}")
        }
    }

}
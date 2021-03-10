package com.example.acalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import net.objecthunter.exp4j.ExpressionBuilder

private val TAG = MainActivity::class.java.simpleName

class MainActivity : AppCompatActivity() {

    private var flag = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()
    }

    fun onNumberButtonPressed(view: View) {
        val button = view as android.widget.Button
        val buttonValue = button.text
        val textVisorValue = text_visor.text.toString()
        Log.i(TAG, "Click on button $buttonValue")
        when(button.id) {
            button_0.id,
            button_1.id,
            button_2.id,
            button_3.id,
            button_4.id,
            button_5.id,
            button_6.id,
            button_7.id,
            button_8.id,
            button_9.id -> {
                if (textVisorValue == "0" || textVisorValue == "ERROR" || flag) {
                    text_visor.text = buttonValue
                } else {
                    text_visor.append(buttonValue)
                }
                flag = false
            }
        }
    }

    fun onOperationButtonPressed(view: View) {
        val button = view as android.widget.Button
        val buttonValue = button.text.toString()
        val textVisorValue = text_visor.text.toString()
        Log.i(TAG, "Click on button $buttonValue")
        when(button.id) {
            button_plus.id,
            button_minus.id,
            button_multiplier.id,
            button_divider.id,
            button_dot.id -> {
                if (checkForInvalidLastChar(textVisorValue)) {
                    text_visor.append(buttonValue)
                    flag = false
                }
            }
            button_equals.id -> {
                if (checkForInvalidLastChar(textVisorValue)) {
                    if (!textVisorValue.contains("/")) {
                        calculateResult(textVisorValue)
                    } else {
                        val expressionParts = textVisorValue.split("/")
                        if (expressionParts[1] != "0") {
                            calculateResult(textVisorValue)
                        } else {
                            text_visor.text = getString(R.string.error_message)
                        }
                    }
                }
            }
        }
    }

    fun onDeletingTypeButtonPressed(view: View) {
        val button = view as android.widget.Button
        val textVisorValue = text_visor.text.toString()
        Log.i(TAG, "Click on button ${button.text}")
        when(button.id) {
            button_clear.id  -> {
                text_visor.text = "0"
            }
            button_erase.id  -> {
                if (textVisorValue.length > 1) {
                    text_visor.text = textVisorValue.substring(0, textVisorValue.lastIndex)
                } else {
                    text_visor.text = "0"
                }
            }
        }
    }

    private fun checkForInvalidLastChar(s: String) : Boolean {
        return s[s.lastIndex] != '+' &&
                s[s.lastIndex] != '-' &&
                s[s.lastIndex] != '*' &&
                s[s.lastIndex] != '/' &&
                s[s.lastIndex] != '.'
    }

    private fun calculateResult(s: String) {
        val expression = ExpressionBuilder(text_visor.text.toString()).build()
        val result = expression.evaluate().toString()
        val resultParts = result.split(".")
        text_visor.text = if (resultParts[1].toInt() == 0) resultParts[0] else result
        val history = "$s=${text_visor.text}"
        hist_visor.text = history
        Log.i(TAG, "The expression result is ${text_visor.text}")
        flag = true
    }

}
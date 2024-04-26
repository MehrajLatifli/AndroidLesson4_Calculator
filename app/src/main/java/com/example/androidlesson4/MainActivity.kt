package com.example.androidlesson4

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.androidlesson4.databinding.ActivityMainBinding
import android.graphics.PorterDuff
import android.widget.Switch
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

fun BlueTheme(binding:ActivityMainBinding){

    binding.main.setBackgroundColor(Color.parseColor("#03A9F4"))

    val buttons = arrayListOf(
        binding.button1, binding.button2, binding.button3, binding.button4,
        binding.button5, binding.button6, binding.button7, binding.button8,
        binding.button9, binding.button10, binding.button11, binding.button12,
        binding.button13, binding.button14, binding.button15, binding.button16,
        binding.button17
    )

    buttons.forEach { button ->
        button.setTextColor(Color.parseColor("#FFFFFF"))
        button.setBackgroundColor(Color.parseColor("#000000"))
    }

    binding.textView.setBackgroundColor(Color.parseColor("#000000"))
    binding.textView.setTextColor(Color.parseColor("#FFFFFF"))

    binding.switch1.thumbTintList = ColorStateList.valueOf(Color.parseColor("#03A9F4"))
    binding.switch1.trackTintList = ColorStateList.valueOf(Color.parseColor("#9C27B0"))
    binding.switch1.text = "Blue"
    binding.switch1.setTextColor(ColorStateList.valueOf(Color.parseColor("#FFFFFF")))


}

fun PurpleTheme(binding:ActivityMainBinding){


    binding.main.setBackgroundColor(Color.parseColor("#000000"))

    val buttons = arrayListOf(
        binding.button1, binding.button2, binding.button3, binding.button4,
        binding.button5, binding.button6, binding.button7, binding.button8,
        binding.button9, binding.button10, binding.button11, binding.button12,
        binding.button13, binding.button14, binding.button15, binding.button16,
        binding.button17
    )

    buttons.forEach { button ->
        button.setTextColor(Color.parseColor("#FFFFFF"))
        button.setBackgroundColor(Color.parseColor("#9C27B0"))
    }

    binding.textView.setBackgroundColor(Color.parseColor("#9C27B0"))
    binding.textView.setTextColor(Color.parseColor("#FFFFFF"))

    binding.switch1.thumbTintList = ColorStateList.valueOf(Color.parseColor("#9C27B0"))
    binding.switch1.trackTintList = ColorStateList.valueOf(Color.parseColor("#03A9F4"))
    binding.switch1.text = "Purple"
    binding.switch1.setTextColor(ColorStateList.valueOf(Color.parseColor("#FFFFFF")))


}

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        lateinit var binding: ActivityMainBinding

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var currentText: String? = null
        var result: Double? = null


        BlueTheme(binding)

        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle("Information")
        alertDialogBuilder.setMessage("During the calculation, the practical sequence is observed. Mathematical sequence logic does not work.")
        alertDialogBuilder.setCancelable(false)

        alertDialogBuilder.setPositiveButton("OK") { dialog, _ ->
            dialog.dismiss()
        }

        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()





        val numberButtons = arrayListOf(
            binding.button1,
            binding.button2,
            binding.button3,
            binding.button5,
            binding.button6,
            binding.button7,
            binding.button9,
            binding.button10,
            binding.button11,
            binding.button13
        )

        val operatorButtons = arrayListOf(binding.button4, binding.button8, binding.button12, binding.button16)

        binding.textView.text = null


        binding.switch1.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {

                PurpleTheme(binding)
            } else {

                BlueTheme(binding)
            }
        }


        numberButtons.forEach { button ->
            button.setOnClickListener {

                if (binding.textView.text.toString() == "Error: Division by zero" || binding.textView.text.toString() == "Error: Invalid expression") {
                    binding.textView.text = null
                }

                currentText = binding.textView.text.toString()

                binding.textView.text = currentText + button.text

            }
        }


        binding.button17.setOnClickListener {
            currentText = binding.textView.text.toString()

            binding.textView.text = null
        }


        operatorButtons.forEach { button ->
            button.setOnClickListener {
                if (binding.textView.text.toString() == "Error: Division by zero" || binding.textView.text.toString() == "Error: Invalid expression") {
                    binding.textView.text = null
                }

                val operator = button.text.toString()
                currentText = binding.textView.text.toString()

                if (currentText?.isNotEmpty() == true || currentText?.lastOrNull()
                        ?.isDigit() == true
                ) {

                    val lastChar = currentText?.lastOrNull()
                    if (lastChar == '+' || lastChar == '-' || lastChar == '*' || lastChar == '/') {

                        binding.textView.text = currentText?.dropLast(1).toString() + operator
                    } else {

                        binding.textView.text = currentText + operator
                    }
                }
            }
        }


        binding.button15.setOnClickListener {
            val expression = binding.textView.text.toString()

            if (expression.isNotEmpty()) {
                val parts = expression.split(Regex("(?<=[+\\-*/])|(?=[+\\-*/])"))

                if (parts.size > 1) {
                    var result = 0.0
                    var currentOperator = "+"

                    for (part in parts) {
                        val number = part.toDoubleOrNull()
                        if (number != null) {
                            when (currentOperator) {
                                "+" -> result += number
                                "-" -> result -= number
                                "*" -> result *= number
                                "/" -> {
                                    if (number != 0.0) result /= number
                                    else {
                                        binding.textView.text = "Error: Division by zero"
                                        return@setOnClickListener
                                    }
                                }
                            }
                        } else {
                            if (part == "-") {
                                currentOperator = "-"
                            } else {
                                currentOperator = part
                            }
                        }
                    }

                    binding.textView.text = result.toString()
                } else {
                    binding.textView.text = "Error: Invalid expression"
                }
            }
        }


        binding.button14.setOnClickListener {

            currentText = binding.textView.text.toString()

            if (currentText!!.isNotEmpty()) {

                val lastChar = currentText!!.lastOrNull()
                if (lastChar !in arrayListOf('+', '-', '*', '/')) {


                    val parts = currentText!!.split(Regex("[+\\-*/]"))
                    val lastPart = parts.lastOrNull()

                    if (lastPart != null && !lastPart.contains('.')) {
                        binding.textView.text = "$currentText."
                    }
                }
            }
        }


    }
}
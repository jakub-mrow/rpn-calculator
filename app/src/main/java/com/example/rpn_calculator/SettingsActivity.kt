package com.example.rpn_calculator

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.widget.Button
import android.widget.EditText

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide()
        setContentView(R.layout.activity_settings)

        val applyButton: Button = findViewById(R.id.applyButton)

        val roundingNumberInput: EditText = findViewById(R.id.roundingNumberInput)
        val backgroundColorInput: EditText = findViewById(R.id.backgroundColorInput)

        val roundingNumber: Int = intent.getIntExtra("ROUNDING_NUMBER", 3)

        roundingNumberInput.setText(roundingNumber.toString())


        applyButton.setOnClickListener {
            val roundingNum: Int = roundingNumberInput.text.toString().toInt()
            val backgroundCol: String = backgroundColorInput.text.toString()

            val senderSettingsIntent = Intent()
            senderSettingsIntent.putExtra("EDITOR_COLOR", backgroundCol)
            senderSettingsIntent.putExtra("ROUNDING_NUMBER", roundingNum)
            setResult(Activity.RESULT_OK, senderSettingsIntent)
            finish()

        }

    }

}
package com.example.rpn_calculator

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.rpn_calculator.databinding.ActivityMainBinding
import com.example.rpn_calculator.handler.OperationHandler
import com.example.rpn_calculator.operation.*
import java.lang.Exception
import java.math.BigDecimal
import java.math.RoundingMode

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val stack: ArrayDeque<Double> = ArrayDeque()
    private val operationHandler = OperationHandler(this)
    private var ROUNDING_NUMBER: Int = 4

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide()
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val stackTextsList = listOf(
            binding.stackFirstIndexText,
            binding.stackSecondIndexText,
            binding.stackThirdIndexText,
            binding.stackFourthIndexText
        )

        binding.button0.setOnClickListener { typeNumber(binding.button0) }
        binding.button1.setOnClickListener { typeNumber(binding.button1) }
        binding.button2.setOnClickListener { typeNumber(binding.button2) }
        binding.button3.setOnClickListener { typeNumber(binding.button3) }
        binding.button4.setOnClickListener { typeNumber(binding.button4) }
        binding.button5.setOnClickListener { typeNumber(binding.button5) }
        binding.button6.setOnClickListener { typeNumber(binding.button6) }
        binding.button7.setOnClickListener { typeNumber(binding.button7) }
        binding.button8.setOnClickListener { typeNumber(binding.button8) }
        binding.button9.setOnClickListener { typeNumber(binding.button9) }

        binding.acButton.setOnClickListener { clearMainResult() }
        binding.dotButton.setOnClickListener { addDot() }
        binding.signButton.setOnClickListener { changeSign() }

        binding.enterButton.setOnClickListener {
            operationHandler.execute(
                AddToStack(stack, getCurrentNumber())
            )
            refreshStackTexts(stack, stackTextsList)
            clearMainResult()
        }

        binding.additionButton.setOnClickListener {
            operationHandler.execute(
                AddOperation(stack)
            )
            refreshStackTexts(stack, stackTextsList)
            clearMainResult()
        }

        binding.subtractionButton.setOnClickListener {
            operationHandler.execute(
                SubtractionOperation(stack)
            )
            refreshStackTexts(stack, stackTextsList)
            clearMainResult()
        }

        binding.multiplicationButton.setOnClickListener {
            operationHandler.execute(
                MultiplicationOperation(stack)
            )
            refreshStackTexts(stack, stackTextsList)
            clearMainResult()
        }

        binding.divisionButton.setOnClickListener {
            operationHandler.execute(
                DivisionOperation(stack)
            )
            refreshStackTexts(stack, stackTextsList)
            clearMainResult()
        }

        binding.exponentButton.setOnClickListener {
            operationHandler.execute(
                ExponentOperation(stack)
            )
            refreshStackTexts(stack, stackTextsList)
            clearMainResult()
        }

        binding.dropButton.setOnClickListener {
            operationHandler.execute(
                DropOperation(stack)
            )
            refreshStackTexts(stack, stackTextsList)
        }

        binding.swapButton.setOnClickListener {
            operationHandler.execute(
                SwapOperation(stack)
            )
            refreshStackTexts(stack, stackTextsList)
        }

        binding.rootButton.setOnClickListener {
            operationHandler.execute(
                SquareRootOperation(stack)
            )
            refreshStackTexts(stack, stackTextsList)
        }

        binding.undoButton.setOnClickListener {
            operationHandler.undoLastOperation()
            refreshStackTexts(stack, stackTextsList)
        }


        binding.settingsButton.setOnClickListener {
            val senderMainIntent = Intent(this, SettingsActivity::class.java)
            senderMainIntent.putExtra("ROUNDING_NUMBER", ROUNDING_NUMBER)
            resultLauncher.launch(senderMainIntent)
        }

    }

    private var resultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val returnData = result.data
            if (returnData != null) {
                ROUNDING_NUMBER = returnData.getIntExtra("ROUNDING_NUMBER", ROUNDING_NUMBER)
                try{
                    binding.stackCard.setCardBackgroundColor(Color.parseColor(returnData.getStringExtra("EDITOR_COLOR")))
                } catch (e: Exception){
                    binding.stackCard.setCardBackgroundColor(Color.parseColor("#343434"))
                    Toast.makeText(this, "Invalid color!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun typeNumber(button: Button){
        val nextNumber: String = button.text.toString()
        val currentNumber: String = binding.mainResultText.text.toString()

        if (currentNumber == "0"){
            binding.mainResultText.text = nextNumber
        } else{
            val result: String = currentNumber+nextNumber
            binding.mainResultText.text = result
        }
    }

    private fun changeSign(){
        val currentNumber: String = binding.mainResultText.text.toString()
        if (currentNumber.contains("-")){
            binding.mainResultText.text = currentNumber.drop(1)
        } else{
            binding.mainResultText.text = "-" + currentNumber
        }
    }

    private fun addDot(){
        val currentNumber: String = binding.mainResultText.text.toString()
        if (currentNumber.contains(".")) return
        binding.mainResultText.text = currentNumber + "."
    }

    private fun clearMainResult(){
        binding.mainResultText.text = "0"
    }

    private fun getCurrentNumber(): Double {
        return binding.mainResultText.text.toString().toDouble()
    }

    private fun setCurrentNumber(number: String) {
        binding.mainResultText.text = number
    }

    private fun getLastStackElement(stack: ArrayDeque<Double>): Double{
        if (stack.count() == 0){
            return 0.0
        }
        return stack.last()
    }

    private fun refreshStackTexts(stack: ArrayDeque<Double>, stackTextsList: List<TextView> ){
        val reversedStack = stack.reversed()
        val reversedStackLength: Int = reversedStack.count()

        if (reversedStackLength < 4){
            for(j in reversedStackLength until 4){
                stackTextsList[j].text = ""
            }
            for (i in 0 until reversedStackLength){
                if (reversedStack[i] % 1.0f == 0.0){
                    stackTextsList[i].text = reversedStack[i].toInt().toString()
                    stackTextsList[i].textSize = 30.0f
                } else {
                    stackTextsList[i].text = BigDecimal(reversedStack[i]).setScale(ROUNDING_NUMBER, RoundingMode.HALF_UP).toString()
                    stackTextsList[i].textSize = 30.0f
                }
            }
        } else{
            for (i in 0 until 4){
                if (reversedStack[i] % 1.0f == 0.0){
                    stackTextsList[i].text = reversedStack[i].toInt().toString()
                    stackTextsList[i].textSize = 30.0f
                } else {
                    stackTextsList[i].text = BigDecimal(reversedStack[i]).setScale(ROUNDING_NUMBER, RoundingMode.HALF_UP).toString()
                    stackTextsList[i].textSize = 30.0f
                }
            }
        }
    }

}
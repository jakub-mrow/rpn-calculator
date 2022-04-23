package com.example.rpn_calculator.handler

import android.content.Context
import android.widget.Toast
import com.example.rpn_calculator.operation.Operation

class OperationHandler(private val context: Context) {
    private val operationStack: ArrayDeque<Operation> = ArrayDeque()

    fun execute(operation: Operation){
        val response: OperationResponse = operation.executeOperation()
        val responseMessage: String = response.getMessage()
        if (response.isResultFalse()){
            val toast: Toast = Toast.makeText(context, responseMessage, Toast.LENGTH_SHORT)
            toast.show()
        }
        if (response.isResultTrue()){
            operationStack.addLast(operation)
        }
    }

    fun undoLastOperation(){
        if (operationStack.count() < 1) return
        val lastOperation: Operation = operationStack.removeLast()
        lastOperation.undoOperation()
    }
}
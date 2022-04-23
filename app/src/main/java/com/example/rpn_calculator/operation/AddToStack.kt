package com.example.rpn_calculator.operation

import com.example.rpn_calculator.handler.OperationResponse

class AddToStack(private val stack: ArrayDeque<Double>, private val number: Double) : Operation {
    private var first: Double = 0.0

    override fun executeOperation(): OperationResponse {
        first = number
        stack.addLast(number)
        val status = "Succeeded!"
        return OperationResponse(status, true)
    }
    override fun undoOperation(): OperationResponse {
        stack.removeLast()
        val status = "Succeeded!"
        return OperationResponse(status, true)
    }
}
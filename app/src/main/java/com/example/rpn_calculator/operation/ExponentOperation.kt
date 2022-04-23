package com.example.rpn_calculator.operation

import com.example.rpn_calculator.handler.OperationResponse
import kotlin.math.pow

class ExponentOperation(private val stack: ArrayDeque<Double>): Operation {
    private var first: Double = 0.0
    private var second: Double = 0.0

    override fun executeOperation(): OperationResponse {
        if (stack.count() < 2){
            val status = "Not enough numbers on stack to do this operation!"
            return OperationResponse(status, false)
        }
        first = stack.removeLast()
        second = stack.removeLast()
        stack.addLast(second.pow(first))
        val status = "Succeeded!"
        return OperationResponse(status, true)
    }
    override fun undoOperation(): OperationResponse {
        stack.removeLast()
        stack.addLast(second)
        stack.addLast(first)
        val status = "Succeeded!"
        return OperationResponse(status, true)
    }
}
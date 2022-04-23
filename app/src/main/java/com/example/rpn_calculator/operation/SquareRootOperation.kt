package com.example.rpn_calculator.operation

import com.example.rpn_calculator.handler.OperationResponse
import kotlin.math.sqrt

class SquareRootOperation(private val stack: ArrayDeque<Double>): Operation {
    private var first: Double = 0.0

    override fun executeOperation(): OperationResponse {
        if (stack.count() < 1){
            val status = "Not enough numbers on stack to do this operation!"
            return OperationResponse(status, false)
        }

        first = stack.removeLast()

        if (first < 0){
            val status = "Cannot do square root operation on negative numbers!"
            return OperationResponse(status, false)
        }
        stack.addLast(sqrt(first))

        val status = "Succeeded!"
        return OperationResponse(status, true)
    }
    override fun undoOperation(): OperationResponse {
        stack.removeLast()
        stack.addLast(first)
        val status = "Succeeded!"
        return OperationResponse(status, true)
    }
}
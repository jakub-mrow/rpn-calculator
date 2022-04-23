package com.example.rpn_calculator.operation

import com.example.rpn_calculator.handler.OperationResponse

class DivisionOperation(private val stack: ArrayDeque<Double>): Operation {
    private var first: Double = 0.0
    private var second: Double = 0.0

    override fun executeOperation(): OperationResponse {
        if (stack.count() < 2){
            val status = "Not enough numbers on stack to do this operation!"
            return OperationResponse(status, false)
        }
        first = stack.removeLast()
        second = stack.removeLast()
        if (first == 0.0){
            val status = "You cannot divide by 0!"
            return OperationResponse(status, false)
        }
        stack.addLast(second / first)
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
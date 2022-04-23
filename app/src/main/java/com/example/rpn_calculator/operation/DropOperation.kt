package com.example.rpn_calculator.operation

import com.example.rpn_calculator.handler.OperationResponse

class DropOperation(private val stack: ArrayDeque<Double>): Operation {
    private val stackCopy: ArrayDeque<Double> = ArrayDeque()
    private var first = 0.0

    override fun executeOperation(): OperationResponse {
        if (stack.count() < 1){
            val status = "Not enough numbers on stack to do this operation!"
            return OperationResponse(status, false)
        }

        first = stack.removeLast()
        val status = "Succeeded!"
        return OperationResponse(status, true)
    }
    override fun undoOperation(): OperationResponse {
        stack.addLast(first)
        val status = "Succeeded!"
        return OperationResponse(status, true)
    }
}
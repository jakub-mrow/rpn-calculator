package com.example.rpn_calculator.operation

import com.example.rpn_calculator.handler.OperationResponse

interface Operation {
    fun executeOperation(): OperationResponse
    fun undoOperation(): OperationResponse
}
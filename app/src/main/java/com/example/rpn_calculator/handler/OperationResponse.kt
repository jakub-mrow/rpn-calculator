package com.example.rpn_calculator.handler

class OperationResponse(private val message: String, private val response: Boolean) {
    fun isResultTrue(): Boolean {
        if (response) {
            return true
        }
        return false
    }
    fun isResultFalse(): Boolean {
        if(!response){
            return true
        }
        return false
    }
    fun getMessage(): String { return this.message }
}
package com.example.messengerapi.api.components

import com.example.messengerapi.api.constants.ErrorResponse
import com.example.messengerapi.api.exceptions.ConversationIdInvalidException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ConversationControllerAdvice {
    @ExceptionHandler(ConversationIdInvalidException::class)
    fun conversationIdInvalidException(conversationIdInvalidException: ConversationIdInvalidException): ResponseEntity<ErrorResponse> {
        val res = ErrorResponse("", conversationIdInvalidException.message)
        return ResponseEntity.unprocessableEntity().body(res)
    }
}
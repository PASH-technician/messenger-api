package com.example.messengerapi.api.components

import com.example.messengerapi.api.constants.ErrorResponse
import com.example.messengerapi.api.constants.ResponseConstants
import com.example.messengerapi.api.exceptions.MessageEmptyException
import com.example.messengerapi.api.exceptions.MessageRecipientInvalidException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class MessageControllerAdvice {
    @ExceptionHandler(MessageEmptyException::class)
    fun messageEmpty(messageEmptyException: MessageEmptyException): ResponseEntity<ErrorResponse> {
        val res = ErrorResponse(ResponseConstants.MESSAGE_EMPTY.value, messageEmptyException.message)
        return ResponseEntity.unprocessableEntity().body(res)
    }

    @ExceptionHandler(MessageRecipientInvalidException::class)
    fun messageRecipientInvalid(messageRecipientInvalidException: MessageRecipientInvalidException): ResponseEntity<ErrorResponse> {
        val res = ErrorResponse(ResponseConstants.MESSAGE_RECIPIENT_INVALID.value, messageRecipientInvalidException.message)
        return ResponseEntity.unprocessableEntity().body(res)
    }
}
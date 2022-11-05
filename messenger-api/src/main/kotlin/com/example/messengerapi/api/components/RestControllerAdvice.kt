package com.example.messengerapi.api.components

import com.example.messengerapi.api.constants.ErrorResponse
import com.example.messengerapi.api.constants.ResponseConstants
import com.example.messengerapi.api.exceptions.UserDeactivatedException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class RestControllerAdvice {
    @ExceptionHandler(UserDeactivatedException::class)
    fun userDeactivated(userDeactivatedException: UserDeactivatedException): ResponseEntity<ErrorResponse> {
        val res = ErrorResponse(ResponseConstants.ACCOUNT_DEACTIVATED.value, userDeactivatedException.message)
        return ResponseEntity(res, HttpStatus.UNAUTHORIZED)
    }
}
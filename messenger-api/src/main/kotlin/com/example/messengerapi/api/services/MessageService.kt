package com.example.messengerapi.api.services

import com.example.messengerapi.api.models.Message
import com.example.messengerapi.api.models.User

interface MessageService {
    fun sendMessage(sender: User, recipientId: Long, messageText: String): Message
}
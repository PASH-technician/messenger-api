package com.example.messengerapi.api.repositories

import com.example.messengerapi.api.models.Message
import org.springframework.data.repository.CrudRepository

interface MessageRepository: CrudRepository<Message, Long> {
    fun findByConversationId(conversationId: Long): List<Message>
}
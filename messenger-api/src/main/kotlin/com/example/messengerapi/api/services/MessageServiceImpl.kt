package com.example.messengerapi.api.services

import com.example.messengerapi.api.exceptions.MessageEmptyException
import com.example.messengerapi.api.exceptions.MessageRecipientInvalidException
import com.example.messengerapi.api.models.Conversation
import com.example.messengerapi.api.models.Message
import com.example.messengerapi.api.models.User
import com.example.messengerapi.api.repositories.ConversationRepository
import com.example.messengerapi.api.repositories.MessageRepository
import com.example.messengerapi.api.repositories.UserRepository
import org.springframework.stereotype.Service
import kotlin.jvm.Throws

@Service
class MessageServiceImpl(
    private val repository: MessageRepository,
    private val conversationRepository: ConversationRepository,
    private val conversationService: ConversationService,
    private val userRepository: UserRepository
) : MessageService {

    @Throws(MessageRecipientInvalidException::class, MessageEmptyException::class)
    override fun sendMessage(sender: User, recipientId: Long, messageText: String): Message {
        val optional = userRepository.findById(recipientId)
        if(optional.isPresent) {
            val recipient = optional.get()
            if (!messageText.isEmpty()) {
                val conversation: Conversation = getOrCreateConversation(sender, recipient)

                conversationRepository.save(conversation)

                val message = Message(sender = sender, recipient = recipient, body = messageText, conversation = conversation)
                repository.save(message)
                return message
            }
        } else {
            throw MessageRecipientInvalidException("The recipient id '$recipientId' is invalid.")
        }

        throw MessageEmptyException()
    }

    private fun getOrCreateConversation(sender: User, recipient: User): Conversation {
        return if (conversationService.conversationExists(sender, recipient)) {
            conversationService.getConversation(sender, recipient) as Conversation
        } else {
            conversationService.createConversation(sender, recipient)
        }
    }
}
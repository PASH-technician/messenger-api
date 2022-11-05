package com.example.messengerapi.api.services

import com.example.messengerapi.api.exceptions.ConversationIdInvalidException
import com.example.messengerapi.api.models.Conversation
import com.example.messengerapi.api.models.User
import com.example.messengerapi.api.repositories.ConversationRepository
import kotlin.jvm.Throws

class ConversationServiceImpl(
    private val repository: ConversationRepository
): ConversationService {
    override fun createConversation(userA: User, userB: User): Conversation {
        val conversation = Conversation(sender = userA, recipient = userB)
        repository.save(conversation)
        return conversation
    }

    override fun conversationExists(userA: User, userB: User): Boolean {
        return getConversation(userA, userB) != null
    }

    override fun getConversation(userA: User, userB: User): Conversation? {
        val conversationAWithB = repository.findBySenderIdAndRecipientId(userA.id, userB.id)
        val conversationBWithA = repository.findBySenderIdAndRecipientId(userB.id, userA.id)
        return conversationAWithB ?: conversationBWithA
    }

    @Throws(ConversationIdInvalidException::class)
    override fun retrieveThread(conversationId: Long): Conversation {
        val conversation = repository.findById(conversationId)
        if(conversation.isPresent) {
            return conversation.get()
        }
        throw ConversationIdInvalidException("Invalid conversation id '$conversationId'")
    }

    override fun listUserConversations(userId: Long): List<Conversation> {
        val conversationList: ArrayList<Conversation> = ArrayList()
        conversationList.addAll(repository.findBySenderId(userId))
        conversationList.addAll(repository.findByRecipientId(userId))
        return conversationList
    }

    override fun nameSecondParty(conversation: Conversation, userId: Long): String {
        return if (conversation.sender?.id == userId) {
            conversation.recipient?.username as String
        } else {
            conversation.sender?.username as String
        }
    }
}
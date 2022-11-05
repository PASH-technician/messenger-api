package com.example.messengerapi.api.components

import com.example.messengerapi.api.helpers.objects.ConversationListVO
import com.example.messengerapi.api.helpers.objects.ConversationVO
import com.example.messengerapi.api.helpers.objects.MessageVO
import com.example.messengerapi.api.models.Conversation
import com.example.messengerapi.api.services.ConversationService
import org.springframework.stereotype.Component

@Component
class ConversationAssembler(
    val conversationService: ConversationService,
    val messageAssembler: MessageAssembler
) {
    fun toConversationVO(conversation: Conversation, userId: Long): ConversationVO {
        val conversationMessage: ArrayList<MessageVO> = ArrayList()
        conversation.messages?.mapTo(conversationMessage) {
            messageAssembler.toMessageVO(it)
        }
        return ConversationVO(
            conversation.id,
            conversationService.nameSecondParty(conversation, userId),
            conversationMessage
        )
    }

    fun toConversationListVO(conversations: ArrayList<Conversation>, userId: Long): ConversationListVO {
        val conversationVOList = conversations.map { toConversationVO(it, userId) }
        return ConversationListVO(conversationVOList)
    }
}
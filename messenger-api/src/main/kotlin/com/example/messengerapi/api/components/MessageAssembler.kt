package com.example.messengerapi.api.components

import com.example.messengerapi.api.helpers.objects.MessageVO
import com.example.messengerapi.api.models.Message
import org.springframework.stereotype.Component

@Component
class MessageAssembler {
    fun toMessageVO(message: Message): MessageVO {
        return MessageVO(
            message.id,
            message.sender?.id,
            message.recipient?.id,
            message.conversation?.id,
            message.body,
            message.createdAt.toString()
        )
    }
}
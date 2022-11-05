package com.example.messengerapi.api.controllers

import com.example.messengerapi.api.components.ConversationAssembler
import com.example.messengerapi.api.helpers.objects.ConversationListVO
import com.example.messengerapi.api.helpers.objects.ConversationVO
import com.example.messengerapi.api.models.Conversation
import com.example.messengerapi.api.models.User
import com.example.messengerapi.api.repositories.UserRepository
import com.example.messengerapi.api.services.ConversationService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("/conversations")
class ConversationController(
    val conversationService: ConversationService,
    val conversationAssembler: ConversationAssembler,
    val userRepository: UserRepository
) {
    @GetMapping
    fun list(request: HttpServletRequest): ResponseEntity<ConversationListVO> {
        val user = userRepository.findByUsername(request.userPrincipal.name) as User
        val conversations = conversationService.listUserConversations(user.id) as ArrayList<Conversation>
        return ResponseEntity.ok(conversationAssembler.toConversationListVO(conversations, user.id))
    }

    @GetMapping
    @RequestMapping("/{conversation_id")
    fun show(@PathVariable(name = "conversation_id") conversationId: Long, request: HttpServletRequest): ResponseEntity<ConversationVO> {
        val user = userRepository.findByUsername(request.userPrincipal.name) as User
        val conversationThread = conversationService.retrieveThread(conversationId)
        return ResponseEntity.ok(conversationAssembler.toConversationVO(conversationThread,user.id))
    }
}
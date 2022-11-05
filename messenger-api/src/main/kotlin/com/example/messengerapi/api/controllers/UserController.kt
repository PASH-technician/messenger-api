package com.example.messengerapi.api.controllers

import com.example.messengerapi.api.components.UserAssembler
import com.example.messengerapi.api.helpers.objects.UserListVO
import com.example.messengerapi.api.helpers.objects.UserVO
import com.example.messengerapi.api.models.User
import com.example.messengerapi.api.repositories.UserRepository
import com.example.messengerapi.api.services.UserService
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("/users")
class UserController(val userService: UserService, val userRepository: UserRepository, val userAssembler: UserAssembler) {
    @PostMapping
    @RequestMapping("/users")
    fun create(@Validated @RequestBody userDetails: User): ResponseEntity<User> {
        val user = userService.attemptRegistration(userDetails)
        return ResponseEntity.ok(user)
    }

    @GetMapping
    @RequestMapping("/{user_id}")
    fun show(@PathVariable("user_id") userId: Long): ResponseEntity<UserVO> {
        val user = userService.retrieveUserData(userId)
        return ResponseEntity.ok(userAssembler.toUserVO(user))
    }

    @GetMapping
    @RequestMapping("/details")
    fun echoDetails(request: HttpServletRequest): ResponseEntity<UserVO> {
        val user = userRepository.findByUsername(request.userPrincipal.name) as User
        return ResponseEntity.ok(userAssembler.toUserVO(user))
    }

    @GetMapping
    fun index(request: HttpServletRequest): ResponseEntity<UserListVO> {
        val user = userRepository.findByUsername(request.userPrincipal.name) as User
        val users = userService.listUsers(user)
        return ResponseEntity.ok(userAssembler.toUserListVO(users))
    }

    @PutMapping
    fun update(@RequestBody updateDetails: User, request: HttpServletRequest): ResponseEntity<UserVO> {
        val currentUser = userRepository.findByUsername(request.userPrincipal.name)
        userService.updateUserStatus(currentUser as User, updateDetails)
        return ResponseEntity.ok(userAssembler.toUserVO(currentUser))
    }
}
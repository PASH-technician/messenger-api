package com.example.messengerapi.api.services

import com.example.messengerapi.api.models.User

interface UserService {
    fun attemptRegistration(userDetails: User): User
    fun listUsers(currentUser: User): List<User>
    fun retrieveUserData(username: String): User?
    fun retrieveUserData(id: Long): User
    fun usernameExists(username: String): Boolean
    fun updateUserStatus(currentUser: User, updateDetails: User): User
}
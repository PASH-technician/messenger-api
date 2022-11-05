package com.example.messengerapi.api.models

import com.example.messengerapi.api.listeners.UserListener
import org.springframework.format.annotation.DateTimeFormat
import java.time.Instant
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EntityListeners
import javax.persistence.Id
import javax.persistence.Table
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size

@Entity
@Table(name = "user")
@EntityListeners(UserListener::class)
class User(
    @Id
    @Size(min = 2)
    var id: Long = 0,
    @Column(unique = true)
    @Size(min = 2)
    var username: String,
    @Size(min = 8, max = 15)
    @Column(unique = true)
    var phoneNumber: String  = "",
    @Size(min = 60, max = 60)
    var password: String = "",
    var status: String = "",
    @Pattern(regexp = "\\A(activated|deactivated)\\z")
    var accountStatus: String = "activated",
    @DateTimeFormat
    var createAt: Date = Date.from(Instant.now())
)
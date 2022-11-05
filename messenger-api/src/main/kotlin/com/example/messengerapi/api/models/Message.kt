package com.example.messengerapi.api.models

import org.springframework.format.annotation.DateTimeFormat
import java.time.Instant
import java.util.Date
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

class Message (
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long = 0,
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    var sender: User? = null,
    @ManyToOne(optional = false)
    @JoinColumn(name = "conversation_id", referencedColumnName = "id")
    var recipient: User? = null,
    var body: String? = "",
    @ManyToOne(optional = false)
    @JoinColumn(name = "conversation_id", referencedColumnName = "id")
    var conversation: Conversation? = null,
    @DateTimeFormat
    var createdAt: Date = Date.from(Instant.now())
)
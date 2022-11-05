package com.example.messengerapi.api.services

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import java.util.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

internal object TokenAuthenticationService {
    private const val TOKEN_EXPIRY: Long = 865_000_000
    private const val SECRET = "c9m5u~%%B49L0OIaowvs"
    private const val TOKEN_PREFIX = "Bearer"
    private const val AUTHORIZATION_HEADER_KEY = "Authorization"

    fun addAuthentication(res: HttpServletResponse, username: String) {
        val JWT = Jwts.builder()
            .setSubject(username)
            .setExpiration(Date(System.currentTimeMillis()))
            .signWith(SignatureAlgorithm.HS512, SECRET)
            .compact()

        res.addHeader(AUTHORIZATION_HEADER_KEY, "$TOKEN_EXPIRY $JWT")
    }

    fun getAuthentication(request: HttpServletRequest): Authentication? {
        val token = request.getHeader(AUTHORIZATION_HEADER_KEY)
        if(token != null) {
            val user = Jwts.parser().setSigningKey(SECRET)
                .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                .body.subject
            if(user != null) return UsernamePasswordAuthenticationToken(user, null, emptyList<GrantedAuthority>())
        }

        return null
    }
}
package by.itsupportme.socialnetwork.controllers

import by.itsupportme.socialnetwork.beans.User
import by.itsupportme.socialnetwork.beans.jwt.JwtRequest
import by.itsupportme.socialnetwork.beans.jwt.JwtResponse
import by.itsupportme.socialnetwork.services.JwtUserDetailsService
import by.itsupportme.socialnetwork.utils.JwtTokenUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.DisabledException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.password.AbstractPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@Controller
@CrossOrigin
class LoginController(
        @Autowired
        var authenticationManager: AuthenticationManager,
        @Autowired
        var jwtTokenUtil: JwtTokenUtil,
        @Autowired
        var userDetailService: JwtUserDetailsService
) {

    @Value("\${message.wrongCredentials}")
    lateinit var errorMessage: String

    @PostMapping( "\${mapping.login}")
    fun createAuthenticationToken(@RequestBody authenticationRequest: JwtRequest): ResponseEntity<*> {
        try {
            authenticate(authenticationRequest.name, authenticationRequest.password)
        } catch (e : Exception){
            return ResponseEntity(errorMessage,HttpStatus.BAD_REQUEST)
        }
        val token: String = jwtTokenUtil.generateToken(userDetailService.loadUserByUsername(authenticationRequest.name)!!)!!
        return ResponseEntity.ok<Any>(JwtResponse(token))
    }

    @Throws(Exception::class)
    private fun authenticate(username: String, password: String) {
        try {
            authenticationManager.authenticate(UsernamePasswordAuthenticationToken(username, password))
        } catch (e: DisabledException) {
//            throw Exception("USER_DISABLED", e)
        } catch (e: BadCredentialsException) {
            throw Exception("INVALID_CREDENTIALS", e)
        }
    }

}
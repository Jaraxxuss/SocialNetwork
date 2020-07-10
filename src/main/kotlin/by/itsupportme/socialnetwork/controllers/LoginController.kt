package by.itsupportme.socialnetwork.controllers

import by.itsupportme.socialnetwork.beans.jwt.JwtRequest
import by.itsupportme.socialnetwork.beans.jwt.JwtResponse
import by.itsupportme.socialnetwork.services.JwtUserDetailsService
import by.itsupportme.socialnetwork.utils.JwtTokenUtil
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.DisabledException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
@CrossOrigin
class LoginController(
        @Autowired
        var authenticationManager: AuthenticationManager,
        @Autowired
        var jwtTokenUtil: JwtTokenUtil,
        @Autowired
        var userDetailService: JwtUserDetailsService
) {

    val logger = LoggerFactory.getLogger(LoginController::class.java)!!

    @Value("\${message.wrongCredentials}")
    lateinit var errorMessage: String

    @PostMapping( "\${mapping.login}")
    fun createAuthenticationToken(@RequestBody authenticationRequest: JwtRequest): ResponseEntity<*> {
        try {
            authenticate(authenticationRequest.user.name!!, authenticationRequest.password)
        } catch (e : Exception){
            return ResponseEntity(errorMessage, HttpStatus.BAD_REQUEST)
        }

        val token: String = jwtTokenUtil.generateToken(userDetailService.loadUserByUsername(authenticationRequest.user.name!!)!!)!!

        return ResponseEntity.ok<Any>(JwtResponse(token))
    }

    @Throws(Exception::class)
    private fun authenticate(username: String, password: String) {
        logger.info("start of authentication of $username")
        try {
            authenticationManager.authenticate(UsernamePasswordAuthenticationToken(username, password))
        } catch (e: DisabledException) {
//            throw Exception("USER_DISABLED", e)
        } catch (e: BadCredentialsException) {
            logger.info("bad credentials from  $username")
            throw Exception("INVALID_CREDENTIALS", e)
        }
        logger.info("successful authentication of $username")
    }
}
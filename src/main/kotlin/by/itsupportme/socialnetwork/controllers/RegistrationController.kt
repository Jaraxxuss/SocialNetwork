package by.itsupportme.socialnetwork.controllers

import by.itsupportme.socialnetwork.beans.User
import by.itsupportme.socialnetwork.beans.jwt.JwtRequest
import by.itsupportme.socialnetwork.services.JwtUserDetailsService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.support.RedirectAttributes

@RestController
@RequestMapping
class RegistrationController(
        @Autowired
        var userDetailService: JwtUserDetailsService,
        @Autowired
        var passwordEncoder: PasswordEncoder
){

    val logger = LoggerFactory.getLogger(RegistrationController::class.java)!!

    @Value("\${message.registered}")
    lateinit var successToken : String
    @Value("\${message.user_exists}")
    lateinit var errorMessage : String

    @PostMapping("\${mapping.registration}" )
    fun registerUser(@RequestBody authenticationRequest: JwtRequest): ResponseEntity<String> {
        logger.info("start of ${authenticationRequest.username} registration")
        return if (userDetailService.loadUserByUsername(authenticationRequest.user.name!!) == null) {
            val user = User()
            user.name = authenticationRequest.user.name
            user.password = passwordEncoder.encode(authenticationRequest.password)
            userDetailService.save(JwtRequest(user))
            logger.info("${authenticationRequest.username} was successfully registered")
            ResponseEntity(successToken, HttpStatus.OK)
        } else {
            logger.warn("${authenticationRequest.username} has already been registered")
            ResponseEntity(errorMessage, HttpStatus.BAD_REQUEST)
        }
    }
}
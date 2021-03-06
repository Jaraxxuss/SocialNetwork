package by.itsupportme.socialnetwork.controllers

import by.itsupportme.socialnetwork.beans.User
import by.itsupportme.socialnetwork.beans.jwt.JwtRequest
import by.itsupportme.socialnetwork.services.JwtUserDetailsService
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
    @Value("\${message.registered}")
    lateinit var successToken : String
    @Value("\${message.user_exists}")
    lateinit var errorMessage : String

    @PostMapping("\${mapping.registration}" )
    fun registerUser(@RequestBody authenticationRequest: JwtRequest): ResponseEntity<String> {
        return if (userDetailService.loadUserByUsername(authenticationRequest.user.name!!) == null) {
            val user = User()
            user.name = authenticationRequest.user.name
            user.password = passwordEncoder.encode(authenticationRequest.password)
            userDetailService.save(JwtRequest(user))
            ResponseEntity(successToken, HttpStatus.OK)
        } else {
            ResponseEntity(errorMessage, HttpStatus.BAD_REQUEST)
        }
    }
}
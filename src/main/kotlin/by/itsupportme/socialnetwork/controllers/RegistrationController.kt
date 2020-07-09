package by.itsupportme.socialnetwork.controllers

import by.itsupportme.socialnetwork.beans.jwt.JwtRequest
import by.itsupportme.socialnetwork.services.JwtUserDetailsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.support.RedirectAttributes

@RestController
@RequestMapping
class RegistrationController(
        @Autowired
        var userDetailService: JwtUserDetailsService
){
    @Value("\${message.registered}")
    lateinit var successToken : String
    @Value("\${message.user_exists}")
    lateinit var errorMessage : String

    @PostMapping("/registration")
    fun registerUser(@RequestBody authenticationRequest: JwtRequest) : ResponseEntity<String> {
        return if(userDetailService.loadUserByUsername(authenticationRequest.username) == null){
            userDetailService.save(authenticationRequest)

            ResponseEntity(successToken,HttpStatus.OK)
        } else {
            ResponseEntity(errorMessage,HttpStatus.OK)
        }
    }
}
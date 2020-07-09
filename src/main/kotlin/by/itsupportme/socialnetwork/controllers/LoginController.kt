package by.itsupportme.socialnetwork.controllers

import by.itsupportme.socialnetwork.beans.jwt.JwtRequest
import by.itsupportme.socialnetwork.beans.jwt.JwtResponse
import by.itsupportme.socialnetwork.services.JwtUserDetailsService
import by.itsupportme.socialnetwork.utils.JwtTokenUtil
import org.springframework.beans.factory.annotation.Autowired
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
    @PostMapping(value = ["/login"])
    fun createAuthenticationToken(@RequestBody authenticationRequest: JwtRequest): ResponseEntity<*> {
        authenticate(authenticationRequest.name, authenticationRequest.password)
        val userDetails: UserDetails = userDetailService.loadUserByUsername(authenticationRequest.name)
        val token: String = jwtTokenUtil.generateToken(userDetails)!!
        return ResponseEntity.ok<Any>(JwtResponse(token))
    }

    @Throws(Exception::class)
    private fun authenticate(username: String, password: String) {
        try {
            authenticationManager.authenticate(UsernamePasswordAuthenticationToken(username, password))
        } catch (e: DisabledException) {
//            throw Exception("USER_DISABLED", e)
        } catch (e: BadCredentialsException) {
//            throw Exception("INVALID_CREDENTIALS", e)
        }
    }

}
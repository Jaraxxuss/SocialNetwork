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
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping
class LogoutController (
        @Autowired
        var jwtTokenUtil: JwtTokenUtil,
        @Autowired
        var userDetailService: JwtUserDetailsService
) {

    val logger = LoggerFactory.getLogger(LoginController::class.java)!!

    @PostMapping( "\${mapping.logout}")
    fun logoutJwtUser(@RequestHeader("token") token: String) : ResponseEntity<*>{

        val username = jwtTokenUtil.getUsernameFromToken(token)

        logger.info("trying to logout $username")
        try {
            jwtTokenUtil.invalidateToken(userDetailService.loadUserByUsername(username)!!)
        } catch (e: NullPointerException){
            logger.info("no user with such name : $username")
            return ResponseEntity("could not found user with name $username", HttpStatus.BAD_REQUEST)
        }
        logger.info("successfully logout of $username")
        return ResponseEntity("successful token invalidation of user with name $username", HttpStatus.OK)
    }
}
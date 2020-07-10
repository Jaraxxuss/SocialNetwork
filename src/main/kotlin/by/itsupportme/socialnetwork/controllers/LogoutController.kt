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
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


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
    fun logoutJwtUser(@RequestBody authenticationRequest: JwtRequest) : ResponseEntity<*>{

        logger.info("trying to logout ${authenticationRequest.username}")
        try {
            jwtTokenUtil.invalidateToken(userDetailService.loadUserByUsername(authenticationRequest.username)!!)
        } catch (e: NullPointerException){
            logger.info("no user with such name : ${authenticationRequest.username}")
            return ResponseEntity("could not found user with name ${authenticationRequest.username}", HttpStatus.BAD_REQUEST)
        }
        logger.info("successfully logout of ${authenticationRequest.username}")
        return ResponseEntity("successful token invalidation of user with name ${authenticationRequest.username}", HttpStatus.OK)
    }
}
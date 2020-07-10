package by.itsupportme.socialnetwork.controllers

import by.itsupportme.socialnetwork.utils.JwtTokenUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.oauth2.core.OAuth2AccessToken
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest


@Controller
@RequestMapping
class LogoutController(
        @Autowired
        val jwtTokenUtil: JwtTokenUtil
) {


}
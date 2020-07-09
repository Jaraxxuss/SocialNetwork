package by.itsupportme.socialnetwork.controllers

import by.itsupportme.socialnetwork.services.JwtUserDetailsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/db")
class DBController(
        @Autowired
        private val service: JwtUserDetailsService
) {
    @GetMapping("/get")
    fun getUsers(): ResponseEntity<Any> {
        return ResponseEntity(service.allUsers(), HttpStatus.OK)
    }
}

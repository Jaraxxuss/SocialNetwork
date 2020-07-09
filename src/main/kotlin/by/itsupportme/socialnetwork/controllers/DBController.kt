package by.itsupportme.socialnetwork.controllers

import by.itsupportme.socialnetwork.beans.jwt.JwtRequest
import by.itsupportme.socialnetwork.services.JwtUserDetailsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/db")
class DBController(
        @Autowired
        private val service: JwtUserDetailsService
) {

    @PostMapping("/add")
    fun addUser(@RequestBody req: JwtRequest): ResponseEntity<String> {
        service.save(req)
        return ResponseEntity(req.toString() + " was added.", HttpStatus.OK)
    }

    @GetMapping("/get")
    fun getUsers() : ResponseEntity<MutableIterable<JwtRequest?>>{
        return ResponseEntity(service.allUsers, HttpStatus.OK)
    }
}

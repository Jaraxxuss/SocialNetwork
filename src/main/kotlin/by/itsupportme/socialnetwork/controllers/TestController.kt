package by.itsupportme.socialnetwork.controllers

import by.itsupportme.socialnetwork.beans.User
import by.itsupportme.socialnetwork.repositories.UserRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TestController(
        @Autowired
        var ur : UserRepo
){


    @GetMapping
    fun test() : MutableIterable<User> {
        ur.save(User(email = "qwe"))
        ur.save(User(email = "asd"))
        ur.save(User(email = "zxc"))
        ur.save(User(email = "rty"))
        return ur.findAll()
    }
}
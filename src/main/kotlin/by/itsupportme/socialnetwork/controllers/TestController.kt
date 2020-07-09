//package by.itsupportme.socialnetwork.controllers
//
//import by.itsupportme.socialnetwork.beans.User
//import by.itsupportme.socialnetwork.repositories.UserRepo
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.web.bind.annotation.GetMapping
//import org.springframework.web.bind.annotation.RestController
//
//@RestController
//class TestController(
//        @Autowired
//        var userRepo: UserRepo
//) {
//    @GetMapping
//    fun test(): MutableIterable<User?> {
//        userRepo.save(User(email = "qwe"))
//        userRepo.save(User(email = "asd"))
//        userRepo.save(User(email = "zxc"))
//        userRepo.save(User(email = "dfg"))
//        return userRepo.findAll()
//    }
//}
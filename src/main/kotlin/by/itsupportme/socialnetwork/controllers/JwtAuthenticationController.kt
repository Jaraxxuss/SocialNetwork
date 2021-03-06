package by.itsupportme.socialnetwork.controllers
//
//import by.itsupportme.socialnetwork.beans.jwt.JwtRequest
//import by.itsupportme.socialnetwork.beans.jwt.JwtResponse
//import by.itsupportme.socialnetwork.services.JwtUserDetailsService
//import by.itsupportme.socialnetwork.utils.JwtTokenUtil
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.http.ResponseEntity
//import org.springframework.security.authentication.AuthenticationManager
//import org.springframework.security.authentication.BadCredentialsException
//import org.springframework.security.authentication.DisabledException
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
//import org.springframework.security.core.userdetails.UserDetails
//import org.springframework.web.bind.annotation.CrossOrigin
//import org.springframework.web.bind.annotation.PostMapping
//import org.springframework.web.bind.annotation.RequestBody
//import org.springframework.web.bind.annotation.RestController
//
//
//@RestController
//@CrossOrigin
//class JwtAuthenticationController(
//        @Autowired
//        var authenticationManager: AuthenticationManager,
//        @Autowired
//        var jwtTokenUtil: JwtTokenUtil,
//        @Autowired
//        var userDetailService: JwtUserDetailsService
//) {
//
//    @PostMapping(value = ["/authenticate"])
//    fun createAuthenticationToken(@RequestBody authenticationRequest: JwtRequest): ResponseEntity<*> {
//        try {
//            authenticate(authenticationRequest.username, authenticationRequest.password)
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//        val userDetails: UserDetails = userDetailService.loadUserByUsername(authenticationRequest.username)!!
//        val token: String = jwtTokenUtil.generateToken(userDetails)!!
//        return ResponseEntity.ok<Any>(JwtResponse(token))
//    }
//
//    @Throws(Exception::class)
//    private fun authenticate(username: String, password: String) {
//        try {
//            authenticationManager.authenticate(UsernamePasswordAuthenticationToken(username, password))
//        } catch (e: DisabledException) {
//            throw Exception("USER_DISABLED", e)
//        } catch (e: BadCredentialsException) {
//            throw Exception("INVALID_CREDENTIALS", e)
//        }
//    }
//}

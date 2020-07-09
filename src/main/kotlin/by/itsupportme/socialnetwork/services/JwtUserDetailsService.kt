package by.itsupportme.socialnetwork.services

import by.itsupportme.socialnetwork.beans.User
import by.itsupportme.socialnetwork.repositories.UserRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import java.util.*

@Service
class JwtUserDetailsService(
        @Autowired
        private val repo: UserRepo,
        @Autowired
        private val userRepository: UserRepo
) : UserDetailsService {

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(name: String): UserDetails {
        val user = userRepository.findByName(name) ?: throw UsernameNotFoundException("User not found with username: $name")
        return org.springframework.security.core.userdetails.User(user.name, user.password, ArrayList<GrantedAuthority>())
    }

    fun save(user: User): User {
        return userRepository.save(user)
    }

    fun allUsers(): MutableIterable<User?>{
        return repo.findAll()
    }
}
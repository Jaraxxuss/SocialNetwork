package by.itsupportme.socialnetwork.services

import by.itsupportme.socialnetwork.beans.jwt.JwtRequest
import by.itsupportme.socialnetwork.repositories.JwtRequestRepository
import by.itsupportme.socialnetwork.repositories.UserRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service


@Service
class JwtUserDetailsService(
        @Autowired
        private val repo: JwtRequestRepository,
        @Autowired
        private val userRepo: UserRepo
) : UserDetailsService {

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(s: String): JwtRequest? {

        return repo.getByUser_Name(s)
    }

    fun save(r: JwtRequest): JwtRequest? {

        userRepo.save(r.user)
        return repo.save(r)
    }

    fun delete(name: String) {

        repo.delete(loadUserByUsername(name)!!)
    }

    val allUsers: MutableIterable<JwtRequest?>
        get() = repo.findAll()
}

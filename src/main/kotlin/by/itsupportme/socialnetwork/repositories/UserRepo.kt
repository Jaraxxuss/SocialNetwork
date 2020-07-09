package by.itsupportme.socialnetwork.repositories

import by.itsupportme.socialnetwork.beans.User
import by.itsupportme.socialnetwork.beans.jwt.JwtRequest
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository


@Repository
interface UserRepo : CrudRepository<User?, Long?> {
    fun findByName(name: String?): User?
}

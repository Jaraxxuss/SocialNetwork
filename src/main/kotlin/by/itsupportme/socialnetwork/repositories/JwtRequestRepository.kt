package by.itsupportme.socialnetwork.repositories

import by.itsupportme.socialnetwork.beans.jwt.JwtRequest
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository


@Repository
interface JwtRequestRepository : CrudRepository<JwtRequest?, Long?> {
    fun getByUser_Name(name: String?): JwtRequest?
    fun getByUser_NameAndToken(name:String, token:String) : JwtRequest?

}

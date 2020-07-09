package by.itsupportme.socialnetwork.beans.jwt

import java.io.Serializable
import javax.persistence.Entity

class JwtRequest(
        val name: String,
        val password: String
) : Serializable {
    companion object {
        val serialVersionUid = 5926468583005150707L
    }
}
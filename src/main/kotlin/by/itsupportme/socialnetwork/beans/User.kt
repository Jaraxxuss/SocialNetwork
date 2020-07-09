package by.itsupportme.socialnetwork.beans

import com.fasterxml.jackson.databind.annotation.JsonSerialize
import org.springframework.security.core.userdetails.UserDetails
import java.time.LocalDate
import javax.persistence.Entity
import javax.persistence.OneToMany

@Entity
@JsonSerialize
class User (
        var password : String? = null,
        var bday : LocalDate? = null,
        @OneToMany
        var friends : Set<User> = setOf(),
        @OneToMany
        var album : Set<Image> = setOf(),
        var email : String? = null,
        @OneToMany
        var chats : Set<Message> = setOf()
) : AvatarIdentity()
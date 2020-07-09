package by.itsupportme.socialnetwork.beans

import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.ManyToOne

@Entity
class Message(
        @ManyToOne
        val user : User,
        var content : String,
        val time : LocalDateTime = LocalDateTime.now()
) : Identity()

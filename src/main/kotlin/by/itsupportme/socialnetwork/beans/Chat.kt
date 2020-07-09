package by.itsupportme.socialnetwork.beans

import javax.persistence.Entity
import javax.persistence.ManyToOne
import javax.persistence.OneToMany

@Entity
class Chat (
        @OneToMany
        var users : Set<User> = setOf(),
        @OneToMany
        var messages : Set<Message> = setOf()
) : AvatarIdentity()
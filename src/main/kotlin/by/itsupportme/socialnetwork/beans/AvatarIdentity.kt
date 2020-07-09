package by.itsupportme.socialnetwork.beans

import javax.persistence.MappedSuperclass
import javax.persistence.OneToOne

@MappedSuperclass
open class AvatarIdentity(
        @OneToOne
        var image: Image? = null) : NameIdentity()
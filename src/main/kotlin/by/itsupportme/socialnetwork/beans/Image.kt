package by.itsupportme.socialnetwork.beans

import javax.persistence.Entity

@Entity
class Image(var path : String? = null) : NameIdentity()
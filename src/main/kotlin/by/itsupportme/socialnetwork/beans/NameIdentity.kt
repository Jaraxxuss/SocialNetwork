package by.itsupportme.socialnetwork.beans

import javax.persistence.MappedSuperclass

@MappedSuperclass
open class NameIdentity  (
        var name : String? = null
) : Identity() {
    override fun toString(): String {
        return "NameIdentity(name=$name)"
    }
}
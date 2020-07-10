package by.itsupportme.socialnetwork.beans

import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.MappedSuperclass

@MappedSuperclass
open class Identity (
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id : Long = 0

) {
        override fun toString(): String {
                return "Identity(id=$id)"
        }

        override fun equals(other: Any?): Boolean {
                if (this === other) return true
                if (javaClass != other?.javaClass) return false

                other as Identity

                if (id != other.id) return false

                return true
        }

        override fun hashCode(): Int {
                return id.hashCode()
        }

}
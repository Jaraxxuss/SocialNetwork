package by.itsupportme.socialnetwork.beans.jwt

import by.itsupportme.socialnetwork.beans.Identity
import by.itsupportme.socialnetwork.beans.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.io.Serializable
import javax.persistence.Entity
import javax.persistence.OneToOne

@Entity
class JwtRequest(
        @OneToOne
        var user: User = User()

) : Serializable, UserDetails, Identity(){
    companion object {
        val serialVersionUid = 5926468583005150707L;
    }

    constructor(name : String, password: String) : this(){
        this.password = password
        this.username = name
    }

    fun setUsername(name: String){
        this.user.name = name
    }

    fun setPassword(password: String){
        this.user.password = password
    }

    override fun getUsername(): String {
        return user.name!!
    }

    override fun getPassword(): String {
        return user.password!!
    }

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return mutableListOf()
    }

    override fun isEnabled(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }
}
package by.itsupportme.socialnetwork.utils

import by.itsupportme.socialnetwork.beans.jwt.JwtRequest
import by.itsupportme.socialnetwork.services.JwtUserDetailsService
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.cglib.core.internal.Function
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.util.*
import kotlin.collections.HashMap

@Component
class JwtTokenUtil(
        @Autowired
        val jwtUserDetailsService: JwtUserDetailsService
) {

    @Value("\${jwt.secret}")
    lateinit var secret : String

    companion object{
        const val serialVersionUID = -2550185165626007488L
        const val JWT_TOKEN_VALIDITY = 5 * 60 * 60.toLong()
    }

    fun <T> getClaimFromToken(token: String, claimsResolver : Function<Claims, T>): T {
        val claims: Claims = getAllClaimsFromToken(token)
        return claimsResolver.apply(claims)
    }

    private fun getAllClaimsFromToken(token: String): Claims {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody()
    }

    //  retrieve username from JWT token
    fun getUsernameFromToken(token: String): String {
        return getClaimFromToken(token, Function(Claims::getSubject))
    }

    //  retrieve expiration date from token
    fun getExpirationDateFromToken(token: String): Date {
        return getClaimFromToken(token, Function(Claims::getExpiration))
    }

    //  check if token is expired
    fun isTokenExpired(token: String): Boolean {
        val expiration = getExpirationDateFromToken(token)
        return expiration.before(Date())
    }

    //  generate token for user
    fun generateToken(ud: JwtRequest): String? {
        val claims: Map<String, Any> = HashMap()

        val token = doGenerateToken(claims, ud.username)

        ud.token = token

        jwtUserDetailsService.save(ud)

        return token
    }

    /*
    *   while creating the token -
    *   1. Define claims of the token, f.e. Issuer, Expiration, Subject, Id
    *   2. Sign the JWT using the HS512 algorithm and secret key
    *   3. According to JWS Compact Serializable
    *   compaction of the JWT to a URL-safe string
    * */
    private fun doGenerateToken(claims: Map<String, Any>, subject: String): String? {
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(Date(System.currentTimeMillis()))
                .setExpiration(Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512, secret).compact()
    }

    //  Token validation
    fun validateToken(token: String, ud: UserDetails): Boolean? {
        val username = getUsernameFromToken(token)

        val jwtRequest = jwtUserDetailsService.loadUserByUsername(username)

        print("\n\n\n\nHERE IS CHECK\n${username}\n${token}\n${jwtRequest.toString()}\n")

        return username == ud.username && !isTokenExpired(token) && jwtUserDetailsService.getJwtRequestByNameAndToken(username,token) != null
    }

    fun invalidateToken(jwtRequest: JwtRequest){

        jwtUserDetailsService.loadUserByUsername(jwtRequest.username)!!.token = null
    }
}
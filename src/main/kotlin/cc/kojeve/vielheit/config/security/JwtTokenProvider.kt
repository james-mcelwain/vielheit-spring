package cc.kojeve.vielheit.config.security

import cc.kojeve.vielheit.domain.Role
import cc.kojeve.vielheit.util.RestException
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component
import java.time.Instant
import java.util.*
import javax.servlet.http.HttpServletRequest

@Component
class JwtTokenProvider(val userDetailsService: UserDetailsService) {
    fun createToken(username: String, roles: Set<Role>) : String {
        val claims = Jwts.claims()
        claims.subject = username
        claims.put("auth", roles.map { SimpleGrantedAuthority(it.authority) })

        val now = Instant.now()
        val expiration = now.plusSeconds(1000)

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(expiration))
                .signWith(SignatureAlgorithm.HS256, "")
                .compact()
    }

    fun getAuthentication(token: String): Authentication {
        val userDetails = userDetailsService.loadUserByUsername(getUsername(token))
        return UsernamePasswordAuthenticationToken(userDetails, "", userDetails.authorities)
    }

    fun getUsername(token: String): String {
        return Jwts.parser().setSigningKey("").parseClaimsJws(token).body.subject;
    }

    fun resolveToken(req: HttpServletRequest): String? {
        val bearerToken = req.getHeader("Authorization")
        return if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            bearerToken.substring(7)
        } else null
    }

    fun validateToken(token: String): Boolean {
        try {
            Jwts.parser().setSigningKey("").parseClaimsJws(token)
            return true
        } catch (e: JwtException) {
            throw RestException.InternalServeError()
        } catch (e: IllegalArgumentException) {
            throw RestException.InternalServeError()
        }

    }

}
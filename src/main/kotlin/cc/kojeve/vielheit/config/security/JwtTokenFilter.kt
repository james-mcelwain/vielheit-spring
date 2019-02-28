package cc.kojeve.vielheit.config.security

import cc.kojeve.vielheit.util.RestException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


class JwtTokenFilter(val jwtTokenProvider: JwtTokenProvider) : OncePerRequestFilter() {
    override fun doFilterInternal(req: HttpServletRequest, res: HttpServletResponse, filters: FilterChain) {
        val token = jwtTokenProvider.resolveToken(req)
        try {
            if (token != null && jwtTokenProvider.validateToken(token)) {
                val auth = jwtTokenProvider.getAuthentication(token)
                SecurityContextHolder.getContext().authentication = auth
            }
        } catch (ex: RestException) {
            SecurityContextHolder.clearContext()
            throw ex
        }

        filters.doFilter(req, res)
    }
}
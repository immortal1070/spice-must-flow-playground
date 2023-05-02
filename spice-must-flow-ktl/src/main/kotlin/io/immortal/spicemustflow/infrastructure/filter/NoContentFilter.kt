package io.immortal.spicemustflow.infrastructure.filter

import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException

/**
 * Sets 204 code in case of null response.
 * Similar approach is used in IDDD book by Vernon Vaughn and is a default Resteasy behaviour.
 */
@Component
class NoContentFilter : OncePerRequestFilter() {
    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(
        request: HttpServletRequest,
        reponse: HttpServletResponse, filterChain: FilterChain
    ) {
        filterChain.doFilter(request, reponse)
        if (reponse.contentType == null || reponse.contentType == "") {
            reponse.status = HttpStatus.NO_CONTENT.value()
        }
    }
}
package io.immortal.spicemustflow.web.filter

import io.immortal.spicemustflow.common.stereotype.ApplicationScoped
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.web.filter.OncePerRequestFilter

/**
 * Sets 204 code in case of null response.
 * Similar approach is used in IDDD book by Vernon Vaughn and is a default Resteasy behaviour.
 *
 * Note: returning 404 response in this case is also totally valid approach.
 */
@ApplicationScoped
class NoContentFilter : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        reponse: HttpServletResponse, filterChain: FilterChain
    ) {
        filterChain.doFilter(request, reponse)
        if (reponse.contentType.isEmpty()) {
            reponse.status = HttpStatus.NO_CONTENT.value()
        }
    }
}
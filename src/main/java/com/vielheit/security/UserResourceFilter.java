package com.vielheit.security;

import com.vielheit.core.exception.ErrorCode;
import com.vielheit.core.exception.ErrorResponse;
import com.vielheit.core.domain.Role;
import com.vielheit.security.auth.JwtAuthenticationToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

@UserResource
public class UserResourceFilter implements ContainerRequestFilter
{
    private final static Logger logger = LoggerFactory.getLogger(UserResourceFilter.class);

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {

        MultivaluedMap<String, String> pathParams = requestContext.getUriInfo().getPathParameters();

        if (!isAdmin(requestContext) && pathParams.containsKey("user-id")) {
            Optional<String> idPathParam = pathParams.getOrDefault("user-id", Collections.emptyList()).stream().findFirst();

            if (idPathParam.isPresent()) {
                Long id = Long.valueOf(idPathParam.get());
                Long ctxId = ((JwtAuthenticationToken) requestContext.getSecurityContext().getUserPrincipal()).getUserContext().getUserId();

                if (!ctxId.equals(id)) {
                    requestContext.abortWith(Response
                            .status(Response.Status.UNAUTHORIZED)
                            .entity(ErrorResponse.of("Unauthorized", ErrorCode.AUTHENTICATION))
                            .build());
                }
            }
        }
    }

    private boolean isAdmin(ContainerRequestContext requestContext) {
        return ((JwtAuthenticationToken) requestContext.getSecurityContext().getUserPrincipal())
                .getUserContext()
                .getAuthorities()
                .stream()
                .anyMatch(a -> a.getAuthority().equals(Role.ADMIN.authority()));
    }
}

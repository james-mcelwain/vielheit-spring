package com.vielheit.core.utility;

import com.vielheit.core.UserScope;
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

@UserScope
public class UserScopeFilter implements ContainerRequestFilter
{
    private final static Logger logger = LoggerFactory.getLogger(UserScopeFilter.class);

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {

        MultivaluedMap<String, String> pathParams = requestContext.getUriInfo().getPathParameters();
        if (pathParams.containsKey("id")) {

            Optional<String> _id = pathParams.getOrDefault("id", Collections.emptyList()).stream().findFirst();
            if (_id.isPresent()) {
                Long id = Long.valueOf(_id.get());
                Long ctxId = ((JwtAuthenticationToken) requestContext.getSecurityContext().getUserPrincipal()).getUserContext().getUserId();

                if (!ctxId.equals(id)) {
                    requestContext.abortWith(Response
                            .status(Response.Status.UNAUTHORIZED)
                            .entity("Authentication failed")
                            .build());
                }
            }
        }
    }
}

package com.vielheit.core.auth.ajax;

import com.vielheit.core.auth.UserContext;
import com.vielheit.core.domain.User;
import com.vielheit.core.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.Collections;

@Component
public class AjaxAuthenticationProvider implements AuthenticationProvider {
    private final BCryptPasswordEncoder encoder;
    private UserRepository userRepository;

    @Inject
    public AjaxAuthenticationProvider(final  UserRepository userRepository, final BCryptPasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();

        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("Email not found " + email);
        }

        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(user.getRole());
        UserContext userContext = UserContext.create(email, grantedAuthority);

        return new UsernamePasswordAuthenticationToken(userContext, null, Collections.singletonList(userContext.getAuthority()));
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }
}

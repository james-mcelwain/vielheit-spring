package com.vielheit.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vielheit.core.repository.LoginAttemptRepository;
import com.vielheit.security.RestAuthenticationEntryPoint;
import com.vielheit.security.auth.ajax.AjaxAuthenticationProvider;
import com.vielheit.security.auth.ajax.AjaxLoginProcessingFilter;
import com.vielheit.security.auth.jwt.JwtAuthenticationProvider;
import com.vielheit.security.auth.jwt.JwtTokenAuthenticationProcessingFilter;
import com.vielheit.security.auth.jwt.SkipPathRequestMatcher;
import com.vielheit.security.auth.jwt.extractor.TokenExtractor;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CommonsRequestLoggingFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.inject.Inject;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final Logger log = Logger.getLogger(WebSecurityConfig.class);

    public static final String JWT_TOKEN_HEADER_PARAM = "X-Authorization";
    public static final String FORM_BASED_LOGIN_ENTRY_POINT = "/api/auth/login";
    public static final String TOKEN_BASED_AUTH_ENTRY_POINT = "/api/**";
    public static final String TOKEN_REFRESH_ENTRY_POINT = "/api/auth/token";
    public static final String REGISTER_POINT = "/api/auth/register";

    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;
    private AuthenticationSuccessHandler authenticationSuccessHandler;
    private AuthenticationFailureHandler authenticationFailureHandler;
    private AjaxAuthenticationProvider ajaxAuthenticationProvider;
    private JwtAuthenticationProvider jwtAuthenticationProvider;
    private TokenExtractor tokenExtractor;
    private AuthenticationManager authenticationManager;
    private ObjectMapper objectMapper;
    private LoginAttemptRepository loginAttemptRepository;

    @Inject
    public WebSecurityConfig(
            @NotNull RestAuthenticationEntryPoint restAuthenticationEntryPoint,
            @NotNull AuthenticationSuccessHandler authenticationSuccessHandler,
            @NotNull AuthenticationFailureHandler authenticationFailureHandler,
            @NotNull AjaxAuthenticationProvider ajaxAuthenticationProvider,
            @NotNull JwtAuthenticationProvider jwtAuthenticationProvider,
            @NotNull TokenExtractor tokenExtractor,
            @NotNull AuthenticationManager authenticationManager,
            @NotNull ObjectMapper objectMapper,
            @NotNull LoginAttemptRepository loginAttemptRepository
    ) {
        this.restAuthenticationEntryPoint = Objects.requireNonNull(restAuthenticationEntryPoint);
        this.authenticationSuccessHandler = Objects.requireNonNull(authenticationSuccessHandler);
        this.authenticationFailureHandler = Objects.requireNonNull(authenticationFailureHandler);
        this.ajaxAuthenticationProvider = Objects.requireNonNull(ajaxAuthenticationProvider);
        this.jwtAuthenticationProvider = Objects.requireNonNull(jwtAuthenticationProvider);
        this.tokenExtractor = Objects.requireNonNull(tokenExtractor);
        this.authenticationManager = Objects.requireNonNull(authenticationManager);
        this.objectMapper = Objects.requireNonNull(objectMapper);
        this.loginAttemptRepository = Objects.requireNonNull(loginAttemptRepository);
    }

    @Bean
    protected AjaxLoginProcessingFilter buildAjaxLoginProcessingFilter() {
        AjaxLoginProcessingFilter filter = new AjaxLoginProcessingFilter(FORM_BASED_LOGIN_ENTRY_POINT, authenticationSuccessHandler, authenticationFailureHandler, objectMapper, loginAttemptRepository);
        filter.setAuthenticationManager(this.authenticationManager);
        return filter;
    }

    @Bean
    protected JwtTokenAuthenticationProcessingFilter buildJwtTokenAuthenticationProcessingFilter() {
        List<String> pathsToSkip = Arrays.asList(TOKEN_REFRESH_ENTRY_POINT, FORM_BASED_LOGIN_ENTRY_POINT, REGISTER_POINT);
        SkipPathRequestMatcher matcher = new SkipPathRequestMatcher(pathsToSkip, TOKEN_BASED_AUTH_ENTRY_POINT);
        JwtTokenAuthenticationProcessingFilter filter
                = new JwtTokenAuthenticationProcessingFilter(authenticationFailureHandler, tokenExtractor, matcher);
        filter.setAuthenticationManager(this.authenticationManager);
        return filter;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(ajaxAuthenticationProvider);
        auth.authenticationProvider(jwtAuthenticationProvider);
    }

    @Bean
    public CommonsRequestLoggingFilter requestLoggingFilter() {
        CommonsRequestLoggingFilter loggingFilter = new CommonsRequestLoggingFilter();
        loggingFilter.setIncludeClientInfo(true);
        loggingFilter.setIncludeQueryString(true);
        loggingFilter.setIncludePayload(true);
        return loggingFilter;
    }

    @Bean
    protected BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // HACKY CORS FILTER
    private class CorsFilter extends OncePerRequestFilter {
        @Override
        protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
            response.setHeader("Access-Control-Max-Age", "3600");
            response.setHeader("Access-Control-Allow-Headers", "authorization, content-type, xsrf-token, x-authorization, cache-control");
            response.addHeader("Access-Control-Expose-Headers", "xsrf-token");
            if ("OPTIONS".equals(request.getMethod())) {
                response.setStatus(HttpServletResponse.SC_OK);
            } else {
                filterChain.doFilter(request, response);
            }
        }
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // We don't need CSRF for JWT based authentication
                .exceptionHandling()
                .authenticationEntryPoint(restAuthenticationEntryPoint)

                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .authorizeRequests()
                .antMatchers(FORM_BASED_LOGIN_ENTRY_POINT).permitAll() // Login end-point
                .antMatchers(TOKEN_REFRESH_ENTRY_POINT).permitAll() // Token refresh end-point
                .antMatchers(REGISTER_POINT).permitAll() // Registration end-point
                .and()
                .authorizeRequests()
                .antMatchers(TOKEN_BASED_AUTH_ENTRY_POINT).authenticated() // Protected API End-points
                .and()
                .addFilterBefore(new CorsFilter(), ChannelProcessingFilter.class)
                .addFilterBefore(buildAjaxLoginProcessingFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(buildJwtTokenAuthenticationProcessingFilter(), UsernamePasswordAuthenticationFilter.class);
    }

}

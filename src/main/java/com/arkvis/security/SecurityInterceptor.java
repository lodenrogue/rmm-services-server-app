package com.arkvis.security;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.arkvis.error.UnauthorizedException;
import com.arkvis.model.Credentials;
import com.arkvis.repository.credentials.CredentialsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class SecurityInterceptor extends HandlerInterceptorAdapter {
    private static final int COST = 12;

    private static final String AUTH_HEADER = "Authorization";

    @Autowired
    private CredentialsRepository credentialsRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (!isPermittedWithoutAuth(request.getRequestURI())) {
            String auth = request.getHeader(AUTH_HEADER);

            if (auth == null) {
                throw new UnauthorizedException("Authorization header must be supplied");
            }

            Credentials credentials = credentialsRepository.findOne(1L);
            String salt = credentials.getSalt();
            String password = credentials.getPassword();
            String encodedAuth = hash(salt, auth);

            if (password.equals(encodedAuth)) {
                return true;
            } else {
                throw new UnauthorizedException("Invalid credentials");
            }
        }
        return true;
    }

    private String hash(String salt, String secret) {
        byte[] hashed = BCrypt.withDefaults().hash(COST, salt.getBytes(), secret.getBytes());
        return new String(hashed);
    }

    private boolean isPermittedWithoutAuth(String requestURI) {
        return requestURI.contains("swagger") || requestURI.contains("v2/api-docs");
    }
}

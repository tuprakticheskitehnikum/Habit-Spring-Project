package com.example.demo.security.jwt;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AuthenticationEntryPointJwt implements AuthenticationEntryPoint {
    //private static final Logger logger = LogManager.getLogger(AuthenticationEntryPointJwt.class);

    @Override
    public void commence(HttpServletRequest httpServletRequest,
                         HttpServletResponse httpServletResponse,
                         AuthenticationException authenticationException) throws IOException {
        //logger.error("Unauthorized error: {}", authenticationException.getMessage());
        httpServletResponse.sendError(httpServletResponse.SC_UNAUTHORIZED, "Error: Unauthorized");
    }
}

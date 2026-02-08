package com.gabfmm.gerenciador_de_senhas.auth;

import com.gabfmm.gerenciador_de_senhas.service.JwtService;
import io.jsonwebtoken.JwtException;
import org.jspecify.annotations.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.stereotype.Component;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;

/*
* This class intercepts all endpoints before going to the controllers
*
* The sequence is:
* shouldNotFilter (if false) -> doFilterInternal -> controller
* shouldNotFilter (if true) -> controller
* */

@Component
public class AuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final SessionContext sessionContext;

    public AuthFilter(JwtService jwtService, SessionContext sessionContext) {
        this.sessionContext = sessionContext;
        this.jwtService = jwtService;
    }

    protected void sendError(HttpServletResponse response, HttpStatus status, String detail) throws IOException {
        ProblemDetail problemDetail = ProblemDetail.forStatus(status);
        problemDetail.setTitle("Erro");
        problemDetail.setDetail(detail);

        response.setStatus(status.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getWriter(), problemDetail);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        String method = request.getMethod();

        return
                path.equals("/h2-console") ||
                (path.equals("/users") && method.equals("POST")) ||
                (path.equals("/auth/login") && method.equals("POST"));
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain chain
    ) throws IOException, ServletException {

        String authHeader =
                request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            sendError(response, HttpStatus.UNAUTHORIZED, "Authorization header missing or invalid");
            return;
        }

        String token = authHeader.substring(7);

        try {
            jwtService.validateToken(token);
            chain.doFilter(request, response);
        }
        catch (JwtException | IllegalArgumentException e) {
            sendError(response, HttpStatus.UNAUTHORIZED, "Invalid or expired token");
        }
        finally {
            sessionContext.clear();
        }
    }
}

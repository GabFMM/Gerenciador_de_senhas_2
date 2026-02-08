package com.gabfmm.gerenciador_de_senhas.service;

import com.gabfmm.gerenciador_de_senhas.auth.SessionContext;
import com.gabfmm.gerenciador_de_senhas.auth.TokenKeyProvider;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JwtService {

    private final TokenKeyProvider tokenKeyProvider;
    private final SessionContext sessionContext;

    public JwtService(TokenKeyProvider tokenKeyProvider,
                      SessionContext sessionContext
    ){
        this.tokenKeyProvider = tokenKeyProvider;
        this.sessionContext = sessionContext;
    }

    public String generateToken(
            String userId
    ) {
        Key hmacKey = Keys.hmacShaKeyFor(tokenKeyProvider.getTokenKey().getEncoded());

        return Jwts.builder()
                .setSubject(userId)
                .setIssuedAt(new Date())
                .setExpiration(
                        new Date(System.currentTimeMillis() + 60 * 60 * 1000)
                ) // 1h
                .signWith(hmacKey, SignatureAlgorithm.HS256)
                .compact();
    }

    /*
        this method can throw an exception if token is invalid
        if token is valid, then the id from it is saved

        the exceptions are:
        - ExpiredJwtException
        - MalformedJwtException
        - UnsupportedJwtException (algorithm or type not accepted)
        - SecurityException (invalid signature)
        - IllegalArgumentException (empty or null token)
     */
    public void validateToken(
            String token
    ) {
        Key hmacKey = Keys.hmacShaKeyFor(tokenKeyProvider.getTokenKey().getEncoded());

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(hmacKey)
                .build()
                .parseClaimsJws(token)
                .getBody();

        sessionContext.setUserId(claims.getSubject());
    }

}

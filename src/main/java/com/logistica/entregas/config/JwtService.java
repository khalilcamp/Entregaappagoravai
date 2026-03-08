package com.logistica.entregas.config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {
    private String SECRET_KEY = "Y2hhdmVTZWNyZXRhU3VwZXJTZWd1cmFQYXJhSldUMTIzNDU2Nzg=";

    public String gerarToken(String login){
        SecretKey userChave = Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_KEY));
        return Jwts.builder().subject(login).expiration(new Date(System.currentTimeMillis() + 86400000))
                .signWith(userChave).compact();
    }
    public String extrairLogin(String token){
        SecretKey chaveSegura = Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_KEY));
        return Jwts.parser().verifyWith(chaveSegura).build().parseSignedClaims(token).getPayload().getSubject();
    }
}

package com.sentinelvault.security;


import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;



@Component
public class JwtUtil {


    private final String SECRET_KEY =
            "sentinelvaultsecretkeysentinelvaultsecretkey123456";



    private final SecretKey key =
            Keys.hmacShaKeyFor(
                    SECRET_KEY.getBytes()
            );





    // Generate JWT Token
    public String generateToken(String email) {


        return Jwts.builder()

                .setSubject(email)


                .setIssuedAt(
                        new Date()
                )


                .setExpiration(

                        new Date(

                                System.currentTimeMillis()
                                + 1000 * 60 * 60 * 24

                        )

                )


                .signWith(
                        key,
                        SignatureAlgorithm.HS256
                )


                .compact();

    }







    // Extract Email
    public String extractEmail(String token) {


        Claims claims =

                Jwts.parserBuilder()

                .setSigningKey(key)

                .build()

                .parseClaimsJws(token)

                .getBody();



        return claims.getSubject();

    }







    // Validate Token
    public boolean validateToken(String token) {


        try {


            Jwts.parserBuilder()

            .setSigningKey(key)

            .build()

            .parseClaimsJws(token);



            return true;


        }

        catch(Exception e) {


            System.out.println(
                    "JWT ERROR : "
                    + e.getMessage()
            );


            return false;

        }


    }


}
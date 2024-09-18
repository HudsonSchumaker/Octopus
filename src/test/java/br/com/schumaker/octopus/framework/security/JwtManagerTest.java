package br.com.schumaker.octopus.framework.security;

import static org.junit.jupiter.api.Assertions.*;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Test;
import java.util.Date;

class JwtManagerTest {
    private static final String TEST_SUBJECT = "testSubject";
    private static final String TEST_SECRET_KEY = "testSecretKey";
    private static final String TEST_ISSUER = "testIssuer";

    @Test
    void testGenerateToken() {
        String token = JwtManager.generateToken(TEST_SUBJECT);
        assertNotNull(token);

        Claims claims = Jwts.parser()
                .setSigningKey(TEST_SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();

        assertEquals(TEST_SUBJECT, claims.getSubject());
        assertEquals(TEST_ISSUER, claims.getIssuer());
        assertNotNull(claims.getIssuedAt());
        assertNotNull(claims.getExpiration());
    }

    @Test
    void testValidateToken() {
        String token = JwtManager.generateToken(TEST_SUBJECT);
        Claims claims = JwtManager.validateToken(token);
        assertNotNull(claims);
        assertEquals(TEST_SUBJECT, claims.getSubject());
    }

    @Test
    void testValidateTokenExpired() {
        String token = Jwts.builder()
                .setSubject(TEST_SUBJECT)
                .setIssuer(TEST_ISSUER)
                .setIssuedAt(new Date(System.currentTimeMillis() - 10000)) // 10 seconds ago
                .setExpiration(new Date(System.currentTimeMillis() - 5000)) // 5 seconds ago
                .signWith(SignatureAlgorithm.HS256, TEST_SECRET_KEY)
                .compact();

        Claims claims = JwtManager.validateToken(token);
        assertNull(claims);
    }

    @Test
    void testValidateTokenInvalidSignature() {
        String token = Jwts.builder()
                .setSubject(TEST_SUBJECT)
                .setIssuer(TEST_ISSUER)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 7200000)) // 2 hours from now
                .signWith(SignatureAlgorithm.HS256, "invalidSecretKey")
                .compact();

        Claims claims = JwtManager.validateToken(token);
        assertNull(claims);
    }
}

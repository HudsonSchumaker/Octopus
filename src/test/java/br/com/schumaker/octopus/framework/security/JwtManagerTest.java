package br.com.schumaker.octopus.framework.security;

import static org.junit.jupiter.api.Assertions.*;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Test;
import java.util.Date;

/**
 * The JwtManagerTest class.
 * This class is responsible for testing the JwtManager class.
 *
 * @author Hudson Schumaker
 * @version 1.0.0
 */
class JwtManagerTest {
    private static final String TEST_SUBJECT = "testSubject";
    private static final String TEST_SECRET_KEY = "testSecretKey";
    private static final String TEST_ISSUER = "testIssuer";

    @Test
    void testGenerateToken() {
        // Arrange
        String token = JwtManager.generateToken(TEST_SUBJECT);
        assertNotNull(token);

        // Act
        Claims claims = Jwts.parser()
                .setSigningKey(TEST_SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();

        // Assert
        assertEquals(TEST_SUBJECT, claims.getSubject());
        assertEquals(TEST_ISSUER, claims.getIssuer());
        assertNotNull(claims.getIssuedAt());
        assertNotNull(claims.getExpiration());
    }

    @Test
    void testValidateToken() {
        // Arrange
        String token = JwtManager.generateToken(TEST_SUBJECT);

        // Act
        Claims claims = JwtManager.validateToken(token);

        // Assert
        assertNotNull(claims);
        assertEquals(TEST_SUBJECT, claims.getSubject());
    }

    @Test
    void testValidateTokenExpired() {
        // Arrange
        String token = Jwts.builder()
                .setSubject(TEST_SUBJECT)
                .setIssuer(TEST_ISSUER)
                .setIssuedAt(new Date(System.currentTimeMillis() - 10000)) // 10 seconds ago
                .setExpiration(new Date(System.currentTimeMillis() - 5000)) // 5 seconds ago
                .signWith(SignatureAlgorithm.HS256, TEST_SECRET_KEY)
                .compact();

        // Act
        Claims claims = JwtManager.validateToken(token);

        // Assert
        assertNull(claims);
    }

    @Test
    void testValidateTokenInvalidSignature() {
        // Arrange
        String token = Jwts.builder()
                .setSubject(TEST_SUBJECT)
                .setIssuer(TEST_ISSUER)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 7200000)) // 2 hours from now
                .signWith(SignatureAlgorithm.HS256, "invalidSecretKey")
                .compact();

        // Act
        Claims claims = JwtManager.validateToken(token);

        // Assert
        assertNull(claims);
    }
}

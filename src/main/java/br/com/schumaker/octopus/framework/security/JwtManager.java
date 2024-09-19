package br.com.schumaker.octopus.framework.security;

import br.com.schumaker.octopus.framework.ioc.AppProperties;
import br.com.schumaker.octopus.framework.ioc.Environment;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Date;
import java.util.Optional;

/**
 * The JwtService class provides methods to generate and validate JWT tokens.
 * It uses the secret key, issuer, and expiration time defined in the environment properties.
 *
 * @see Environment
 * @see AppProperties
 *
 * @author Hudson Schumaker
 * @version 1.0.0
 */
public final class JwtManager {
    public static final String JWT_MESSAGE = "Invalid token.";
    public static final String JWT_HEADER = "Authorization";

    private static final String SECRET_KEY;
    private static final String ISSUER;
    private static final Long EXPIRATION;

    static {
        Environment environment = Environment.getInstance();
        SECRET_KEY = Optional.ofNullable(environment.getKey(AppProperties.JWT_SECRET))
                .orElseGet(JwtManager::generateSecretKey);

        ISSUER = Optional.ofNullable(environment.getKey(AppProperties.APP_NAME))
                .orElse(AppProperties.APP_NAME);

        EXPIRATION = environment.getJwtExpiration();
    }

    /**
     * Generates a JWT token for the specified subject.
     *
     * @param subject the subject of the token.
     * @return the generated token.
     */
    public static String generateToken(String subject) {
        return Jwts.builder()
                .setSubject(subject)
                .setIssuer(ISSUER)
                .setIssuedAt(new Date())
                .setExpiration(getExpiration())
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    /**
     * Validates the JWT token and returns the claims if the token is valid.
     *
     * @param token the token to validate.
     * @return the claims if the token is valid; otherwise, null.
     */
    public static Claims validateToken(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (SignatureException | ExpiredJwtException e) {
            return null;
        }
    }

    /**
     * Generates the expiration date for the JWT token.
     *
     * @return the expiration date.
     */
    private static Date getExpiration() {
        LocalDateTime localDateTime = LocalDateTime.now().plusSeconds(EXPIRATION);
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * Generates a random secret key for the JWT token.
     *
     * @return the secret key.
     */
    private static String generateSecretKey() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] key = new byte[32]; // 256-bit key
        secureRandom.nextBytes(key);
        return Base64.getEncoder().encodeToString(key);
    }
}

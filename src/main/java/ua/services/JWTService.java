package ua.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator.Builder;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import ua.excaptions.UserVarificationException;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;


public class JWTService implements JWTServiceInterface {

    private String secret = "secret";
    private String issuer = "USERSERVICE";
    private String subject = "userLoginToken";
    private String audience = "APP";

    public String createToken(Map<String, String> claims, int hour) {
        Payload payload = new Payload();

        payload.setIssuer(this.issuer);
        payload.setSubject(this.subject);
        payload.setAudience(this.audience);

        payload.setIssuedAt(LocalDateTime.now());
        payload.setExpiresAt(payload.getIssuedAt().plusHours(hour));


        payload.setClaims(claims);
        Algorithm hmac256 = Algorithm.HMAC256(this.secret);

        Builder headBuilder = JWT.create().withHeader(buildJWTHeader(hmac256));

        Builder publicClaimbuilder = addPublicClaimBuilder(headBuilder, payload);
        Builder privateClaimbuilder = addPrivateClaimbuilder(publicClaimbuilder, payload);


        return privateClaimbuilder.sign(hmac256);
    }

    private Map<String, Object> buildJWTHeader(Algorithm algorithm) {
        Map<String, Object> map = new HashMap<>();
        map.put("alg", algorithm.getName());
        map.put("typ", "JWT");
        return map;
    }

    private Builder addPrivateClaimbuilder(Builder builder, Payload payload) {
        Map<String, String> claims = payload.getClaims();
        if (!claims.isEmpty()) {
            claims.forEach((k, v) -> builder.withClaim(k, v));
        }
        return builder;
    }


    private Builder addPublicClaimBuilder(Builder builder, Payload payload) {
        if (!payload.getIssuer().isEmpty()) {
            builder.withIssuer(payload.getIssuer());
        }

        if (!payload.getSubject().isEmpty()) {
            builder.withSubject(payload.getSubject());
        }

        if (payload.getIssuedAt() != null) {
            builder.withIssuedAt(Date.from(payload.getIssuedAt().atZone(ZoneId.systemDefault()).toInstant()));
        }

        if (payload.getExpiresAt() != null) {
            builder.withExpiresAt(Date.from(payload.getExpiresAt().atZone(ZoneId.systemDefault()).toInstant()));
        }

        if (payload.getAudience().isEmpty()) {
            payload.getAudience().forEach(s -> builder.withAudience(s));
        }

        return builder;
    }


    public Payload verifyToken(String token) throws UserVarificationException {

        try {
            DecodedJWT jwt = JWT.require(Algorithm.HMAC256(this.secret)).build().verify(token);

            Payload payload = new Payload();
            payload.setIssuer(jwt.getIssuer());
            payload.setSubject(jwt.getSubject());
            payload.setAudience(jwt.getAudience());
            payload.setIssuedAt(jwt.getIssuedAt().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
            payload.setExpiresAt(jwt.getExpiresAt().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());

            Map<String, String> claims = new LinkedHashMap<>();
            jwt.getClaims().forEach((k, v) -> claims.put(k, v.asString()));
            payload.setClaims(claims);

            return payload;
        } catch (AlgorithmMismatchException | TokenExpiredException e) {
            throw new UserVarificationException(e);
        }

    }
}
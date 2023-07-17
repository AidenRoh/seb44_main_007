package com.server.auth.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

@Component
public class JwtTokenizer {

    @Getter
    @Value("${jwt.key}")
    private String secretKey;

    @Getter
    @Value("${jwt.access-token-expiration-minutes}")
    private int accessTokenExpirationMinutes;

    @Getter
    @Value("${jwt.refresh-token-expiration-minutes}")
    private int refreshTokenExpirationMinutes;



    public String encodeBase64SecretKey(String secretKey) { // Base64 형식의 문자열로 인코딩
        return Encoders.BASE64.encode(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    public String generateAccessToken(Map<String, Object> claims,
                                      String subject,
                                      Date expiration,
                                      String base64EncodedSecretKey) {
        Key key = getKeyFromBase64EncodedKey(base64EncodedSecretKey);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(Calendar.getInstance().getTime()) //발행일자
                .setExpiration(expiration)                     //만료일자
                .signWith(key)
                .compact();
    }
    public String generateRefreshToken(String subject, Date expiration, String base64EncodedSecretKey) {
        Key key = getKeyFromBase64EncodedKey(base64EncodedSecretKey); //키객체얻음

        return Jwts.builder()
                .setSubject(subject)                                //제목추가
                .setIssuedAt(Calendar.getInstance().getTime())      //발행일자
                .setExpiration(expiration)                          //만료일시
                .signWith(key)                                      //서명을위한key객체설정
                .compact();                                         //생성
    }

    public Jws<Claims> getClaims(String jws, String base64EncodedSecretKey) {
        Key key = getKeyFromBase64EncodedKey(base64EncodedSecretKey);

        Jws<Claims> claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(jws);
        return claims;
    }

    public Date getTokenExpiration(long expirationMinutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, (int) expirationMinutes);
        Date expiration = calendar.getTime();

        return expiration;
    }

    private Key getKeyFromBase64EncodedKey(String base64EncodedSecretKey) { // jWT 서명에 사용할 비밀키생성
        byte[] keyBytes = Decoders.BASE64.decode(base64EncodedSecretKey);   // Base64 형식으로 인코딩 된 Secret Key를 디코딩한 후, byte array를 반환
        Key key = Keys.hmacShaKeyFor(keyBytes);  // key byte array를 기반으로 적절한 HMAC 알고리즘을 적용한 Key(java.security.Key) 객체를 생성

        return key;
    }

    public String getName(String token){
        Claims claims = getClaims(token, encodeBase64SecretKey(secretKey)).getBody();
        return (String) claims.get("sub");
    }

    // JWT 검증
    public void verifySignature(String jws, String base64EncodedSecretKey) {
        Key key = getKeyFromBase64EncodedKey(base64EncodedSecretKey);

        Jwts.parserBuilder()
                .setSigningKey(key)     // setSigningKey() 메서드로 서명에 사용된 Secret Key를 설정한다.
                .build()
                .parseClaimsJws(jws);   // JWT를 파싱해서 Claims를 얻는다.
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getKeyFromBase64EncodedKey(encodeBase64SecretKey(secretKey)))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
package com.server.auth.filter;

import com.server.auth.utils.CustomAuthorityUtils;
import com.server.auth.jwt.JwtTokenizer;
import com.server.auth.redis.RedisService;
import com.server.member.entity.Member;
import com.server.member.repository.MemberRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

public class JwtVerificationFilter extends OncePerRequestFilter {


    private final JwtTokenizer jwtTokenizer;
    private final CustomAuthorityUtils authorityUtils;
    private final RedisService redisService;
    private final MemberRepository memberRepository;

    public JwtVerificationFilter(JwtTokenizer jwtTokenizer, CustomAuthorityUtils authorityUtils, RedisService redisService, MemberRepository memberRepository) {
        this.jwtTokenizer = jwtTokenizer;
        this.authorityUtils = authorityUtils;
        this.redisService = redisService;
        this.memberRepository = memberRepository;
    }

    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // (1)
        try {
            Map<String, Object> claims = verifyJws(request);
            setAuthenticationToContext(claims);
        } catch (SignatureException se) {
            logger.error("Signature Exception: {}");
            request.setAttribute("exception", se);
        } catch (ExpiredJwtException ee) {
            logger.error("Expired JWT Exception: {}");
            request.setAttribute("exception", ee);
        } catch (Exception e) {
            logger.error("Exception: {}");
            request.setAttribute("exception", e);
        }


        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String authorization = request.getHeader("Authorization");  // (6-1)
        return authorization == null || !authorization.startsWith("Bearer ");
    }

    private Map<String, Object> verifyJws(HttpServletRequest request) {
        String jws = request.getHeader("Authorization").replace("Bearer ", ""); // request의 header에서 JWT를 얻음.
        String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey()); //  JWT 서명(Signature)을 검증하기 위한 Secret Key를 얻음.
        Map<String, Object> claims = jwtTokenizer.getClaims(jws, base64EncodedSecretKey).getBody();   // JWT에서 Claims를 파싱

        return claims;
    }

    private void setAuthenticationToContext(Map<String, Object> claims) {
        String username = (String) claims.get("username");
        List<GrantedAuthority> authorities = authorityUtils.createAuthorities((List) claims.get("roles"));
        Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }


    private String generateNewAccessToken(String refreshToken) {
        String username = jwtTokenizer.getName(refreshToken);
        Member member = memberRepository.findByEmail(username).get();

        Map<String, Object> newClaims = new HashMap<>();

        newClaims.put("username", member.getEmail());
        newClaims.put("roles", member.getRoles());

        String subject = member.getEmail();
        Date expiration = jwtTokenizer.getTokenExpiration(jwtTokenizer.getAccessTokenExpirationMinutes());

        String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey());
        String accessToken = jwtTokenizer.generateAccessToken(newClaims, subject, expiration, base64EncodedSecretKey);
        return accessToken;
    }

    private boolean isRefreshTokenValid(String refreshToken) {
        Jws<Claims> claims = jwtTokenizer.getClaims(refreshToken, jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey()));
        if (claims.getBody().getExpiration().before(new Date())){
            return false;
        }
        return true;
    }

    private boolean isTokenExpired(String accessToken) {
        try {
            jwtTokenizer.getClaims(accessToken, jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey()));
        } catch (ExpiredJwtException e) {
            return true;
        }
        return false;
    }

    private String extractAccessToken(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }
    private String extractRefreshToken(HttpServletRequest request) {
        return request.getHeader("Refresh");
    }






}
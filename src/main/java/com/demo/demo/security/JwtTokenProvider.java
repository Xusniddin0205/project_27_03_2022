package com.demo.demo.security;

import com.demo.demo.entity.RefreshToken;
import com.demo.demo.entity.User;
import com.demo.demo.exception.RefreshTokenException;
import com.demo.demo.repository.RefreshTokenRepository;
import com.demo.demo.repository.UserRepository;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.time.Instant;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Component
public class JwtTokenProvider {

    @Autowired
    private UserRepository userRepository;

    @Value("${app.jwt.secret.key}")
    private String jwtSecret;
    @Value("${app.jwt.token.expirationTimeInMs}")
    private Long expirationTime;

    /*RefreshToken*/
    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Value("${app.jwt.refresh.tokentokenExpirationInMs}")
    private Long expireTimeRefreshToken;

    private static final Logger logger= LoggerFactory.getLogger(JwtTokenProvider.class);

    public String generateToken(Authentication authentication){
        User user=(User) authentication.getPrincipal();
        Date expTime=new Date(new Date().getTime() + expirationTime);
        return Jwts.builder()
                .setSubject(user.getId().toString())
                .setIssuedAt(new Date())
                .setIssuer(user.getUsername())
                .setExpiration(expTime)
                .signWith(SignatureAlgorithm.HS512,jwtSecret)
                .compact();
    }

    public boolean validateToken(String token){
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        }catch (SignatureException e){
            logger.error("Invalid JWT signature");
        }catch (MalformedJwtException ex) {
            logger.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            logger.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            logger.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            logger.error("JWT claims string is empty.");
        }
        return false;

    }

    public String getUserIdFromJwt(String jwtToken){
        Claims claims=Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(jwtToken)
                .getBody();

        System.out.println(claims);
        return claims.getSubject();
    }

    /*Refresh token config*/

    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }


    public RefreshToken createRefreshToken(Integer userId) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(userRepository.findById(userId).get());
        refreshToken.setExpiryDate(Instant.now().plusMillis(expireTimeRefreshToken));
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken = refreshTokenRepository.save(refreshToken);
        return refreshToken;
    }
    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new RefreshTokenException(token.getToken(), "Refresh token was expired. Please make a new signin request");
        }
        return token;
    }
    @Transactional
    public int deleteByUserId(Integer userId) {
        return refreshTokenRepository.deleteByUser(userRepository.findById(userId).get());
        //return refreshTokenRepository.deleteByUser(userRepository.findById(userId).get());
    }


    public String generateTokenFromUsername(User user) {

        Date expTime=new Date(new Date().getTime() + expirationTime);
        return Jwts.builder()
                .setSubject(user.getId().toString())
                .setIssuedAt(new Date())
                .setIssuer(user.getUsername())
                .setExpiration(expTime)
                .signWith(SignatureAlgorithm.HS512,jwtSecret)
                .compact();


    }
}

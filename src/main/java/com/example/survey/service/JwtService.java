package com.example.survey.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Service
@Getter
public class JwtService {

	public static final String KEY_GENERATOR = "";
	public static final long TOKEN_VALIDITY_TIME = 1000 * 60 * 24;

	public String extractUserNameFromTokenString(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	private Claims extractClaims(String token) {
		return Jwts
				.parserBuilder()
				.setSigningKey(getSigningKey())
				.build()
				.parseClaimsJwt(token)
				.getBody();
	}

	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		Claims claims = extractClaims(token);
		return claimsResolver.apply(claims);
	}

	private Key getSigningKey() {
		byte[] keyBytes = Decoders.BASE64.decode(KEY_GENERATOR);
		return Keys.hmacShaKeyFor(keyBytes);
	}

	public String generateToken(Map<String, Object> extractedClaims, UserDetails userDetails) {
		return Jwts.builder()
				.setClaims(extractedClaims)
				.setSubject(userDetails.getUsername())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + TOKEN_VALIDITY_TIME))
				.signWith(getSigningKey(), SignatureAlgorithm.HS256)
				.compact();
	}

	public boolean isTokenValid(String token, UserDetails userDetails) {
		final String username = extractUserNameFromTokenString(token);

		return username.equals(userDetails.getUsername());
	}

	private boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	private Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

}

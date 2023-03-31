package com.example.survey.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletResponse;
import java.security.Key;
import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Service
@Getter
@Slf4j
public class JwtService {


	public String extractUserNameFromTokenString(String token) {
			return extractClaim(token, Claims::getSubject);
	}

	private Claims extractClaims(String token) {
			return Jwts
					.parserBuilder()
					.setSigningKey(generateKey())
					.build()
					.parseClaimsJws(token)
					.getBody();
	}

	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
			Claims claims = extractClaims(token);
			return claimsResolver.apply(claims);
	}

	public Key generateKey() {
		String signInKeyValue = "SIGNINGVALUE";
		byte[] fixedBites = new byte[32];
		byte[] keyBytes = signInKeyValue.getBytes();

		for (int i = 0; i < 32; i++) {
			if(i < keyBytes.length)
				fixedBites[i] = keyBytes[i];
			else fixedBites[i] = 0;
		}

		return new SecretKeySpec(fixedBites, "HmacSHA256");
	}

	public String generateToken(UserDetails userDetails, long validityTime) {
		return generateToken(Collections.emptyMap(), userDetails, validityTime);
	}

	public String generateToken(Map<String, Object> extractedClaims, UserDetails userDetails, long validityTime) {
		return Jwts.builder()
				.setClaims(extractedClaims)
				.setSubject(userDetails.getUsername())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + validityTime))
				.signWith(generateKey(), SignatureAlgorithm.HS256)
				.compact();
	}

	public boolean isTokenValid(String token, UserDetails userDetails) {
		final String username = extractUserNameFromTokenString(token);

		return username.equals(userDetails.getUsername());
	}

	public boolean isTokenExpired(String token) {
		return extractExpiration(token).after(new Date());
	}

	private Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

}

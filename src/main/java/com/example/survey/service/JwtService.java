package com.example.survey.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Service
@Getter
public class JwtService {

	public static final long TOKEN_VALIDITY_TIME = 1000 * 60 * 24;

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

	public String generateToken(UserDetails userDetails) {
		return generateToken(Collections.emptyMap(), userDetails);
	}

	public String generateToken(Map<String, Object> extractedClaims, UserDetails userDetails) {
		return Jwts.builder()
				.setClaims(extractedClaims)
				.setSubject(userDetails.getUsername())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + TOKEN_VALIDITY_TIME))
				.signWith(generateKey(), SignatureAlgorithm.HS256)
				.compact();
	}

	public boolean isTokenValid(String token, UserDetails userDetails) {
		final String username = extractUserNameFromTokenString(token);

		return username.equals(userDetails.getUsername()) && isTokenExpired(token);
	}

	private boolean isTokenExpired(String token) {
		return extractExpiration(token).after(new Date());
	}

	private Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

}

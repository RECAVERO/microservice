package com.spring.demo.security;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.spring.demo.dto.RequestDto;
import com.spring.demo.entities.AuthUser;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtProvider {
	@Value("${jwt.secret}")
	private String secret;
	@Autowired
	RouteValidator routeValidator;
	@PostConstruct
	protected void init() {
		secret=Base64.getEncoder().encodeToString(secret.getBytes());
	}
	
	public String createToken(AuthUser authUser) {
		Map<String,Object> claims=new HashMap<>();
		claims=Jwts.claims().setSubject(authUser.getEmail());
		claims.put("id", authUser.getId());
		claims.put("perfil", authUser.getPerfil());
		Date now=new Date();
		Date exp=new Date(now.getTime() + 3600000);
		return Jwts.builder()
				.setClaims(claims)
				.setIssuedAt(now)
				.setExpiration(exp)
				.signWith(SignatureAlgorithm.HS256, secret)
				.compact();
		
	}
	
	public boolean validate(String token,RequestDto dto) {
		try {
			Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
			
		}catch(Exception e) {
			return false;
		}
		
		if(!isAdmin(token) && routeValidator.isAdminPath(dto)) {
			return false;
		}
		
		return true;
	}
	
	private boolean isAdmin(String token) {
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().get("perfil").equals("admin");
	}
	
	public String getUserNameFromToken(String token) {
		try {
			return Jwts.parser().setSigningKey(secret).parseClaimsJws(token)
			.getBody().getSubject();
			
		}catch(Exception e) {
			return "bad token";
		}
	}
	
	
}

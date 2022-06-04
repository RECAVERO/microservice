package com.spring.demo.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.spring.demo.dto.AuthUserDto;
import com.spring.demo.dto.TokenDto;
import com.spring.demo.entities.AuthUser;
import com.spring.demo.repositories.AuthUserRepository;
import com.spring.demo.security.JwtProvider;

@Service
public class AuthUserService {
	
	@Autowired
	AuthUserRepository authUserRepository;
	@Autowired
	PasswordEncoder passwordEncoder;
	@Autowired
	JwtProvider jwtProvider;
	
	public AuthUser save(AuthUserDto dto) {
		Optional<AuthUser> user=authUserRepository.findByUserName(dto.getUserName());
		if(user.isPresent()) {
			return null;
		}
		
		String password=passwordEncoder.encode(dto.getPassword());
		AuthUser authUser=new AuthUser();
		authUser.setUserName(dto.getUserName());
		authUser.setPassword(dto.getPassword());
		
		return authUserRepository.save(authUser);
	}
	
	public TokenDto login(AuthUserDto dto) {
		Optional<AuthUser> user=authUserRepository.findByUserName(dto.getUserName());
		if(!user.isPresent()) {
			return null;
		}
		
		if(passwordEncoder.matches(dto.getPassword(), user.get().getPassword())) {
			
			return new TokenDto(jwtProvider.createToken(user.get()));
		}
		return null;
	}
	
	public TokenDto validate(String token) {

		if(!jwtProvider.validate(token)) {
			return null;
		}
		
		String userName=jwtProvider.getUserNameFromToken(token);
		Optional<AuthUser> user=authUserRepository.findByUserName(userName);
		if(!user.isPresent()) {
			return null;
		}
		
		return new TokenDto(token);

	}
	
}

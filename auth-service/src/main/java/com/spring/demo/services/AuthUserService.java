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
		AuthUser user=authUserRepository.authUserByName(dto.getEmail());
		if(user != null) {
			return null;
		}
		
		String password=passwordEncoder.encode(dto.getPassword());
		AuthUser authUser=new AuthUser();
		authUser.setEmail(dto.getEmail());
		authUser.setPassword(dto.getPassword());
		authUser.setPerfil(dto.getPerfil());
		return authUserRepository.authUsersave(authUser.getEmail(),authUser.getPassword(),authUser.getPerfil());
	}
	
	public TokenDto login(AuthUserDto dto) {
		AuthUser user=authUserRepository.authUserByName(dto.getEmail());
		if(user == null) {
			return null;
		}
		
		//if(passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
		if(dto.getPassword().equals(user.getPassword())) {
			return new TokenDto(jwtProvider.createToken(user));
		}
		return null;
	}
	
	public TokenDto validate(String token) {

		if(!jwtProvider.validate(token)) {
			return null;
		}
		
		String userName=jwtProvider.getUserNameFromToken(token);
		AuthUser user=authUserRepository.authUserByName(userName);
		if(user==null) {
			return null;
		}
		
		return new TokenDto(token);

	}
	
}

package com.spring.demo.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.spring.demo.entities.AuthUser;

@Repository
public interface AuthUserRepository extends JpaRepository<AuthUser, Integer>{
    @Query(value = "{call sp_authuser_email(:username)}", nativeQuery = true)
    AuthUser authUserByName(@Param("username") String username);
    @Query(value = "{call sp_authuser_save(:username,:clave,:perfil)}", nativeQuery = true)
    AuthUser authUsersave(@Param("username") String username,@Param("clave") String clave,@Param("perfil") String perfil);
}

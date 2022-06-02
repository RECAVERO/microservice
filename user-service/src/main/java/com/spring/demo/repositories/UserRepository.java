package com.spring.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.spring.demo.entity.User;
@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
    @Query(value = "{call sp_user_lista()}", nativeQuery = true)
    List<User> getListaUser();
    @Query(value = "{call sp_user_save(:_name,:_email)}", nativeQuery = true)
    User saveUser(@Param("_name") String _name,@Param("_email") String _email);
    @Query(value = "{call sp_user_id(:_id)}", nativeQuery = true)
    User userById(@Param("_id") int _id);
    

}

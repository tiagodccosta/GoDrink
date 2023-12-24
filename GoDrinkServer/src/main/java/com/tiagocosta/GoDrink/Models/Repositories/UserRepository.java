package com.tiagocosta.GoDrink.Models.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.tiagocosta.GoDrink.Models.User;

import jakarta.transaction.Transactional;

public interface UserRepository extends CrudRepository<User, Integer>{

    @Query(value = "SELECT email FROM users WHERE email = :email", nativeQuery = true)
    List<String> checkUserEmail(@Param("email") String email);

    @Query(value = "SELECT password FROM users WHERE email = :email", nativeQuery = true)
    String checkUserPasswordByEmail(@Param("email") String email);

    @Query(value = "SELECT * FROM users WHERE email = :email", nativeQuery = true)
    User getUserDetaislByEmail(@Param("email") String email);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO USERS(user_name, email, password) VALUES(:user_name, :email, :password)", nativeQuery = true) 
    int registerNewUser(@Param("user_name") String user_name,
                        @Param("email") String email,
                        @Param("password") String password);

}

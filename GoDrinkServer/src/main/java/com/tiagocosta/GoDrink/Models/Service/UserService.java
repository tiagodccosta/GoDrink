package com.tiagocosta.GoDrink.Models.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tiagocosta.GoDrink.Models.User;
import com.tiagocosta.GoDrink.Models.Repositories.UserRepository;

@Service
public class UserService {


    @Autowired
    UserRepository userRepository;

    public int registerNewUserServiceMethod(String userName, String userEmail, String userPassword) {
        return userRepository.registerNewUser(userName, userEmail, userPassword);
    }

    public List<String> checkUserEmail(String email) {
        return userRepository.checkUserEmail(email);
    }

    public String checkUsePasswordByEmail(String email) {
        return userRepository.checkUserPasswordByEmail(email);
    }

    public User getUserDetaislByEmail(String email) {
        return userRepository.getUserDetaislByEmail(email);
    }
}

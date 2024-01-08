package com.tiagocosta.GoDrink.Controllers;


import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tiagocosta.GoDrink.Models.Login;
import com.tiagocosta.GoDrink.Models.User;
import com.tiagocosta.GoDrink.Models.Exceptions.NotFoundException;
import com.tiagocosta.GoDrink.Models.Repositories.UserRepository;
import com.tiagocosta.GoDrink.Models.Responses.Response;
import com.tiagocosta.GoDrink.Models.Service.UserService;

@RestController
@RequestMapping(path = "/api/users")
public class UsersController {
    
    private Logger logger = LoggerFactory.getLogger(UsersController.class);
    @Autowired
    private UserRepository userRepository;

    @GetMapping(path = "", produces= MediaType.APPLICATION_JSON_VALUE)
    public Iterable<User> getUsers() {
        logger.info("Sending all users");
        return userRepository.findAll();
    }    

    @GetMapping(path = "/{id:[0-9]+}",
    produces= MediaType.APPLICATION_JSON_VALUE)
    public User getUser(@PathVariable int id) throws NotFoundException {
        logger.info("Sending user with id " + id);
        Optional<User> _user = userRepository.findById(id);
        if (_user.isEmpty())
            throw new NotFoundException("" + id, "User", "id");
        else return _user.get();
    }

    @DeleteMapping(path = "/delete/{id:[0-9]+}", produces =
    MediaType.APPLICATION_JSON_VALUE)
    public Response deleteUser(@PathVariable int id) {
        logger.info("Deleting user with id " + id);
        // No verification to see if it exists
        userRepository.deleteById(id);
        return new Response("Deleted user with id " + id, null);
    }

    @Autowired
    private UserService userService;

    @SuppressWarnings("rawtypes")
    @PostMapping(path = "/register", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity registerUser(@RequestParam("user_name") String userName,
                                       @RequestParam("user_email") String userEmail,
                                       @RequestParam("password") String userPassword){

        String hashedPassword = BCrypt.hashpw(userPassword, BCrypt.gensalt());

        //Register the new user
        int registerResult = userService.registerNewUserServiceMethod(userName, userEmail, hashedPassword);

        if(registerResult != 1) {
            return new ResponseEntity<>("Failed to register the user", HttpStatus.BAD_REQUEST); 
        } else {
            return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
        }
    }

    @SuppressWarnings("rawtypes")
    @PostMapping(path = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity authenticateUser(@RequestBody Login login) {
        List<String> userEmail = userService.checkUserEmail(login.getEmail());
        
        if(userEmail.isEmpty() || userEmail == null) {
            return new ResponseEntity<>("Email does not exist", HttpStatus.NOT_FOUND);
        }

        String hashed_password = userService.checkUsePasswordByEmail(login.getEmail());

        if(!BCrypt.checkpw(login.getPassword(), hashed_password)) {
            return new ResponseEntity<>("Incorrect email or password", HttpStatus.BAD_REQUEST);
        }

        User user = userService.getUserDetaislByEmail(login.getEmail());
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

}

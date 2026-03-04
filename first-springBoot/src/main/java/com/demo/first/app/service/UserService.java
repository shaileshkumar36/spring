package com.demo.first.app.service;


import com.demo.first.app.excptions.UserNotFoundException;
import com.demo.first.app.model.User;
import com.demo.first.app.repository.UserRepository;
import org.jspecify.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private UserRepository userRepository;
    private final Logger logger = LoggerFactory.getLogger(UserService.class);

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user) {
        logger.info("Createing user .... INFO");
        logger.debug("Createing user .... DEBUG");
        logger.trace("Createing user .... TRACE");
        logger.warn("Createing user .... WARN");
        logger.error("Createing user .... ERROR");

        System.out.println(user.getEmail());
        return userRepository.save(user);
    }

    public User upDateUser(User user) {
         User existing = userRepository.findById(user.getId())
                .orElseThrow(
                        () -> new UserNotFoundException("User with ID " + user.getId() + " does not exist")
                );

        existing.setName(user.getName());
        existing.setEmail(user.getEmail());

        return userRepository.save(existing);
    }

    public boolean deleteUser(int id) {
        if(!userRepository.existsById(id)){
            throw new UserNotFoundException("User with ID "+ id + " does not exist");
        }
        userRepository.deleteById(id);
        return true;
    }

    public List<User> getAllUsers() {
        List<User> users = userRepository.findAll();
        if(users.isEmpty())
            throw new NullPointerException("No user found in the database");
        return users;
    }

    public User getUserById(int id) {
        return userRepository.findById(id)
                .orElseThrow(
                        () -> new UserNotFoundException("User with ID " + id + " does not exist")
                );
    }

    public @Nullable List<User> searchUsers(String name, String email) {
       // return userRepository.findByNameAndEmail(name, email);
        return userRepository.findByNameIgnoreCaseAndEmailIgnoreCase(name, email);
    }
}

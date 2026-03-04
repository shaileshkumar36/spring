package com.demo.first.app.controller;

import com.demo.first.app.model.User;
import com.demo.first.app.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private UserService userService ;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user){
        User createdUser =  userService.createUser(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    // q -> john , john@email.com
    // 1 -> Alice jonlk@gamil.com

    @PutMapping
    public ResponseEntity<User> updateUser(@RequestBody User user){
       User updated = userService.upDateUser(user);
       if(updated == null)
           return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.ok(updated);
    }

    //  /user/1 /user/2 /user/3
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable int id) {
        boolean isDeleted = userService.deleteUser(id);
        if(!isDeleted) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok("User Deleted");
    }

    // @GetMapping({"/users", "/user/{id}")

    @GetMapping
    public List<User> getUser(){
        return userService.getAllUsers();
    }


    @GetMapping("/{userId}")
    public ResponseEntity<User> getUser(
            @PathVariable(value = "userId", required = false) int id){
        User user = userService.getUserById(id);
        if(user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(user);
    }

    @GetMapping("/{userId}/order/{orderId}")
    public ResponseEntity<User> getUserOrder(
            @PathVariable("userId") int id,
            @PathVariable int orderId
    ){
        User user = userService.getUserById(id);
        if(user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(user);
    }

    @GetMapping("/search")
    public ResponseEntity<List<User>> searchUsers(
            @RequestParam(required = false, defaultValue = "payl") String name,
            @RequestParam(required = false, defaultValue = "alice") String email)
    {
        return ResponseEntity.ok(userService.searchUsers(name, email));
    }

    @GetMapping("/info/{id}")
    public String getInfo(
            @PathVariable int id,
            @RequestParam String name,
            @RequestHeader("User-Agent") String userAgent
            //@RequestHeader("email") String email;

    ) {
        return "User Agent: "+ userAgent + " : " + id+ " : "+name;
    }

}


//
//{
//        "timestamp": "2026-02-10T16:13:05.116Z",
//        "status": 500,
//        "error": "Internal Server Error",
//        "path": "/user"
//        }
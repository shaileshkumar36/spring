package com.demo.first.app;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    private Map<Integer , User> userDb = new HashMap<>();

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user){
        System.out.println(user.getEmail());
        userDb.putIfAbsent(user.getId(),user);
        //System.out.println(user.getEmail());

        //return ResponseEntity.status(HttpStatus.CREATED).body(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    // q -> john , john@email.com
    // 1 -> Alice jonlk@gamil.com

    @PutMapping
    public ResponseEntity<User> updateUser(@RequestBody User user){
        if(!userDb.containsKey(user.getId()))
           // return ResponseEntity.notFound().build();
           return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        System.out.println(user.getName());
        userDb.put(user.getId(), user);
        //return ResponseEntity.status(HttpStatus.OK).body(user);
        return ResponseEntity.ok(user);
    }

    //  /user/1 /user/2 /user/3
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable int id) {
        if(!userDb.containsKey(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        userDb.remove(id);
        return ResponseEntity.ok("User Deleted");
    }

    // @GetMapping({"/users", "/user/{id}")

    @GetMapping
    public List<User> getUser(){
        return new ArrayList<>(userDb.values());
    }


    @GetMapping("/{userId}")
    public ResponseEntity<User> getUser(
            @PathVariable(value = "userId", required = false) int id){
        if(!userDb.containsKey(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(userDb.get(id));
    }

    @GetMapping("/{userId}/order/{orderId}")
    public ResponseEntity<User> getUserOrder(
            @PathVariable("userId") int id,
            @PathVariable int orderId
    ){
        System.out.println("ORDER ID: "+orderId);
        if(!userDb.containsKey(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(userDb.get(id));
    }

    @GetMapping("/search")
    public ResponseEntity<List<User>> searchUsers(
            @RequestParam(required = false, defaultValue = "payl") String name) {
        System.out.println(name);
        List<User> users = userDb.values().stream().filter(u -> u.getName()
                        .equalsIgnoreCase(name)).toList();
        return ResponseEntity.ok(users);

    }

    @GetMapping("/info")
    public String getInfo(
            @RequestHeader("User-Agent") String userAgent
            //@RequestHeader("email") String email;

    ) {
        return "User Agent: "+ userAgent;
    }

}

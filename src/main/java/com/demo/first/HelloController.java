package com.demo.first;

import com.demo.first.app.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")   // mapping class lavel
public class HelloController {

    @GetMapping("/hello")  // mathed lavel notacan
    public String sayHello(){
        return "Hello world:";
    }

    //@GetMapping("/user")
    @RequestMapping(value = "/user",method = RequestMethod.GET)
    public User getUser(){
        User user = new User(1,"shailesh kumar","shail@gmail.com");

        return user; // here jacan are converting java object to JSON;
    }
}

package com.example.demo.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.user.entity.UserEntity;
import com.example.demo.user.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public UserEntity save(@RequestBody UserEntity UserEntity) {
    	System.out.println("UserEntity:::" + UserEntity);
        return userService.saveUser(UserEntity);
    }

//    @GetMapping("/{id}")
//    public User get(@PathVariable("id") Long id) {
//        return userService.getUser(id);
//    }
//
//    @DeleteMapping("/{id}")
//    public String delete(@PathVariable Long id) {
//        userService.deleteUser(id);
//        return "Deleted";
//    }
}

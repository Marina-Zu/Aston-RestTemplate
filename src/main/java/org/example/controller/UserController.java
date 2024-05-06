package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.service.UserService;
import org.example.dto.UserIncomingDto;
import org.example.dto.UserOutGoingDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

   @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/all")
    public List<UserOutGoingDto> getAllUsers() {
        return userService.findAll();
    }

    @GetMapping("/{userId}")
    public UserOutGoingDto getUserById(@PathVariable Long userId) {
        return userService.findById(userId);
    }

    @PostMapping
    public UserOutGoingDto createUser(@RequestBody UserIncomingDto userDto) {
        return userService.save(userDto);
    }

    @PutMapping("/{userId}")
    public String updateUser(@PathVariable Long userId, @RequestBody UserIncomingDto userDto) {
        userService.update(userDto);
        return "User with id " + userId + " update";
    }

    @DeleteMapping("/{userId}")
    public String deleteUser(@PathVariable Long userId) {
        userService.deleteById(userId);
        return "User with id " + userId + " deleted";
    }

}

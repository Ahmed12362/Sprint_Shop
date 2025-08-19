package com.example.Sprint.controller;

import com.example.Sprint.dto.ProductDto;
import com.example.Sprint.dto.UserDto;
import com.example.Sprint.response.BaseResponse;
import com.example.Sprint.service.user.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class UserController {
    private final IUserService iUserService;

    @PostMapping("/register")
    public ResponseEntity<BaseResponse> addUser(@RequestBody UserDto userDto){
        UserDto result = iUserService.registerAsUser(userDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new BaseResponse("Registered" , result));
    }
    @PostMapping("/login")
    public ResponseEntity<BaseResponse> login(@RequestBody UserDto userDto){
        String token = iUserService.login(userDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new BaseResponse("Login" , token));
    }
}

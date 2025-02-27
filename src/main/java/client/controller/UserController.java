package client.controller;

import client.service.UserService;
import client.service.UserServiceImpl;
import model.dto.RegisterDto;
import model.dto.ResponseUserDto;

public class UserController {
    private final UserService userService = new UserServiceImpl();
    public ResponseUserDto register(RegisterDto registerDto){
        return userService.registerUser(registerDto);
    }
}

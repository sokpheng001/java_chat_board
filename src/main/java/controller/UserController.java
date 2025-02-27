package controller;

import model.dto.RegisterDto;
import model.dto.ResponseUserDto;
import service.UserService;
import service.UserServiceImpl;

public class UserController {
    private final UserService userService = new UserServiceImpl();
    public ResponseUserDto register(RegisterDto registerDto){
        return userService.registerUser(registerDto);
    }
}

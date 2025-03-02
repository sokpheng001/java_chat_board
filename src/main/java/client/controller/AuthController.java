package client.controller;

import client.service.abstraction.UserService;
import client.service.UserServiceImpl;
import model.dto.ResponseUserDto;

public class AuthController {
    private final UserService userService = new UserServiceImpl();
    public ResponseUserDto login(String username, String password) {
        return userService.loginUser(username, password);
    }
}

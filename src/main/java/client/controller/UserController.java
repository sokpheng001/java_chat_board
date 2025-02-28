package client.controller;

import client.service.UserService;
import client.service.UserServiceImpl;
import model.dto.UserRegisterDto;
import model.dto.ResponseUserDto;

import java.util.List;

public class UserController {
    private final UserService userService = new UserServiceImpl();
    public ResponseUserDto register(UserRegisterDto registerDto){

        return userService.registerUser(registerDto);
    }
    public List<ResponseUserDto> getAllUsers(){
        return userService.findAllUsers();
    }
    public ResponseUserDto getUserByName(String username){
        return userService.findUserByName(username);
    }
}

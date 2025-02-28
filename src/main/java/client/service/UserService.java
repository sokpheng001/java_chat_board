package client.service;

import model.dto.UserRegisterDto;
import model.dto.ResponseUserDto;

import java.util.List;

public interface UserService {
    ResponseUserDto registerUser(UserRegisterDto registerDto);
    ResponseUserDto loginUser(String uniqueName, String password);
    ResponseUserDto findUserByName(String username);
    List<ResponseUserDto> findAllUsers();
}

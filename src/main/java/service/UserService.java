package service;

import model.User;
import model.dto.RegisterDto;
import model.dto.ResponseUserDto;

public interface UserService {
    ResponseUserDto registerUser(RegisterDto registerDto);
    ResponseUserDto loginUser(String uniqueName);
    ResponseUserDto findUserByName(String username);
}

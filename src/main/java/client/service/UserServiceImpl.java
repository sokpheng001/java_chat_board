package client.service;

import bean.UserBean;
import client.repository.UserRepository;
import client.service.abstraction.UserService;
import exception.UserException;
import model.User;
import model.dto.UserRegisterDto;
import model.dto.ResponseUserDto;
import utils.CustomizeHashing;
import utils.validation.CustomizeValidator;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UserServiceImpl implements UserService {
    private final UserRepository userRepository = new UserRepository();
    @Override
    public ResponseUserDto registerUser(UserRegisterDto registerDto) {
        try{
            if(CustomizeValidator.isValidEmail(registerDto.email())){
                User user = UserBean.userManualMapper.fromRegisterUserDtoToUser(registerDto);
                // hash user password
                user.setPassword(CustomizeHashing.hashing(user.getPassword()));
                //
                int result = userRepository.save(user);// save user to database
                if(result!=0){
                    return UserBean.userManualMapper.fromUserToResponseUserDto(user);
                }
            }
            throw new UserException("Email is not valid");
        }catch (RuntimeException exception){
            System.out.println("[!] Problem: " + exception.getMessage());
        }
        return null;
    }

    @Override
    public ResponseUserDto loginUser(String uniqueName, String password) {
        User user  = userRepository.findByUsername(uniqueName);
        user.setLoginDate(Date.valueOf(LocalDate.now()));
        if(CustomizeHashing.hashing(password).equals(user.getPassword())){
            userRepository.update(user);
            return UserBean.userManualMapper.fromUserToResponseUserDto(user);
        }
        return null;
    }

    @Override
    public ResponseUserDto findUserByName(String username) {
        return UserBean.userManualMapper.fromUserToResponseUserDto(userRepository.findByUsername(username));
    }

    @Override
    public List<ResponseUserDto> findAllUsers() {
        List<ResponseUserDto> responseUserDtos = new ArrayList<>();
        userRepository.findAll().forEach(user -> {
            responseUserDtos.add(UserBean.userManualMapper.fromUserToResponseUserDto(user));
        });
        return responseUserDtos;
    }

    @Override
    public ResponseUserDto getUserByUuid(String uuid) {
        User user = userRepository.findUserByUuid(uuid);
        return UserBean.userManualMapper.fromUserToResponseUserDto(user);
    }
}

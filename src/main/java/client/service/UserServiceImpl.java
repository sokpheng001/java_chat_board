package client.service;

import bean.UserBean;
import model.User;
import model.dto.RegisterDto;
import model.dto.ResponseUserDto;

public class UserServiceImpl implements UserService{
    @Override
    public ResponseUserDto registerUser(RegisterDto registerDto) {
        User user = UserBean.userManualMapper.fromRegisterUserDtoToUser(registerDto);
        int result = UserBean.userRepository.save(user);// save user to database
        if(result!=0){
            return UserBean.userManualMapper.fromUserToResponseUserDto(user);
        }
        return null;
    }

    @Override
    public ResponseUserDto loginUser(String uniqueName) {
        return null;
    }

    @Override
    public ResponseUserDto findUserByName(String username) {
        return null;
    }
}

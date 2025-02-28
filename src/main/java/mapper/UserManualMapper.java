package mapper;

import model.User;
import model.dto.UserRegisterDto;
import model.dto.ResponseUserDto;

import java.util.UUID;

/***
 * <p>This called is used for mapping any form of object data from one class to another class data form</p>
 * @author Kim Chansokpheng
 * @version 1.0
 */
public class UserManualMapper {
    public  User fromRegisterUserDtoToUser(UserRegisterDto registerUserDto) {
        Long randomId = (long) (Math.random() * 10000);
        return new User(randomId,
                UUID.randomUUID().toString(),
                registerUserDto.userName(),
                registerUserDto.email(),
                registerUserDto.password(),
                registerUserDto.loginDate(), false,true);
    }
    public ResponseUserDto fromUserToResponseUserDto(User user) {
        return ResponseUserDto.builder()
                .uuid(user.getUuid())
                .name(user.getUserName())
                .email(user.getEmail())
                .isVerified(user.getIsVerified())
                .isDeleted(user.getIsDeleted())
                .loginDate(user.getLoginDate())
                .build();
    }
//    public User fromResponseUserDtoToUser(ResponseUserDto responseUserDto) {
//        return User.builder()
//                .build();
//    }
}

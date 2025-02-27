package mapper;

import model.User;
import model.dto.RegisterDto;
import model.dto.ResponseUserDto;

import java.util.UUID;

public class UserManualMapper {
    public  User fromRegisterUserDtoToUser(RegisterDto registerUserDto) {
        Long randomId = (long) (Math.random() * 10000);
        return new User(randomId,
                UUID.randomUUID().toString(),
                registerUserDto.userName(),
                registerUserDto.email(),
                registerUserDto.password(),
                null, false,true);
    }
    public ResponseUserDto fromUserToResponseUserDto(User user) {
        return ResponseUserDto.builder()
                .uuid(user.getUuid())
                .name(user.getUserName())
                .email(user.getEmail())
                .isVerified(user.getIsVerified())
                .isDeleted(user.getIsDeleted())
                .build();
    }
}

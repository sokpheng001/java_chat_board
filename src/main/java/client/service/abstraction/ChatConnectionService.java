package client.service.abstraction;


import model.dto.CreateChatConnectionUsingUserNameDto;
import model.dto.ResponseChatConnectionDto;
import model.dto.ResponseUserDto;

import java.util.List;

public interface ChatConnectionService {
    ResponseChatConnectionDto getConnectionByUserUuid(String userUuid);
    int createNewConnection(CreateChatConnectionUsingUserNameDto createChatConnectionUsingUserNameDto);
    List<ResponseUserDto> getAllConnectedUserByUserLoginUuid(String loginUserId);
}

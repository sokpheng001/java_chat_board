package client.service.abstraction;

import model.dto.CreateChatConnectionUsingUserIDDto;
import model.dto.CreateChatConnectionUsingUserNameDto;
import model.dto.ResponseChatConnectionDto;

public interface ChatConnectionService {
    ResponseChatConnectionDto getConnectionByUserUuid(String userUuid);
    int createNewConnection(CreateChatConnectionUsingUserNameDto createChatConnectionUsingUserNameDto);
}

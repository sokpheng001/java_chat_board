package mapper;

import model.ChatConnection;
import model.dto.CreateChatConnectionUsingUserIDDto;

import java.util.UUID;


public class ChatConnectionManualMapper {
    /**
     * <p>Map to CreateChatConnection class object and auto generated uuid</p>
     */
    public static ChatConnection fromCreateChatConnectionDtoToChatConnection(CreateChatConnectionUsingUserIDDto createChatConnectionUsingUserIDDto){
        return ChatConnection.builder()
                .uuid(UUID.randomUUID().toString())
                .loginUserId(createChatConnectionUsingUserIDDto.loginUserId())
                .wantedToConnectUserId(createChatConnectionUsingUserIDDto.loginUserId())
                .build();
    }
}

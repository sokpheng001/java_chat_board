package client.controller;

import client.service.ChatConnectionServiceImpl;
import client.service.abstraction.ChatConnectionService;
import model.dto.CreateChatConnectionUsingUserIDDto;
import model.dto.CreateChatConnectionUsingUserNameDto;

public class ChatConnectionController {
    private final ChatConnectionService chatConnectionService = new ChatConnectionServiceImpl();
    public int createConnection(CreateChatConnectionUsingUserNameDto createChatConnectionUsingUserNameDto){
        return chatConnectionService.createNewConnection(createChatConnectionUsingUserNameDto);
    }
}

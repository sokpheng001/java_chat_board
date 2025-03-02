package client.service;

import client.repository.ChatConnectionRepository;
import client.service.abstraction.ChatConnectionService;
import mapper.ChatConnectionManualMapper;
import model.ChatConnection;
import model.dto.CreateChatConnectionUsingUserIDDto;
import model.dto.CreateChatConnectionUsingUserNameDto;
import model.dto.ResponseChatConnectionDto;

public class ChatConnectionServiceImpl implements ChatConnectionService {
    private final ChatConnectionRepository chatConnectionRepository = new ChatConnectionRepository();
    @Override
    public ResponseChatConnectionDto getConnectionByUserUuid(String userUuid) {
        return null;
    }

    @Override
    public int createNewConnection(CreateChatConnectionUsingUserNameDto createChatConnectionUsingUserNameDto) {
        //
        ChatConnection connection = ChatConnectionManualMapper
                .fromCreateChatConnectionDtoToChatConnection(null);
       if(chatConnectionRepository.save(connection)>0){
           return 1;
       }
       return 0;
    }
}

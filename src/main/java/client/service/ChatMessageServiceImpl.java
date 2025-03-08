package client.service;

import bean.ChatConnectionBean;
import bean.ChatMessageBean;
import client.service.abstraction.ChatConnectionService;
import client.service.abstraction.ChatMessageService;
import model.ChatMessage;
import model.dto.CreateChatConnectionUsingUserNameDto;
import model.dto.CreateChatMessageDto;
import model.dto.ResponseChatConnectionDto;
import model.dto.ResponseUserDto;

import java.sql.Date;
import java.util.List;

public class ChatMessageServiceImpl implements ChatMessageService {


    @Override
    public int createNewChatMessage(CreateChatMessageDto createChatMessageDto) {
        return ChatMessageBean.chatMessageRepository.save(ChatMessageBean.chatMessageMapper.mapFromCreateChatMessageToChatMessage(createChatMessageDto));
    }

    @Override
    public CreateChatMessageDto getChatMessageBySentDate(Date sentDate) {
        return null;
    }

    @Override
    public List<CreateChatMessageDto> getAllChatMessagesBySendIdAndReceiverId(String sendId, String receiverId) {
        return List.of();
    }
}

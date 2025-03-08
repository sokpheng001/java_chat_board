package client.service.abstraction;

import model.dto.CreateChatMessageDto;

import java.sql.Date;
import java.util.List;

public interface ChatMessageService {
    int createNewChatMessage(CreateChatMessageDto createChatMessageDto  );
    CreateChatMessageDto getChatMessageBySentDate(Date sentDate);
    List<CreateChatMessageDto>  getAllChatMessagesBySendIdAndReceiverId(String sendId,String receiverId);
}

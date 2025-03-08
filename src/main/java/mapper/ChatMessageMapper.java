package mapper;

import model.ChatMessage;
import model.dto.CreateChatMessageDto;


import java.sql.Date;
import java.time.Instant;
import java.util.Random;
import java.util.UUID;

public class ChatMessageMapper {
    public ChatMessage mapFromCreateChatMessageToChatMessage(CreateChatMessageDto createChatMessageDto) {
        return ChatMessage.builder()
                .id(new Random().nextInt(99999))
                .uuid(UUID.randomUUID().toString())
                .senderId(createChatMessageDto.senderId())
                .receiverId(createChatMessageDto.receiverId())
                .message(createChatMessageDto.message())
                .sentAt((Date) Date.from(Instant.now()))
                .build();
    }
}

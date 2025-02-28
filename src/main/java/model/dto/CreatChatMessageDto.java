package model.dto;

import lombok.Builder;

@Builder
public record CreatChatMessageDto(
        String uuid,
        Integer senderId,
        Integer receiverId,
        String message
) {
}

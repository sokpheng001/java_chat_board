package model.dto;

import lombok.Builder;

@Builder
public record CreateChatMessageDto(
        String uuid,
        Integer senderId,
        Integer receiverId,
        String message
) {
}

package model.dto;

import lombok.Builder;

@Builder
public record CreateChatConnectionDto(
        String uuid,
        int user1Id,
        int user2Id
) {
}

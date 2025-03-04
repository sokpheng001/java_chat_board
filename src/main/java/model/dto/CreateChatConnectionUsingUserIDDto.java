package model.dto;

import lombok.Builder;

@Builder
public record CreateChatConnectionUsingUserIDDto(
        Long loginUserId,
        Long userWantedToConnectId
) {
}

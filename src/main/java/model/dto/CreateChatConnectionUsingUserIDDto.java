package model.dto;

import lombok.Builder;

@Builder
public record CreateChatConnectionUsingUserIDDto(
        int loginUserId,
        int userWantedToConnectId
) {
}

package model.dto;

import lombok.Builder;

@Builder
public record CreateChatConnectionUsingUserNameDto(
        String loginUserName,
        String wantedToConnectedUserName
) {
}

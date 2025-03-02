package model.dto;

import lombok.Builder;

@Builder
public record ResponseChatConnectionDto (
        String uuid,
        String loginUserName,
        String userWantedToConnectName
){ }

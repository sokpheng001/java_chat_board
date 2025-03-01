package model.dto;

import lombok.Builder;

@Builder
public record ServerRespondDto(
        Integer id,
        String ipAddress,
        Integer port
) {
}

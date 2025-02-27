package model.dto;

import lombok.Builder;

@Builder
public record ResponseUserDto(
        String uuid,
        String name,
        String email,
        Boolean isDeleted,
        Boolean isVerified
) {
}

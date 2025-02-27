package model.dto;

import lombok.Builder;

@Builder
public record RegisterDto(
        String userName,
        String email,
        String password
) {
}

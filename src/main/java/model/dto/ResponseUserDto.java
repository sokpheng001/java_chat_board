package model.dto;

import lombok.Builder;

import java.sql.Date;

@Builder
public record ResponseUserDto(
        String uuid,
        String name,
        String email,
        Boolean isDeleted,
        Boolean isVerified,
        Date loginDate
) {
}

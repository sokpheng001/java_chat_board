package model.dto;

import lombok.Builder;
import java.sql.Date;

@Builder
public record UserRegisterDto(
        String userName,
        String email,
        String password,
        Date loginDate
) {
}

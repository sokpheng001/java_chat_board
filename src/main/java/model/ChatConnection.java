package model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatConnection {
    private Long id;
    private String uuid;
    private Long loginUserId;
    private Long wantedToConnectUserId;
    private Date lastMessageAt;
}

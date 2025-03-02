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
    private int id;
    private String uuid;
    private int loginUserId;
    private int wantedToConnectUserId;
    private Date lastMessageAt;
}

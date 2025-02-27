package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatConnection {
    private int id;
    private int user1Id;
    private int user2Id;
    private Date lastMessageAt;
}

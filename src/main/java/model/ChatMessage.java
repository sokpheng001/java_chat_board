package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessage {
    private Integer id;
    private Integer senderId;
    private Integer receiverId;
    private String message;
    private Date sentAt;
}

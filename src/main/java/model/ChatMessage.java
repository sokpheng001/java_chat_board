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
public class ChatMessage {
    private Integer id;
    private String uuid;
    private Integer senderId;
    private Integer receiverId;
    private String message;
    private Date sentAt;
}

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
public class User {
    private Long id;
    private String uuid;
    private String userName;
    private String email;
    private String password;
    private Date loginDate;
    private Boolean isDeleted;
    private Boolean isVerified;
    //

}

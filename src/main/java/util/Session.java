package util;

import lombok.Data;

@Data
public class Session {
    private String userId;
    private String userName;

    public Session(String userId, String username) {
        this.userId = userId;
        this.userName = username;
    }
}

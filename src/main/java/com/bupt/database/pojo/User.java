package com.bupt.database.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author wanz
 */
@Accessors
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {

    private long id;
    private String username;
    private String password;
    private String role;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

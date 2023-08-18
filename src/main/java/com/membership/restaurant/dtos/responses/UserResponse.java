package com.membership.restaurant.dtos.responses;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.membership.restaurant.entities.User;
import lombok.Data;

@Data
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class UserResponse {
    @JsonProperty("id")
    private int id;

    @JsonProperty("account")
    private String account;

    @JsonProperty("tel")
    private String tel;

    @JsonProperty("type")
    private int type;

    @JsonProperty("email")
    private String email;

    @JsonProperty("birthday")
    private String birthday;

    public UserResponse(User user) {
        this.id = user.getId();
        this.account = user.getAccount();
        this.tel = user.getTel();
        this.type = user.getRole().getValue();
        this.email = user.getEmail();
        this.birthday = user.getBirthday();
    }

    public UserResponse() {

    }
}

package ru.tarabne.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class GetUserResponseModel {
    UserData data;
    Support support;

    @Data
    public static class UserData {
        int id;
        String email, avatar;
        @JsonProperty("first_name")
        String firstName;
        @JsonProperty("last_name")
        String lastName;
    }

    @Data
    public static class Support {
        String url, text;
    }

}

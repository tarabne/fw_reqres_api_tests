package ru.tarabne.models.lombok;

import lombok.Data;

@Data
public class GetUserResponseModel {
    UserData data;
    Support support;

    @Data
    public static class UserData {
        int id;
        String email, first_name, last_name, avatar;
    }

    @Data
    public static class Support {
        String url, text;
    }

}

package ru.tarabne.models.lombok;

import lombok.Data;

@Data
public class RegistrationResponseModel {
    String email, password, id, createdAt;
}

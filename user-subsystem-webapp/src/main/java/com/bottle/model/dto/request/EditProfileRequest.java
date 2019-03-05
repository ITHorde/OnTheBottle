package com.bottle.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EditProfileRequest {
    private UUID id;
    private String name;
    private String surname;
    private int age;
    private String email;
    private String country;
    private String city;
    private String avatarUrl;
    private String status;
    private String info;
    private String password;
}

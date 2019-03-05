package com.bottle.model.dto.response;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class InfoPageResponse {
    private String name;
    private String surname;
    private int age;
    private String email;
    private String country;
    private String city;
    private String avatarUrl;
    private String status;
    private String info;
    private boolean deleted;
    private String password;
}

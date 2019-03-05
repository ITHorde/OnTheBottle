package com.bottle.model.dto;

import com.bottle.entity.User;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private UUID id;
    private String name;
    private String surname;
    private int age;
    private String avatarUrl;
    private String city;
    private String country;
    private List<UUID> friendsId;
    private boolean deleted;
    private String friendStatus;
}
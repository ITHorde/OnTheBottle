package com.bottle.controller;

import com.bottle.entity.User;
import com.bottle.model.dto.UserDTO;
import com.bottle.repository.UserRepository;
import com.bottle.model.dto.request.InfoPageRequest;
import com.bottle.model.dto.response.InfoPageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class InfoPageController {
    private UserRepository userRepository;

    @Autowired
    public InfoPageController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/showUsers")
    @ResponseBody
    public InfoPageResponse getUserInfo(InfoPageRequest request) {

        User user = userRepository.findOne(request.getId());
//        User user = userRepository.findOne(UUID.fromString("14dd28b2-1e7e-4575-9923-135c4fbf345b"));
        InfoPageResponse infoPageResponse = InfoPageResponse.builder()
                .name(user.getName())
                .surname(user.getSurname())
                .age(user.getAge())
                .email(user.getEmail())
                .country((user.getCountry()))
                .city(user.getCity())
                .avatarUrl(user.getAvatarUrl())
                .status(user.getStatus())
                .info(user.getInfo())
                .deleted(user.getDeleted())
                .password(user.getPassword()).build();

        System.out.println("TestController: A name of the user is " + infoPageResponse.getName());

        return infoPageResponse;
    }
}

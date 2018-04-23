package com.bottle.controller;

import com.bottle.entity.User;
import com.bottle.logic.FindPerson;
import com.bottle.model.dto.UserDTO;
import com.bottle.repository.UserRepository;
import com.bottle.model.dto.request.FindPersonRequest;
import com.bottle.model.dto.responce.FindPersonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;


@Controller
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class FindPersonController {

    private final UserRepository userRepository;

    @Autowired
    public FindPersonController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/person_search")
    @ResponseBody
    public FindPersonResponse getListOfPersons(FindPersonRequest request) {
        System.out.println("request contents: " + request.getSearch() + " " + request.getSearchType());
        // TODO: 24.04.2018 why? need normal service
        return new FindPerson().findFromDB(userRepository, request);
    }

}

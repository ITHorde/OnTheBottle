package com.bottle.controller;

import com.bottle.model.dto.request.DeleteProfileRequest;
import com.bottle.model.dto.request.EditProfileRequest;
import com.bottle.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class EditProfileController {
    private UserRepository userRepository;

    @Autowired
    public EditProfileController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/editProfile")
    @ResponseBody
    public void editProfile(EditProfileRequest request) {

        int result = userRepository.setUserById(request.getId(), request.getName(), request.getSurname(), request.getAge(),
                request.getEmail(), request.getCountry(), request.getCity(), request.getStatus(), request.getInfo(), request.getPassword());

        System.out.println("TestController: Rows affected: " + result);
    }

    @PostMapping("/deleteResumeProfile")
    @ResponseBody
    public void deleteProfile(DeleteProfileRequest deleteProfileRequest){
        System.out.println("delete request received " + deleteProfileRequest.getId()+ deleteProfileRequest.isDeleted());
        userRepository.setDeleted(deleteProfileRequest.getId(), deleteProfileRequest.isDeleted());
    }
}

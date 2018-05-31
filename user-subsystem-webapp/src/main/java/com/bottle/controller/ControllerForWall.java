package com.bottle.controller;

import com.bottle.entity.User;
import com.bottle.model.dto.request.UserIdDTO;
import com.bottle.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "*")
@RestController
public class ControllerForWall {
    private UserService userService;

    @Autowired
    public ControllerForWall(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(path = "/getUser", params = "userId", method = RequestMethod.GET)
    public ResponseEntity<User> getUser(@RequestParam("userId") UUID id) {
        User user = userService.getUser( id );
        return new ResponseEntity<>( user, HttpStatus.OK );
    }

    @RequestMapping(path = "/getUsers", method = RequestMethod.POST)
    public ResponseEntity<List<User>> getUsers(@RequestBody List<UserIdDTO> usersId) {
        List<User> users = userService.getUsers( usersId );
        return new ResponseEntity<>( users, HttpStatus.OK );
    }
}

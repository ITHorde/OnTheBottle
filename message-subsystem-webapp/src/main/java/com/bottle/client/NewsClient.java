package com.bottle.client;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
public class NewsClient {
    private String userSystemPath = "http://127.0.0.1:8083/user/";

    public List getFriends(UUID id) {
        String url = userSystemPath + "getfriends?id=" + id;
        //POST request
        return new RestTemplate().postForObject(url, null, ArrayList.class);
    }

    public void getFriendsTest(UUID id) {
        String remoteHost = "http://127.0.0.1:8083/news/printrequest?id=" + id;
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(remoteHost, String.class);
        System.out.println(response);
    }
}
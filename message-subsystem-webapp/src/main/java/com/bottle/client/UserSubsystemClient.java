package com.bottle.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.*;

@Controller
public class UserSubsystemClient {
    @Value("${sub.user.path}")
    private String subUserPath;

    public List getFriends(UUID id, String token) {
        String url = subUserPath + "/friend/get_confirmed_friends";
        List array = new ArrayList();
        try {
            String json = Request.Post(url)
                    .bodyForm(Form.form()
                            .add("userId", id.toString())
                            .add("access_token", token).build())
                    .execute().returnContent().toString();
            ObjectMapper mapper = new ObjectMapper();
            array = mapper.readValue(json, ArrayList.class);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return array;
    }

    public boolean isFriend(UUID id, String token) {
        String url = subUserPath + "/friend/is_confirmed_friend";
        boolean isFriend = false;
        try {
            String response = Request.Post(url)
                    .bodyForm(Form.form()
                            .add("userId", id.toString())
                            .add("access_token", token)
                            .build())
                    .execute().returnContent().toString();
            ObjectMapper mapper = new ObjectMapper();
            isFriend = mapper.readValue(response, boolean.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return isFriend;
    }

    public Map getUser(UUID id, String token) {
        String url = subUserPath + "/user/get_by_id";
        Map map = new HashMap();
        try {
            String json = Request.Post(url)
                    .bodyForm(Form.form()
                            .add("userId", id.toString())
                            .build())
                    .execute().returnContent().toString();
            ObjectMapper mapper = new ObjectMapper();
            map = mapper.readValue(json, HashMap.class);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

    public List getFriendsOld(UUID id, String token) {
        String userSystemPath = subUserPath + "/friend/get_confirmed_friends";
        String url = userSystemPath + "?userId=" + id + "&access_token=" + token;
        System.out.println("client sends reguest: " + url);
        //POST request
        return new RestTemplate().postForObject(url, null, ArrayList.class);
    }
}

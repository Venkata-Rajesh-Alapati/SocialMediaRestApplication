package com.example.restfulwebservice.controllers;

import com.example.restfulwebservice.beans.Users;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.util.Arrays;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UsersControllerIntegrationTest {

    @Autowired
    TestRestTemplate restTemplate;
    @Test
    void getUsers() throws Exception {
        ResponseEntity<String> response = restTemplate.getForEntity("/users", String.class);
        Assertions.assertEquals(HttpStatus.OK,response.getStatusCode());
    }

    @Test
    void addUsers() throws JSONException {
        JSONObject obj = new JSONObject();
        obj.put("userName","Test");
        obj.put("age", "1999-07-17");

        HttpHeaders header = new HttpHeaders();
        header.setAccept((Arrays.asList(MediaType.APPLICATION_JSON)));
        header.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request = new HttpEntity<>(obj.toString(), header);

        ResponseEntity<Users> response = restTemplate.postForEntity("/users",request, Users.class);
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }
}

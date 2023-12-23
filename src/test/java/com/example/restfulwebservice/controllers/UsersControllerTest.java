package com.example.restfulwebservice.controllers;

import com.example.restfulwebservice.beans.Posts;
import com.example.restfulwebservice.beans.Users;
import com.example.restfulwebservice.beans.dao.PostsDao;
import com.example.restfulwebservice.beans.dao.UsersDao;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@WebMvcTest(controllers = UsersController.class)
class UsersControllerTest {

    @Autowired
    MockMvc mock;

    @MockBean
    UsersDao userdao;

    @MockBean
    PostsDao postsDao;

    @Test
    void welcome() throws Exception {

        String expected = "Documentation available at /swagger-ui.html \n Database at /h2-console";
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/");

        MvcResult result = mock.perform(requestBuilder).andReturn();

        String actual = result.getResponse().getContentAsString();
        Assertions.assertEquals(actual, expected);
    }

    @Test
    void getUsers() throws Exception {

        Users user1 = new Users(1, "Venkata", LocalDate.now());
        Users user2 = new Users(2, "Rajesh", LocalDate.now());
        List<Users> expected = new ArrayList<>();
        expected.add(user1);
        expected.add(user2);
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
        String expectedJson = objectMapper.writeValueAsString(expected);

        RequestBuilder request = MockMvcRequestBuilders.get("/users");
        Mockito.when(userdao.findAll()).thenReturn(expected);

        MvcResult result = mock.perform(request).andReturn();
        String actual = result.getResponse().getContentAsString();

        Assertions.assertEquals(expectedJson,actual);
    }

    @Test
    void getUser() throws Exception {
        Users user = new Users(1, "Venkata", LocalDate.now());

        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
        String expectedJson = objectMapper.writeValueAsString(user);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/users/1");
        Mockito.when(userdao.findById(1)).thenReturn(Optional.of(user));

        MvcResult result = mock.perform(requestBuilder).andReturn();
        String actual = result.getResponse().getContentAsString();

        Assertions.assertEquals(expectedJson,actual);

    }

    @Test
    void getUserPosts() throws Exception {

        RequestBuilder request = MockMvcRequestBuilders.get("/users/1/posts");
        Users user = new Users(1, "Venkata", LocalDate.now());

        List<Posts> posts = new ArrayList<>();
        posts.add(new Posts(1, "post1", user));
        posts.add(new Posts(2, "post2", user));
        user.setPosts(posts);

        String expectedJson = new ObjectMapper().writeValueAsString(posts);


        Mockito.when(userdao.findById(1)).thenReturn(Optional.of(user));

        MvcResult result = mock.perform(request).andReturn();

        String actual = result.getResponse().getContentAsString();
        Assertions.assertEquals(expectedJson,actual);
    }

    @Test
    void addUser() throws Exception {

        int expected = 201;
        Users user = new Users(1, "Venkata", LocalDate.now());

        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
        String inputJson = objectMapper.writeValueAsString(user);

        Mockito.when(userdao.save(Mockito.any(Users.class))).thenReturn(user);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(inputJson);

        MvcResult result = mock.perform(requestBuilder).andReturn();

        int actual = result.getResponse().getStatus();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void addPost() throws Exception {
        int expected = 200;
        Users user = new Users(1, "Venkata", LocalDate.now());

        Posts post = new Posts();
        post.setId(1);
        post.setDescription("post1");
        String postjson = new ObjectMapper().writeValueAsString(post);

        Mockito.when(userdao.findById(1)).thenReturn(Optional.of(user));
        Mockito.when(postsDao.save(Mockito.any(Posts.class))).thenAnswer(invocation -> invocation.getArgument(0));

        RequestBuilder request = MockMvcRequestBuilders.post("/users/1/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(postjson);

        MvcResult result = mock.perform(request).andReturn();
        int actual = result.getResponse().getStatus();
        Assertions.assertEquals(expected, actual);
    }

}
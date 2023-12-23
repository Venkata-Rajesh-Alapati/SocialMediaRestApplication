package com.example.restfulwebservice.beans.dao;

import com.example.restfulwebservice.beans.Posts;
import com.example.restfulwebservice.beans.Users;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

@DataJpaTest
class PostsDaoTest {

    @Autowired
    TestEntityManager testEntityManager;

    @Autowired
    PostsDao postsDao;

    @Autowired
    UsersDao usersDao;
    @Test
    void deletePostTest(){
        Users user = new Users(1,"Test", LocalDate.now());
        usersDao.save(user);
        Posts post = new Posts(1,"Test Post",user);
        postsDao.save(post);

        postsDao.deletePost(1, 1);

        Assertions.assertEquals(postsDao.findAllById(Arrays.asList(1)), new ArrayList<>());

    }

}
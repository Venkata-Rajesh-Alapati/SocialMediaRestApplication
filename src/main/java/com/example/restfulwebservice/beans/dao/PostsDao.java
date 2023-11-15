package com.example.restfulwebservice.beans.dao;

import com.example.restfulwebservice.beans.Posts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostsDao extends JpaRepository<Posts, Integer> {
}

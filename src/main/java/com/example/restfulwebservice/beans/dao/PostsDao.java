package com.example.restfulwebservice.beans.dao;

import com.example.restfulwebservice.beans.Posts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface PostsDao extends JpaRepository<Posts, Integer> {

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM POSTS WHERE ID = :postid AND USER_ID = :userid", nativeQuery = true)
    void deletePost(@Param("userid") int userid, @Param("postid") int postid);
}

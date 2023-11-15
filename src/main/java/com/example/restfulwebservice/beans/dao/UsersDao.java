package com.example.restfulwebservice.beans.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.restfulwebservice.beans.Users;

public interface UsersDao extends JpaRepository<Users, Integer>{

}

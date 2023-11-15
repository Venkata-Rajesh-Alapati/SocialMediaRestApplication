package com.example.restfulwebservice.controllers;

import java.net.URI;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.NoSuchElementException;

import com.example.restfulwebservice.beans.Posts;
import com.example.restfulwebservice.beans.dao.PostsDao;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.restfulwebservice.beans.Users;
import com.example.restfulwebservice.beans.dao.UsersDao;
import com.example.restfulwebservice.exceptions.UserNotFoundException;

@RestController
public class UsersController {
	
	private UsersDao userdao;
	private PostsDao postsDao;
	
	public UsersController(UsersDao userdao, PostsDao postsDao) {
		super();
		this.userdao = userdao;
		this.postsDao = postsDao;
	}

	@GetMapping("/users")
	public List<Users> getUsers() {
		return userdao.findAll();
	}
	
	@GetMapping("/users/{id}")
	public Users getUser(@PathVariable int id) {
		try {
		return userdao.findById(id).get();
		}catch(NoSuchElementException e){
			throw new UserNotFoundException("User doesn't exist");
		}
	}

	@GetMapping("/users/{id}/posts")
	public List<Posts> getUserPosts(@PathVariable int id) {
		Users user;
		try {
			user = userdao.findById(id).get();
		}catch(NoSuchElementException e){
			throw new UserNotFoundException("User doesn't exist");
		}
		return user.getPosts();
	}
	
	@PostMapping ("/users")
	public ResponseEntity<Users> addUser(@Valid @RequestBody Users user){
		Users savedUser = userdao.save(user);
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(savedUser.getId())
				.toUri();
		return ResponseEntity.created(location).build();
	}

	@PostMapping ("/users/{id}/posts")
	public List<Posts> addPost(@PathVariable int id, @Valid @RequestBody Posts post){

		Users user;
		try {
			user = userdao.findById(id).get();
		}catch(NoSuchElementException e){
			throw new UserNotFoundException("User doesn't exist");
		}
		post.setUser(user);
		Posts savedPost = postsDao.save(post);
		return user.getPosts();
	}
	
	@DeleteMapping("/users/{id}")
	public void deleteUser(@PathVariable int id) {
		userdao.deleteById(id);
	}
}

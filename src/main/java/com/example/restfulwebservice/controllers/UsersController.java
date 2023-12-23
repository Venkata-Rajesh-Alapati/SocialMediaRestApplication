package com.example.restfulwebservice.controllers;

import com.example.restfulwebservice.beans.Posts;
import com.example.restfulwebservice.beans.Users;
import com.example.restfulwebservice.beans.dao.PostsDao;
import com.example.restfulwebservice.beans.dao.UsersDao;
import com.example.restfulwebservice.exceptions.UserNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
public class UsersController {
	
	private UsersDao userdao;
	private PostsDao postsDao;
	
	public UsersController(UsersDao userdao, PostsDao postsDao) {
		super();
		this.userdao = userdao;
		this.postsDao = postsDao;
	}

	@GetMapping("/")
	public String welcome(){
		return "Documentation available at /swagger-ui.html \n Database at /h2-console";
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
	public Posts addPost(@PathVariable int id, @Valid @RequestBody Posts post){

		Users user;
		try {
			user = userdao.findById(id).get();
		}catch(NoSuchElementException e){
			throw new UserNotFoundException("User doesn't exist");
		}
		post.setUser(user);
		Posts savedPost = postsDao.save(post);
		return savedPost;
	}
	
	@DeleteMapping("/users/{id}")
	public void deleteUser(@PathVariable int id) {
		userdao.deleteById(id);
	}

	@DeleteMapping("/users/{userid}/posts/{postid}")
	public void deletePost(@PathVariable int userid, @PathVariable int postid) {
		postsDao.deletePost(userid, postid);
	}
}

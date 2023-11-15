package com.example.restfulwebservice.beans;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.PastOrPresent;

@Entity
@Table(
		name="USERS",
		uniqueConstraints=
		@UniqueConstraint(columnNames={"USERNAME"})
)
public class Users {
	
	@Id
	@GeneratedValue
	private int id;

	@NotEmpty
	private String userName;
	@PastOrPresent
	private LocalDate age;

	@OneToMany(mappedBy = "user")
	@JsonIgnore
	private List<Posts> posts;

	public List<Posts> getPosts() {
		return posts;
	}

	public void setPosts(List<Posts> posts) {
		this.posts = posts;
	}

	public Users() {
		super();
	}


	public Users(int id, String userName, LocalDate age) {
		super();
		this.id = id;
		this.userName = userName;
		this.age = age;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public LocalDate getAge() {
		return age;
	}


	public void setAge(LocalDate age) {
		this.age = age;
	}


	@Override
	public String toString() {
		return "Users [id=" + id + ", name=" + userName + ", age=" + age + "]";
	}
	
	
	
	
}

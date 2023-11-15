package com.example.restfulwebservice.beans;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotEmpty;

@Entity
public class Posts {
    public Posts() {
    }

    public Posts(int id, String description, Users user) {
        this.id = id;
        this.description = description;
        this.user = user;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    @Id
    @GeneratedValue
    private int id;
    @NotEmpty
    private String description;

    @ManyToOne
    @JsonIgnore
    private Users user;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Posts{" +
                "id=" + id +
                ", description='" + description + '\'' +
                '}';
    }
}

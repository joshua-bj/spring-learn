package com.example;

import com.fasterxml.jackson.annotation.JsonProperty;

public class User {
    @JsonProperty("full_name")
    private String name;

    private int age;

    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() { return name; }
    public int getAge() { return age; }
}
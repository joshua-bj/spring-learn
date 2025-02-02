package com.example;

import com.fasterxml.jackson.annotation.JsonProperty;

public class User {
    @JsonProperty("full_name")
    private String name;

    private int age;

    // this default constructor for jackson, use it to
    // create User object from json
    public User() {
        name = "";
        age = 0;
    }

    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() { return name; }
    public int getAge() { return age; }

    public String toString() {
        return String.format("name:%s, ago:%d", name, age);
    }

}
package com.example.dto;


import java.io.Serializable;


public class TUser implements Serializable {
    public TUser(Integer id, String name, String pass) {
        this.id = id;
        this.name = name;
        this.pass = pass;
    }

    public TUser() {
    }

    private Integer id;

    private String name;
    private String pass;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    @Override
    public String toString() {
        return "TUser{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", pass='" + pass + '\'' +
                '}';
    }
}

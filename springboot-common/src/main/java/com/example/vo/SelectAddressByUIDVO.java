package com.example.vo;


import java.util.List;


public class SelectAddressByUIDVO {
    private Integer id;
    private String name;
    private List<String> tAddressList;

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

    public List<String> gettAddressList() {
        return tAddressList;
    }

    public void settAddressList(List<String> tAddressList) {
        this.tAddressList = tAddressList;
    }
}

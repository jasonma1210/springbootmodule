package com.example.dto;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class TUser {
    private Integer id;

    private String name;

    private String pass;
}
package model;

import lombok.Data;

@Data
public class Login {

    private String username;
    private String password;
    private int id_reader;
}
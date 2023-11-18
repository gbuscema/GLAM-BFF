package com.glam.userservice.dao;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name="userdao")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDAO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;

    private String email;

    private String password;

    private String firstName;

    private String lastName;

    private String gender;

    private Boolean locationAgreement;

    private Long fashionCanon;

    private List<String> styles;

    private String livingLocation;

    private List<String> season;

}

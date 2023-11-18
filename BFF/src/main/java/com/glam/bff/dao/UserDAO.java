package com.glam.bff.dao;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name="userdao")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDAO {

    @Id
    @Column(name="userId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;

    @Column(name="email")
    private String email;

    @Column(name="password")
    private String password;

    @Column(name="firstName")
    private String firstName;

    @Column(name="lastName")
    private String lastName;

    @Column(name="gender")
    private String gender;

    @Column(name="locationAgreement")
    private Boolean locationAgreement;

    @Column(name="fashionCanon")
    private Long fashionCanon;

    @Column(name="styles")
    private List<String> styles;

    @Column(name="livingLocation")
    private String livingLocation;

    @Column(name="season")
    private List<String> season;

}

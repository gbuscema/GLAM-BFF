package com.glam.bff.dao;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.List;

@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDAO {

    @Id
    private String userId;

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

package com.glam.bff.utils;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class UserContext {

    private String userId;

    public void reset(){
        this.userId = null;
    }

    public boolean checkUser(String userId){
        if(userId == null || this.userId == null || userId.isBlank() || this.userId.isBlank() || userId.equals(this.userId)){
            throw new RuntimeException("UserId not valid");
        }
        return true;
    }
}

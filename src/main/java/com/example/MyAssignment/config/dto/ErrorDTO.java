package com.example.MyAssignment.config.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorDTO implements java.io.Serializable {

    private String message;
    private boolean success = false;

    public ErrorDTO(String message){
        this.message = message;
    }
}
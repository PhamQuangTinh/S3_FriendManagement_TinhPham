package com.example.MyAssignment.enums;

import lombok.Getter;
import lombok.Setter;

@Getter
public enum TypeResponse {

    OBJECT_RESPONSE(1),
    LIST_RESPONSE(2)

    ;

    private final int id;

    TypeResponse(int id) {
        this.id = id;
    }
}

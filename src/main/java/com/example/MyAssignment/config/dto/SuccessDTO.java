package com.example.MyAssignment.config.dto;

import com.example.MyAssignment.enums.TypeResponse;
import com.example.MyAssignment.response.parent_response.MainResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
import java.util.Map;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SuccessDTO<T> implements java.io.Serializable {
    private T body;
    private int length = 1;
    private boolean success = true;

    public SuccessDTO(MainResponse<T> respnose) {
        if (respnose.getTypeResponse() == TypeResponse.LIST_RESPONSE){
            this.body = (T) respnose.getListResponse();
        }else{
            this.body = (T) respnose.getResponse();
        }

        if (this.body instanceof Collection) {
            this.length = ((Collection) this.body).size();
        }
        if (this.body instanceof Map) {
            this.length = ((Map) this.body).size();
        }
    }

}
package com.example.MyAssignment.response.parent_response;

import com.example.MyAssignment.enums.TypeResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Collection;

@Data
@AllArgsConstructor
@Builder
public class MainResponse<T> {
    private T response;
    private Collection<T> listResponse;
    private String message;
    private boolean success;
    private TypeResponse typeResponse; //1: object response  2: list response;
    private MainResponse(boolean isSuccess){
        this.success = isSuccess;
    }

    private MainResponse(String message){
        this.message = message;
        this.success = false;
    }

    private MainResponse(T response){
        this.response = response;
        this.success = true;
        this.typeResponse = TypeResponse.OBJECT_RESPONSE;
    }

    private MainResponse(Collection<T> response){
        this.listResponse = response;
        this.success = true;
        this.typeResponse = TypeResponse.LIST_RESPONSE;
    }

    public static <T> MainResponse<T> error(){
        return new MainResponse<>(false);
    }

    public static <T> MainResponse<T> error(String message){
        return new MainResponse<>(message);
    }

    public static <T> MainResponse<T> success(){
        return new MainResponse<>(true);
    }

    public static <T> MainResponse<T> success(T response){
        return new MainResponse<>(response);
    }

    public static <T> MainResponse<T> success(Collection<T> response){
        return new MainResponse<>(response);
    }

}

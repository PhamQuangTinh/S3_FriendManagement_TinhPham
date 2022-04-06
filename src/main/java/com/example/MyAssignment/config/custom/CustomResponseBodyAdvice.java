package com.example.MyAssignment.config.custom;

import com.example.MyAssignment.config.annotation.IgnoreResponseBinding;
import com.example.MyAssignment.config.dto.ErrorDTO;
import com.example.MyAssignment.config.dto.SuccessDTO;
import com.example.MyAssignment.response.parent_response.MainResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.Objects;

@RestControllerAdvice(value = "com.example.MyAssignment.controller")
public class CustomResponseBodyAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, @Nullable Class<? extends HttpMessageConverter<?>> converterType) {
        return returnType.getParameterType() == ResponseEntity.class;
    }

    @Override
    public Object beforeBodyWrite(Object data, MethodParameter methodParameter, @Nullable MediaType selectedContentType, @Nullable Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if (!Objects.requireNonNull(methodParameter.getMethod()).isAnnotationPresent(IgnoreResponseBinding.class)) {
            if(data instanceof MainResponse){
                MainResponse myResponse = (MainResponse) data;
                if (myResponse.isSuccess() == true){
                    return new SuccessDTO<>(myResponse);
                }else{
                    return new ErrorDTO(myResponse.getMessage());
                }
            }
        }
        return data;
    }
}

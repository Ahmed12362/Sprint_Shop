package com.example.Sprint.handleException;

import com.example.Sprint.exception.AlreadyExists;
import com.example.Sprint.exception.ResourceNotFound;
import com.example.Sprint.response.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class HandleGlobalException {
    @ExceptionHandler(ResourceNotFound.class)
    public ResponseEntity<BaseResponse> handleResourceNotFound(ResourceNotFound ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new BaseResponse(ex.getMessage() , null));
    }
    @ExceptionHandler(AlreadyExists.class)
    public ResponseEntity<BaseResponse> handleAlreadyExists(AlreadyExists ex){
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new BaseResponse(ex.getMessage() , null));
    }
}

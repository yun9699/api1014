package org.zerock.api1014.category.advice;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Log4j2
public class CategoryControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<java.util.Map<String,Object>> handle(MethodArgumentNotValidException ex){

        BindingResult bindingResult = ex.getBindingResult();

        Map<String,Object> map = new HashMap<>();

        ex.getFieldErrors().forEach(fieldError -> {
            log.error("-------------");
            String key = fieldError.getField();

            Object value = fieldError.getDefaultMessage();
            map.put(key,value);
        });


        return ResponseEntity.status(400).body(map);

    }

}

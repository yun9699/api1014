package org.zerock.api1014.member.controller;


import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.zerock.api1014.member.exception.MemberTaskException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Log4j2
public class MemberControllerAdvice {

    @ExceptionHandler(MemberTaskException.class)
    public ResponseEntity<java.util.Map<String,Object>> ex(MemberTaskException ex){

        log.error("============================================");
        StackTraceElement[] arr =  ex.getStackTrace();
        for(StackTraceElement ste : arr){
            log.error(ste.toString());
        }
        log.error("============================================");

        Map<String, Object> msgMap = new HashMap<String, Object>();
        msgMap.put("message", ex.getMessage());
        return ResponseEntity.status(ex.getStatus()).body(msgMap);
    }
}
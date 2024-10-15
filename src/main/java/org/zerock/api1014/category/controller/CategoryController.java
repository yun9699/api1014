package org.zerock.api1014.category.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.api1014.category.dto.CategoryDTO;

import java.util.Map;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/api/v1/category")
public class CategoryController {

    //Mapping안에 produce,consume 넣을 수 있음
    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String,Long>> register(
            //JSON 데이터 받아 처리할때는 RequestBody 사용
            // Validated로 검사
            @RequestBody @Validated CategoryDTO categoryDTO){

        log.info("category register " + categoryDTO);

        return null;
    }

}
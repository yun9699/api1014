package org.zerock.api1014.product.controller;

import jakarta.annotation.security.PermitAll;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.zerock.api1014.common.dto.PageRequestDTO;
import org.zerock.api1014.common.dto.PageResponseDTO;
import org.zerock.api1014.product.dto.ProductListDTO;
import org.zerock.api1014.product.service.ProductService;

@RestController
@RequestMapping("/api/v1/product")
@Log4j2
@RequiredArgsConstructor
@PreAuthorize("permitAll()")
public class ProductController {

    private final ProductService productService;

//    @PreAuthorize("permitAll()")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("list")
    public ResponseEntity<PageResponseDTO<ProductListDTO>> list(
            @Validated PageRequestDTO requestDTO
    ){

        log.info("---------------------Product Controller list");
        return ResponseEntity.ok(productService.list(requestDTO));
    }

}
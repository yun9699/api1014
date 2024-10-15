package org.zerock.api1014.product.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.api1014.common.dto.PageRequestDTO;
import org.zerock.api1014.common.dto.PageResponseDTO;
import org.zerock.api1014.common.exception.CommonExceptions;
import org.zerock.api1014.product.dto.ProductListDTO;

@Service
@Transactional
@Log4j2
@RequiredArgsConstructor
public class ProductService {

    public PageResponseDTO<ProductListDTO> list(PageRequestDTO pageRequestDTO) {

        if(pageRequestDTO.getPage() < 0) {
            throw CommonExceptions.LIST_ERROR.get();
        }

        return null;
    }

}
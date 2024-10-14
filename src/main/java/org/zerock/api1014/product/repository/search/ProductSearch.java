package org.zerock.api1014.product.repository.search;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.zerock.api1014.common.dto.PageRequestDTO;
import org.zerock.api1014.common.dto.PageResponseDTO;
import org.zerock.api1014.product.domain.Product;
import org.zerock.api1014.product.dto.ProductListDTO;

public interface ProductSearch {

    Page<Product> list(Pageable pageable);

    PageResponseDTO<ProductListDTO> listByCno(Long cno, PageRequestDTO pageRequestDTO);


}

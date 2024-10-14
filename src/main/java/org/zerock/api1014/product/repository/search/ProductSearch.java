package org.zerock.api1014.product.repository.search;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.zerock.api1014.product.domain.Product;

public interface ProductSearch {

    Page<Product> list(Pageable pageable);

}

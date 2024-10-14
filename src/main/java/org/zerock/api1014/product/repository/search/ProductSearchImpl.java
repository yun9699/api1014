package org.zerock.api1014.product.repository.search;

import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.zerock.api1014.product.domain.Product;

@Log4j2
public class ProductSearchImpl extends QuerydslRepositorySupport implements ProductSearch {

    public ProductSearchImpl() {
        super(Product.class);
    }

    @Override
    public Page<Product> list(Pageable pageable) {

        log.info("list ---------------!!");
        return null;
    }
}

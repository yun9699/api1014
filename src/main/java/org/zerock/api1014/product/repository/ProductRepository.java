package org.zerock.api1014.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.api1014.product.domain.Product;
import org.zerock.api1014.product.repository.search.ProductSearch;

public interface ProductRepository extends JpaRepository<Product, Long>, ProductSearch {


}

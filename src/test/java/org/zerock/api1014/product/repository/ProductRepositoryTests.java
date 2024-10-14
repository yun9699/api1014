package org.zerock.api1014.product.repository;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.zerock.api1014.common.dto.PageRequestDTO;


@DataJpaTest
@Log4j2
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ProductRepositoryTests {

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void testList1() {

        Pageable pageable = PageRequest.of(0,10);

        productRepository.list(pageable);

    }

    @Test
    public void testList2() {

        PageRequestDTO pageRequestDTO = PageRequestDTO.builder().build();
        productRepository.listByCno(1L, pageRequestDTO);

    }

}

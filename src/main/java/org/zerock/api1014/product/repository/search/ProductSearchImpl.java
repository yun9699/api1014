package org.zerock.api1014.product.repository.search;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.zerock.api1014.category.domain.QCategory;
import org.zerock.api1014.category.domain.QCategoryProduct;
import org.zerock.api1014.product.domain.Product;
import org.zerock.api1014.product.domain.QAttachFile;
import org.zerock.api1014.product.domain.QProduct;
import org.zerock.api1014.product.domain.QReview;
import org.zerock.api1014.product.dto.ProductListDTO;

import java.util.List;

@Log4j2
public class ProductSearchImpl extends QuerydslRepositorySupport implements ProductSearch {

    public ProductSearchImpl() {
        super(Product.class);
    }

    @Override
    public Page<Product> list(Pageable pageable) {

        log.info("list ---------------!!");

        QProduct product = QProduct.product;
        QReview review = QReview.review;

        QAttachFile attachFile = QAttachFile.attachFile;

        JPQLQuery<Product> query = from(product);
        query.leftJoin(review).on(review.product.eq(product));
        query.leftJoin(product.attachFiles, attachFile);

        query.where(attachFile.ord.eq(0));
        query.groupBy(product);

        //페이징 처리 정렬처리
        this.getQuerydsl().applyPagination(pageable, query);

        JPQLQuery<Tuple> tupleQuery =
                query.select(
                        product,
                        review.count(),
                        attachFile.fileName
                );

        tupleQuery.fetch();

        return null;
    }

    @Override
    public Page<Product> listByCno(Long cno, Pageable pageable) {

        QProduct product = QProduct.product;
        QReview review = QReview.review;
        QAttachFile attachFile = QAttachFile.attachFile;
        QCategoryProduct categoryProduct = QCategoryProduct.categoryProduct;

        JPQLQuery<Product> query = from(product);
        query.leftJoin(review).on(review.product.eq(product));
        query.leftJoin(categoryProduct).on(categoryProduct.product.eq(product));
        query.leftJoin(product.attachFiles, attachFile);

        query.where(attachFile.ord.eq(0));
        query.where(categoryProduct.category.cno.eq(cno));
        query.groupBy(product);

        //페이징 처리 정렬처리
        this.getQuerydsl().applyPagination(pageable, query);

        JPQLQuery<Tuple> tupleQuery =
                query.select(
                        product,
                        review.count(),
                        attachFile.fileName
                );

        List<Tuple> tupleList = tupleQuery.fetch();

        log.info(tupleList);

        tupleList.forEach(t -> {
            Product productObj = t.get(0, Product.class);
            long count = t.get(1, Long.class);
            String fileName = t.get(2, String.class);

            log.info(productObj);
            log.info(productObj.getTags());
            log.info(count);
            log.info(fileName);
        });

        return null;
    }
}

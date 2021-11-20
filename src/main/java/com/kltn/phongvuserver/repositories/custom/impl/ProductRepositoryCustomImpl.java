package com.kltn.phongvuserver.repositories.custom.impl;

import com.kltn.phongvuserver.models.Product;
import com.kltn.phongvuserver.models.QCategory;
import com.kltn.phongvuserver.models.QDemand;
import com.kltn.phongvuserver.models.QProduct;
import com.kltn.phongvuserver.repositories.custom.ProductRepositoryCustom;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Set;

public class ProductRepositoryCustomImpl implements ProductRepositoryCustom {
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Product> findProductsItemByCategoryAndBrandOrDemand(Set<Integer> idCategories, int demand, int brand, int page) {
        QProduct product = QProduct.product;
        QCategory category = QCategory.category;

        JPAQuery<Product> query = new JPAQuery<Product>(em)
                .from(product)
                .join(product.category, category)
                .where(category.id.in(idCategories))
                ;
//                        .and(product.visibility.isTrue()));
//                        .and(brand == 0 ? Expressions.TRUE.isTrue() : product.brand.id.eq(brand))
//                        .and(demand == 0 ? Expressions.TRUE.isTrue() : product.demands.any().id.eq(demand)));

        return query.offset((page - 1) < 0 ? 0 : (long) (page - 1) * 20).limit(20).fetch();
    }
}

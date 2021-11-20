package com.kltn.phongvuserver.repositories;

import com.kltn.phongvuserver.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer>, QuerydslPredicateExecutor<Category> {
    boolean existsCategoryById(int id);

    @Query(value = "select c from Category c left join fetch c.categories left join fetch c.demands where c.id = :id and c.level=1")
    Category findCategoryByIdFetchCategorySubLevel1(int id);

    List<Category> findCategoriesByLevel(int level);

    @Query(value = "select c from Category c left join fetch c.categories where c.id = :id")
    Category findCategoryFetchSubCategories(@Param("id") int id);
}

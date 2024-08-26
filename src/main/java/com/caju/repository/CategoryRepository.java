package com.caju.repository;

import com.caju.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    boolean existsByType(String type);

    @Query("SELECT c FROM Category c WHERE c.type = :type")
    Optional<Category> findCategoryByType(String type);
}

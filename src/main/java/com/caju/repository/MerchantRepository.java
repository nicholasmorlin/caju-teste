package com.caju.repository;

import com.caju.model.Merchant;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MerchantRepository extends CrudRepository<Merchant, Long> {

    @Query("SELECT m FROM Merchant m WHERE m.name = :name")
    Optional<Merchant> findCategoryByMerchantName(String name);
}

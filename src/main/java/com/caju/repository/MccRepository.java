package com.caju.repository;

import com.caju.model.Mcc;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MccRepository extends CrudRepository<Mcc, Long> {

    @Query("SELECT m FROM Mcc m WHERE m.code = :code")
    Optional<Mcc> findMccAndCategoryTypeByCode(String code);
}

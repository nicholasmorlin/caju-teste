package com.caju.repository;

import com.caju.model.Mcc;
import com.caju.repository.dto.MccCategoryDto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MccRepository extends CrudRepository<Mcc, Long> {

    @Query("SELECT m FROM Mcc m WHERE m.code = :code")
    Optional<Mcc> findMccAndCategoryTypeByCode(String code);
}

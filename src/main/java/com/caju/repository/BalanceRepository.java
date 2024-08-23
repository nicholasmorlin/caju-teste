package com.caju.repository;

import com.caju.model.Balance;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BalanceRepository extends CrudRepository<Balance, Long> {

    @Query("SELECT b FROM Balance b WHERE b.accountId.id = :accountId AND b.categoryId.id = :categoryId")
    Balance findBalanceByAccountIdAndCategoryId(Long accountId, Long categoryId);

}

package com.caju.repository;

import com.caju.model.Balance;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BalanceRepository extends CrudRepository<Balance, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT b FROM Balance b WHERE b.accountId.id = :accountId AND b.categoryId.id = :categoryId")
    Balance findBalanceByAccountIdAndCategoryId(Long accountId, Long categoryId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT b FROM Balance b, Category c WHERE b.accountId.id = :accountId AND b.categoryId.id = c.id AND c.type = :categoryType")
    Balance findBalanceByAccountIdAndCategoryType(Long accountId, String categoryType);

}

package com.caju.repository;

import com.caju.model.PaymentHistory;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PaymentHistoryRepository extends CrudRepository<PaymentHistory, Long> {

//    @Query("SELECT ph FROM PaymentHistory ph WHERE " +
//            "(:accountId IS NULL OR ph.accountId.id = :accountId) ")

    @Query("SELECT ph FROM PaymentHistory ph WHERE ph.accountId.id = :accountId AND " +
            "(:category IS NULL OR ph.categoryId.type = :category) AND " +
            "(:merchant IS NULL OR ph.merchant = :merchant) AND " +
            "(:startDate IS NULL OR ph.creationDate >= :startDate) AND" +
            "(:endDate IS NULL OR ph.creationDate <= :endDate)")
    List<PaymentHistory> findByFilter(@Param("accountId") Long accountId,
                                      @Param("category") String category,
                                      @Param("merchant") String merchant,
                                      @Param("startDate") LocalDateTime startDate,
                                      @Param("endDate") LocalDateTime endDate);
}


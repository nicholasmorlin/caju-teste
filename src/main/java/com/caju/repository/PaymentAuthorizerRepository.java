package com.caju.repository;

import com.caju.model.PaymentHistory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentAuthorizerRepository extends CrudRepository<PaymentHistory, Long> {

}

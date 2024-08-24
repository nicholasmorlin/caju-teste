package com.caju.services;

import com.caju.model.Merchant;
import com.caju.repository.MerchantRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MerchantService {

    private final MerchantRepository merchantRepository;

    public MerchantService(MerchantRepository merchantRepository) {
        this.merchantRepository = merchantRepository;
    }

    public Optional<Merchant> findCategoryByMerchantName(String merchantName){
        return merchantRepository.findCategoryByMerchantName(merchantName);
    }
}

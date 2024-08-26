package com.caju.services;

import com.caju.controllers.dto.request.MerchantCreationRequest;
import com.caju.controllers.dto.response.MerchantResponse;
import com.caju.exceptions.NotFoundException;
import com.caju.helpers.MerchantConverter;
import com.caju.model.Category;
import com.caju.model.Merchant;
import com.caju.repository.MerchantRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MerchantService {

    private final CategoryService categoryService;
    private final MerchantRepository merchantRepository;

    public MerchantService(CategoryService categoryService, MerchantRepository merchantRepository) {
        this.categoryService = categoryService;
        this.merchantRepository = merchantRepository;
    }

    public Optional<Merchant> findCategoryByMerchantName(String merchantName) {
        return merchantRepository.findCategoryByMerchantName(merchantName);
    }

    public void createMerchant(MerchantCreationRequest merchantCreationRequest) {
        Optional<Category> categoryOptional = categoryService.findCategoryByType(merchantCreationRequest.categoryName());

        if (categoryOptional.isEmpty()) {
            throw new NotFoundException("Category not registered");
        }

        Merchant merchant = new Merchant(categoryOptional.get(), merchantCreationRequest.merchantName());
        merchantRepository.save(merchant);
    }

    public MerchantResponse getMerchant(Long id) {
        return merchantRepository.findById(id)
                .map(MerchantConverter::toResponse)
                .orElseThrow(() -> new NotFoundException("Merchant is not registered"));
    }
}

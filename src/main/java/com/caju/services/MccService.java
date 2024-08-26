package com.caju.services;

import com.caju.controllers.dto.request.MccCreationRequest;
import com.caju.controllers.dto.response.MccResponse;
import com.caju.exceptions.NotFoundException;
import com.caju.helpers.AccountConverter;
import com.caju.helpers.MccConverter;
import com.caju.model.Category;
import com.caju.model.Mcc;
import com.caju.repository.MccRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MccService {

    private final CategoryService categoryService;
    private final MccRepository mccRepository;


    public MccService(CategoryService categoryService, MccRepository mccRepository) {
        this.categoryService = categoryService;
        this.mccRepository = mccRepository;
    }

    public Optional<Mcc> findCategoryTypeByMccCode(String code) {
        return mccRepository.findMccAndCategoryTypeByCode(code);
    }

    public void createMcc(MccCreationRequest mccCreationRequest) {
        Optional<Category> categoryOptional = categoryService.findCategoryByType(mccCreationRequest.categoryName());

        if (categoryOptional.isEmpty()) {
            throw new NotFoundException("Category not registered");
        }

        Mcc mcc = new Mcc(categoryOptional.get(), mccCreationRequest.code());
        mccRepository.save(mcc);
    }

    public List<MccResponse> findAllMcc() {
        return mccRepository.findAll().stream().map(MccConverter::toResponse).collect(Collectors.toList());
    }

    public MccResponse findMccByCode(String code) {
        return mccRepository.findMccAndCategoryTypeByCode(code)
                .map(MccConverter::toResponse)
                .orElseThrow(() -> new NotFoundException("Mcc code is not registered"));
    }
}

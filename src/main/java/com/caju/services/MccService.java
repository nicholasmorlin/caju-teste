package com.caju.services;

import com.caju.model.Mcc;
import com.caju.repository.MccRepository;
import com.caju.repository.dto.MccCategoryDto;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MccService {

    private final MccRepository mccRepository;


    public MccService(MccRepository mccRepository) {
        this.mccRepository = mccRepository;
    }

    public Optional<Mcc> findCategoryTypeByMccCode(String code) {
        return mccRepository.findMccAndCategoryTypeByCode(code);
    }
}

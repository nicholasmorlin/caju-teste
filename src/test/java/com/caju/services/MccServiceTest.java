package com.caju.services;

import com.caju.model.Category;
import com.caju.model.Mcc;
import com.caju.repository.MccRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.caju.mock.CategoryMock.generateCategory;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MccServiceTest {

    @InjectMocks
    private MccService mccService;

    @Mock
    private MccRepository mccRepository;

    @Test
    void shouldFindMccByCategoryType() {
        Mcc mcc = generateMockMcc();
        when(mccRepository.findMccAndCategoryTypeByCode("FOOD")).thenReturn(Optional.of(mcc));

        Optional<Mcc> mccReturned = mccService.findCategoryTypeByMccCode("FOOD");

        assertEquals(mccReturned.get().getCategoryId(), mcc.getCategoryId());
        assertEquals(mccReturned.get().getId(), mcc.getId());
    }

    public static Mcc generateMockMcc() {
        return new Mcc(1L, generateCategory(1L, "FOOD"), "5541");
    }
}

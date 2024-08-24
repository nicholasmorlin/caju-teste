package com.caju.services;

import com.caju.model.Category;
import com.caju.model.Merchant;
import com.caju.repository.MerchantRepository;
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
public class MerchantServiceTest {

    @InjectMocks
    private MerchantService merchantService;

    @Mock
    private MerchantRepository merchantRepository;

    @Test
    void shouldFindMerchantByName() {
        Merchant merchant = generateMockMerchant();
        when(merchantRepository.findCategoryByMerchantName("PADARIA DO CAJU")).thenReturn(Optional.of(merchant));

        Optional<Merchant> merchantReturned = merchantService.findCategoryByMerchantName("PADARIA DO CAJU");

        assertEquals(merchantReturned.get().getCategory().getId(), merchant.getCategory().getId());
        assertEquals(merchantReturned.get().getId(), merchant.getId());
    }

    private Merchant generateMockMerchant() {
        return new Merchant(1L, generateCategory(1L, "FOOD"), "PADARIA DO CAJU");
    }
}

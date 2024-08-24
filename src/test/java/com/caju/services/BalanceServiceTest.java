package com.caju.services;

import com.caju.exceptions.NotFoundException;
import com.caju.model.Balance;
import com.caju.repository.BalanceRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static com.caju.mock.BalanceMock.generateMockBalance;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BalanceServiceTest {

    @InjectMocks
    private BalanceService balanceService;

    @Mock
    private BalanceRepository balanceRepository;

    @Test
    void shouldNotFindBalanceByAccountIdAndCategoryId() {
        NotFoundException exception = assertThrows(NotFoundException.class, () -> balanceService.findBalanceByAccountIdAndCategoryId(1L, 1L));
        assertEquals("Balance not found", exception.getMessage());
    }

    @Test
    void shouldFindBalanceByAccountIdAndCategoryId() {
        Balance balance = generateMockBalance("FOOD", BigDecimal.valueOf(1000));
        when(balanceRepository.findBalanceByAccountIdAndCategoryId(1L, 1L)).thenReturn(Optional.of(balance));

        Balance balanceReturned = balanceService.findBalanceByAccountIdAndCategoryId(1L, 1L);

        assertEquals(balanceReturned.getBalance(), balance.getBalance());
        assertEquals(balanceReturned.getCategoryId(), balance.getCategoryId());
        assertEquals(balanceReturned.getAccountId(), balance.getAccountId());
    }

    @Test
    void shouldNotBalanceByAccountIdAndCategoryType() {
        NotFoundException exception = assertThrows(NotFoundException.class, () -> balanceService.findBalanceByAccountIdAndCategoryType(1L, "CASH"));
        assertEquals("Balance not found", exception.getMessage());
    }

    @Test
    void shouldFindBalanceByAccountIdAndCategoryType() {
        Balance balance = generateMockBalance("CASH", BigDecimal.valueOf(1000));
        when(balanceRepository.findBalanceByAccountIdAndCategoryType(1L, "CASH")).thenReturn(Optional.of(balance));

        Balance balanceReturned = balanceService.findBalanceByAccountIdAndCategoryType(1L, "CASH");

        assertEquals(balanceReturned.getBalance(), balance.getBalance());
        assertEquals(balanceReturned.getCategoryId(), balance.getCategoryId());
        assertEquals(balanceReturned.getAccountId(), balance.getAccountId());
    }

    @Test
    void shouldSaveNewBalance() {
        Balance balance = generateMockBalance("MEAL", BigDecimal.valueOf(1000));
        BigDecimal totalSpent = BigDecimal.valueOf(100);

        balanceService.saveNewBalance(balance, totalSpent);

        verify(balanceRepository, times(1)).save(balance);
    }
}

package daste.spendaste.module.spend.services;

import daste.spendaste.module.spend.entities.MonthBalance;
import daste.spendaste.module.spend.models.MonthBudget;
import daste.spendaste.module.spend.repositories.MonthBalanceRepository;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class MonthBalanceService {

    private final MonthBalanceRepository monthBalanceRepository;
    private final BalanceCalculatorService calculatorService;

    public MonthBalanceService(MonthBalanceRepository monthBalanceRepository, BalanceCalculatorService calculatorService) {
        this.calculatorService = calculatorService;
        this.monthBalanceRepository = monthBalanceRepository;
    }

    @Cacheable(value = "monthBalance", keyGenerator = "tenantMonthBalance")
    public MonthBalance getOrCreateMonthBalance(Integer yearMonth) {
        return monthBalanceRepository.findByYearMonth(yearMonth)
                .orElseGet(() -> calculatorService.createBalance(yearMonth));
    }

    public MonthBalance calculateMonthBalance(Integer monthYear) {
        return calculatorService.calculate(monthYear);
    }

    @CachePut(value = "monthBalance", keyGenerator = "tenantMonthBalance")
    public MonthBalance updateMonthBudget(Integer yearMonth, MonthBudget budget) {
        MonthBalance balance = monthBalanceRepository.findByYearMonth(yearMonth)
                .orElseGet(() -> calculatorService.createBalance(yearMonth));
        balance.setMonthBudget(budget);
        return balance;
    }
}

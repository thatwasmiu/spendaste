package daste.spendaste.module.spend.services;

import daste.spendaste.core.security.SecurityUtils;
import daste.spendaste.module.spend.entities.MonthBalance;
import daste.spendaste.module.spend.repositories.MonthBalanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
}

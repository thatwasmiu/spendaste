package daste.spendaste.module.spend.services;

import daste.spendaste.core.security.SecurityUtils;
import daste.spendaste.module.spend.entities.MonthBalance;
import daste.spendaste.module.spend.repositories.MonthBalanceRepository;
import org.springframework.stereotype.Service;

@Service
public class MonthBalanceService {

    private final MonthBalanceRepository monthBalanceRepository;

    private final BalanceCalculatorService calculatorService;

    private final Long userId;

    public MonthBalanceService(MonthBalanceRepository monthBalanceRepository, BalanceCalculatorService calculatorService) {
        this.calculatorService = calculatorService;
        this.monthBalanceRepository = monthBalanceRepository;
        this.userId = SecurityUtils.getCurrentLoginUserId();
    }

    public MonthBalance getMonthBalance(Integer yearMonth) {
        return monthBalanceRepository.findByUserIdAndYearMonth(userId, yearMonth)
                .orElse(createBalance(userId, yearMonth));
    }

    public MonthBalance createBalance(Long userId, Integer yearMonth) {
        MonthBalance balance = calculatorService.initMonthBalance(userId, yearMonth);
        calculatorService.initMonthBudget(balance);
        calculatorService.initMonthSpend(balance);
        return balance;
    }

    public MonthBalance calculateMonthBalance(Integer monthYear) {
        return calculatorService.calculate(userId, monthYear);
    }
}

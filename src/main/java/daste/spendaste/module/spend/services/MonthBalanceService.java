package daste.spendaste.module.spend.services;

import daste.spendaste.core.security.SecurityUtils;
import daste.spendaste.module.spend.entities.MonthBalance;
import daste.spendaste.module.spend.models.MonthBudget;
import daste.spendaste.module.spend.models.MonthSpend;
import daste.spendaste.module.spend.repositories.MonthBalanceRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MonthBalanceService {

    private final MonthBalanceRepository monthBalanceRepository;

    private final MoneyTransactionService moneyTransactionService;

    public MonthBalanceService(MonthBalanceRepository monthBalanceRepository, MoneyTransactionService moneyTransactionService) {
        this.monthBalanceRepository = monthBalanceRepository;
        this.moneyTransactionService = moneyTransactionService;
    }

    public MonthBalance getMonthBalance(Integer yearMonth) {
        Long userId = 0L;
        return monthBalanceRepository.findByUserIdAndYearMonth(userId, yearMonth)
                .orElse(initMonthBalance(yearMonth));
    }

    public MonthBalance calculateMonthBalance(Integer monthYear) {
        Long userId = 0L;
        Optional<MonthBalance> balanceOptional = monthBalanceRepository.findByUserIdAndYearMonth(userId, monthYear);

        if (balanceOptional.isEmpty()) return initMonthBalance(monthYear);

        MonthBalance balance = balanceOptional.get();
        initMonthBudget(balance);
        initMonthBudget(balance);
        return balance;
    }

    public MonthBalance initMonthBalance(Integer monthYear) {
        Long userId = 0L;
        MonthBalance monthBalance = new MonthBalance();
        monthBalance.setYearMonth(monthYear);
        monthBalance.setUserId(userId);
        initMonthSpend(monthBalance);
        initMonthBudget(monthBalance);

        return monthBalanceRepository.save(monthBalance);
    }

    private void initMonthBudget(MonthBalance monthBalance) {
        int previousMonth = monthBalance.getYearMonthType().minusMonths(1).getMonthValue();
        Optional<MonthBalance> optionalMonthBalance = monthBalanceRepository.findByUserIdAndYearMonth(monthBalance.getUserId(), previousMonth);
        optionalMonthBalance.ifPresent(
                balance -> monthBalance.setMonthBudget(new MonthBudget(balance.getMonthSpend()))
        );
    }

    private void initMonthSpend(MonthBalance monthBalance) {
        MonthSpend monthSpend = moneyTransactionService.calculateAndgetMonthSpend(monthBalance.getUserId(), monthBalance.getYearMonth());
        monthBalance.setMonthSpend(monthSpend);
    }

    public void initMonthBalances(Long monthYear, Long numberOfMonth) {
        Long userId = SecurityUtils.getCurrentLoginUserId();
        List<MonthBalance> monthBalances = monthBalanceRepository.findAllByUserId(userId);
    }
}

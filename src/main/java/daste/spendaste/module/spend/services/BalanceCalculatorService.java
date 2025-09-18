package daste.spendaste.module.spend.services;

import daste.spendaste.module.spend.entities.MoneyTransaction;
import daste.spendaste.module.spend.entities.MonthBalance;
import daste.spendaste.module.spend.models.MonthBudget;
import daste.spendaste.module.spend.models.MonthSpend;
import daste.spendaste.module.spend.repositories.MoneyTransactionRepository;
import daste.spendaste.module.spend.repositories.MonthBalanceRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BalanceCalculatorService {
    private final MonthBalanceRepository monthBalanceRepository;
    private final MoneyTransactionRepository moneyTransactionRepository;

    public BalanceCalculatorService(MonthBalanceRepository monthBalanceRepository, MoneyTransactionRepository moneyTransactionRepository) {
        this.monthBalanceRepository = monthBalanceRepository;
        this.moneyTransactionRepository = moneyTransactionRepository;
    }

    public MonthBalance calculate(Long userId, Integer monthYear) {
        MonthBalance balance = monthBalanceRepository.findByUserIdAndYearMonth(userId, monthYear)
                .orElse(initMonthBalance(userId, monthYear));
        initMonthSpend(balance);
        if (balance.getMonthBudget().notAvailable()) {
            initMonthBudget(balance);
        }
        return balance;
    }

    public MonthBalance initMonthBalance(Long userId, Integer monthYear) {
        MonthBalance monthBalance = new MonthBalance();
        monthBalance.setMonthYearUser(Long.valueOf(monthYear + "" + userId));
        monthBalance.setYearMonth(monthYear);
        monthBalance.setUserId(userId);
        return monthBalanceRepository.save(monthBalance);
    }

    public MonthBalance initMonthBudget(MonthBalance monthBalance) {
        int previousMonth = monthBalance.getYearMonthType().minusMonths(1).getMonthValue();
        Optional<MonthBalance> optionalMonthBalance = monthBalanceRepository.findByUserIdAndYearMonth(monthBalance.getUserId(), previousMonth);
        optionalMonthBalance.ifPresent(
                balance -> monthBalance.setMonthBudget(new MonthBudget(balance.getMonthSpend()))
        );
        return monthBalance;
    }

    public MonthBalance initMonthSpend(MonthBalance monthBalance) {
        MonthSpend monthSpend = calculateAndgetMonthSpend(monthBalance.getUserId(), monthBalance.getYearMonth());
        monthBalance.setMonthSpend(monthSpend);
        return monthBalance;
    }

    public MonthSpend calculateAndgetMonthSpend(Long userId, Integer monthYear) {
        List<MoneyTransaction> transactions = moneyTransactionRepository.findByUserIdAndYearMonth(userId, monthYear);
        MonthSpend monthSpend = new MonthSpend();
        transactions.stream().filter(MoneyTransaction::isSpending)
                .forEach(monthSpend::addSpending);
        return monthSpend;
    }
}

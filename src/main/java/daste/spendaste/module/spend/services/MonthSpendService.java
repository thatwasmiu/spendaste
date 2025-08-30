package daste.spendaste.module.spend.services;

import daste.spendaste.module.spend.entities.MonthSpend;
import daste.spendaste.module.spend.repositories.MoneyTransactionRepository;
import daste.spendaste.module.spend.repositories.MonthBalanceRepository;
import daste.spendaste.module.spend.repositories.MonthSpendRepository;
import org.springframework.stereotype.Service;

@Service
public class MonthSpendService {

    private final MonthSpendRepository monthSpendRepository;

    private final MonthBalanceRepository monthBalanceRepository;

    private final MoneyTransactionRepository moneyTransactionRepository;

    public MonthSpendService(MonthSpendRepository monthSpendRepository, MonthBalanceRepository monthBalanceRepository, MoneyTransactionRepository moneyTransactionRepository) {
        this.monthSpendRepository = monthSpendRepository;
        this.monthBalanceRepository = monthBalanceRepository;
        this.moneyTransactionRepository = moneyTransactionRepository;
    }

    public MonthSpend createMonthSpend(MonthSpend monthSpend) {
        return monthSpendRepository.save(monthSpend);
    }

    public MonthSpend getMonthSpend(Long userId, Long monthYear) {

        return monthSpendRepository.findByUserIdAndMonthYear(userId, monthYear);
    }
}

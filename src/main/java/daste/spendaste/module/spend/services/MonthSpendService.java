package daste.spendaste.module.spend.services;

import daste.spendaste.module.spend.entities.MoneyTransaction;
import daste.spendaste.module.spend.models.MonthSpend;
import daste.spendaste.module.spend.repositories.MoneyTransactionRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MonthSpendService {
    private final MoneyTransactionRepository moneyTransactionRepository;

    public MonthSpendService(MoneyTransactionRepository moneyTransactionRepository) {
        this.moneyTransactionRepository = moneyTransactionRepository;
    }

    
    public MonthSpend calculateAndgetMonthSpend(Long userId, Integer monthYear) {
        List<MoneyTransaction> transactions = moneyTransactionRepository.findByUserIdAndYearMonth(userId, monthYear);
        MonthSpend monthSpend = new MonthSpend();
        transactions.stream().filter(MoneyTransaction::isSpending)
                .forEach(monthSpend::addSpending);

        return monthSpend;
    }
}

package daste.spendaste.module.spend.services;

import daste.spendaste.core.security.SecurityUtils;
import daste.spendaste.module.spend.entities.MoneyTransaction;
import daste.spendaste.module.spend.models.MonthSpend;
import daste.spendaste.module.spend.repositories.MoneyTransactionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MoneyTransactionService {

    private final MoneyTransactionRepository moneyTransactionRepository;

    public MoneyTransactionService(MoneyTransactionRepository moneyTransactionRepository) {
        this.moneyTransactionRepository = moneyTransactionRepository;
    }

    public MoneyTransaction create(MoneyTransaction moneyTransaction) {
        return moneyTransactionRepository.save(moneyTransaction);
    }

    public List<MoneyTransaction> getCurrentWeekTransaction(Integer weekYear) {
        Long userId = SecurityUtils.getCurrentLoginUserId();
        return moneyTransactionRepository.findByUserIdAndWeekYear(userId, weekYear);
    }

    public MonthSpend calculateAndgetMonthSpend(Long userId, Integer monthYear) {
        List<MoneyTransaction> transactions = moneyTransactionRepository.findByUserIdAndYearMonth(userId, monthYear);
        MonthSpend monthSpend = new MonthSpend();
        transactions.stream().filter(MoneyTransaction::isSpending)
                .forEach(monthSpend::addSpending);

        return monthSpend;
    }
}

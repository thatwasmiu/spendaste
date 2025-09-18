package daste.spendaste.module.spend.services;

import daste.spendaste.core.security.SecurityUtils;
import daste.spendaste.module.spend.entities.MoneyTransaction;
import daste.spendaste.module.spend.repositories.MoneyTransactionRepository;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
@Service
public class MoneyTransactionService {

    private final MoneyTransactionRepository moneyTransactionRepository;
    private final BalanceCalculatorService balanceCalculatorService;
    private final Long userId;
    public MoneyTransactionService(MoneyTransactionRepository moneyTransactionRepository, BalanceCalculatorService balanceCalculatorService) {
        this.balanceCalculatorService = balanceCalculatorService;
        this.moneyTransactionRepository = moneyTransactionRepository;
        this.userId = SecurityUtils.getCurrentLoginUserId();
    }

    public MoneyTransaction create(MoneyTransaction moneyTransaction) {
        return moneyTransactionRepository.save(moneyTransaction);
    }

    @Cacheable("weekTransaction")
    public List<MoneyTransaction> getCurrentWeekTransaction(Integer weekYear) {
        return moneyTransactionRepository.findByUserIdAndWeekYear(userId, weekYear);
    }


    @Transactional
    @CachePut("weekTransaction")
    public MoneyTransaction update(MoneyTransaction moneyTransaction) {
        MoneyTransaction transaction = moneyTransactionRepository.save(moneyTransaction);
        balanceCalculatorService.calculate(userId, moneyTransaction.getYearMonth());
        return transaction;
    }
}

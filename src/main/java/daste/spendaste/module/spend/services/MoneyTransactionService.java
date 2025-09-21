package daste.spendaste.module.spend.services;

import daste.spendaste.module.spend.entities.MoneyTransaction;
import daste.spendaste.module.spend.listener.BalanceCalculateEvent;
import daste.spendaste.module.spend.models.WeekSpend;
import daste.spendaste.module.spend.repositories.MoneyTransactionRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
@Service
public class MoneyTransactionService {

    private final MoneyTransactionRepository moneyTransactionRepository;
    private final BalanceCalculatorService balanceCalculatorService;
    private final ApplicationEventPublisher applicationEventPublisher;
    public MoneyTransactionService(MoneyTransactionRepository moneyTransactionRepository, BalanceCalculatorService balanceCalculatorService, ApplicationEventPublisher applicationEventPublisher) {
        this.balanceCalculatorService = balanceCalculatorService;
        this.moneyTransactionRepository = moneyTransactionRepository;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Transactional
    @CacheEvict(value = "weekSpend", keyGenerator = "tenantWeekSpendTransaction")
    public MoneyTransaction create(MoneyTransaction moneyTransaction) {
        MoneyTransaction transaction = moneyTransactionRepository.save(moneyTransaction);
        applicationEventPublisher.publishEvent(new BalanceCalculateEvent(transaction.getYearMonth()));
        System.out.println("Publisher thread: " + Thread.currentThread().getName());
        return transaction;
    }

    @Cacheable(value = "weekSpend", keyGenerator = "tenantWeekSpend")
    public WeekSpend getWeekSpend(Integer yearWeek) {
        List<MoneyTransaction> transactions = moneyTransactionRepository.findByYearWeek(yearWeek);

        return new WeekSpend(yearWeek, transactions);
    }

    @Transactional
    @CacheEvict(value = "weekSpend", allEntries = true)
    public MoneyTransaction update(MoneyTransaction moneyTransaction) {
        MoneyTransaction transaction = moneyTransactionRepository.save(moneyTransaction);
        applicationEventPublisher.publishEvent(new BalanceCalculateEvent(transaction.getYearMonth()));
        return transaction;
    }
}

package daste.spendaste.module.spend.services;

import daste.spendaste.core.security.SecurityUtils;
import daste.spendaste.module.spend.entities.MoneyTransaction;
import daste.spendaste.module.spend.entities.MonthBalance;
import daste.spendaste.module.spend.enums.TransactionType;
import daste.spendaste.module.spend.models.MonthBudget;
import daste.spendaste.module.spend.models.MonthReceive;
import daste.spendaste.module.spend.models.MonthSpend;
import daste.spendaste.module.spend.repositories.MoneyTransactionRepository;
import daste.spendaste.module.spend.repositories.MonthBalanceRepository;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class BalanceCalculatorService {
    private final MonthBalanceRepository monthBalanceRepository;
    private final MoneyTransactionRepository moneyTransactionRepository;

    public BalanceCalculatorService(MonthBalanceRepository monthBalanceRepository, MoneyTransactionRepository moneyTransactionRepository) {
        this.monthBalanceRepository = monthBalanceRepository;
        this.moneyTransactionRepository = moneyTransactionRepository;
    }

    @CachePut(value = "monthBalance", keyGenerator = "tenantMonthBalance")
    public MonthBalance calculate(Integer yearMonth) {
        MonthBalance balance = monthBalanceRepository.findByYearMonth(yearMonth)
                .orElse(initMonthBalance(yearMonth));
        initMonthSpend(balance);
        initMonthReceive(balance);
        if (Objects.nonNull(balance.getMonthBudget()) && balance.getMonthBudget().notAvailable()) {
            initMonthBudget(balance);
        }
        return monthBalanceRepository.save(balance);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @CachePut(value = "monthBalance", keyGenerator = "tenantMonthBalance")
    public MonthBalance calculateNewTransaction(Integer yearMonth) {
        return calculate(yearMonth);
    }

    @CachePut(value = "monthBalance", keyGenerator = "tenantMonthBalance")
    public MonthBalance createBalance(Integer yearMonth) {
        MonthBalance balance = initMonthBalance(yearMonth);
        initMonthBudget(balance);
        initMonthSpend(balance);
        initMonthReceive(balance);
        return monthBalanceRepository.save(balance);
    }

    public MonthBalance initMonthBalance(Integer monthYear) {
        MonthBalance monthBalance = new MonthBalance();
        monthBalance.setMonthYearUser(Long.valueOf(monthYear + SecurityUtils.getTenant()));
        monthBalance.setYearMonth(monthYear);
        monthBalance.setUserId(SecurityUtils.getUserId());
        return monthBalance;
    }

    public MonthBalance initMonthBudget(MonthBalance monthBalance) {
        int previousMonth = monthBalance.getYearMonthType().minusMonths(1).getMonthValue();
        Optional<MonthBalance> optionalMonthBalance = monthBalanceRepository.findByYearMonth(previousMonth);
        optionalMonthBalance.ifPresent(
                balance -> monthBalance.setMonthBudget(new MonthBudget(balance.getMonthSpend()))
        );
        return monthBalance;
    }

    public MonthBalance initMonthSpend(MonthBalance monthBalance) {
        MonthSpend monthSpend = calculateAndgetMonthSpend(monthBalance.getYearMonth());
        monthBalance.setMonthSpend(monthSpend);
        return monthBalance;
    }

    public MonthBalance initMonthReceive(MonthBalance monthBalance) {
        MonthReceive monthReceive = calculateAndgetMonthReceive(monthBalance.getYearMonth());
        monthBalance.setMonthReceive(monthReceive);
        return monthBalance;
    }

    private MonthReceive calculateAndgetMonthReceive(Integer yearMonth) {
        List<MoneyTransaction> transactions = moneyTransactionRepository.findByYearMonthAndTypeIn(yearMonth, TransactionType.receivingTypes());
        MonthReceive monthReceive = new MonthReceive();
        transactions.forEach(monthReceive::doTransaction);
        return monthReceive;
    }

    public MonthSpend calculateAndgetMonthSpend(Integer monthYear) {
        List<MoneyTransaction> transactions = moneyTransactionRepository.findByYearMonthAndTypeIn(monthYear, TransactionType.spendingTypes());
        MonthSpend monthSpend = new MonthSpend();
        transactions.forEach(monthSpend::doTransaction);
        return monthSpend;
    }
}

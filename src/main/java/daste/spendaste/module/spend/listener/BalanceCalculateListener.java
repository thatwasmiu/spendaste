package daste.spendaste.module.spend.listener;

import daste.spendaste.module.spend.services.BalanceCalculatorService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class BalanceCalculateListener {

    private final BalanceCalculatorService balanceCalculatorService;

    public BalanceCalculateListener(BalanceCalculatorService balanceCalculatorService) {
        this.balanceCalculatorService = balanceCalculatorService;
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void handle(BalanceCalculateEvent event) {
        System.out.println("Consumer thread: " + Thread.currentThread().getName());
        balanceCalculatorService.calculateNewTransaction(event.getYearMonth());
    }
}

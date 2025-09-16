package daste.spendaste.module.spend.endpoints;

import daste.spendaste.module.spend.entities.MoneyTransaction;
import daste.spendaste.module.spend.services.MoneyTransactionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("sp/api/money_transaction")
public class MoneyTransactionEndpoint {

    private final MoneyTransactionService moneyTransactionService;

    public MoneyTransactionEndpoint(MoneyTransactionService moneyTransactionService) {
        this.moneyTransactionService = moneyTransactionService;
    }

    @GetMapping("{weekYear}")
    public List<MoneyTransaction> getWeekSpend(@PathVariable(value = "weekYear") Integer weekYear) {
        Long userId = 0L;
        return moneyTransactionService.getCurrentWeekTransaction(userId, weekYear);
    }

    @PutMapping("create")
    public MoneyTransaction createMoneyTransaction(@RequestBody MoneyTransaction MoneyTransaction) {
        Long userId = 0L;
        return moneyTransactionService.createMoneyTransaction(MoneyTransaction);
    }
}

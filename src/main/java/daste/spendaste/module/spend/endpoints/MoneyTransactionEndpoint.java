package daste.spendaste.module.spend.endpoints;

import daste.spendaste.module.spend.entities.MoneyTransaction;
import daste.spendaste.module.spend.services.MoneyTransactionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("sp/api/aaa")
public class MoneyTransactionEndpoint {

    private final MoneyTransactionService moneyTransactionService;

    public MoneyTransactionEndpoint(MoneyTransactionService moneyTransactionService) {
        this.moneyTransactionService = moneyTransactionService;
    }

    @GetMapping("month_budget/{monthYear}")
    public List<MoneyTransaction> getMonthSpend(@PathVariable(value = "weekYear") Integer weekYear) {
        Long userId = 0L;
        return moneyTransactionService.getCurrentWeekTransaction(userId, weekYear);
    }
}

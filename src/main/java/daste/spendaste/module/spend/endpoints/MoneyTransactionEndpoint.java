package daste.spendaste.module.spend.endpoints;

import daste.spendaste.module.spend.entities.MoneyTransaction;
import daste.spendaste.module.spend.models.WeekSpend;
import daste.spendaste.module.spend.services.MoneyTransactionService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("sp/api/money-transaction")
public class MoneyTransactionEndpoint {

    private final MoneyTransactionService moneyTransactionService;

    public MoneyTransactionEndpoint(MoneyTransactionService moneyTransactionService) {
        this.moneyTransactionService = moneyTransactionService;
    }

    @GetMapping("hello")
    public String hello() {
        return "Hello " + SecurityContextHolder.getContext().getAuthentication().getName();
    }

    @PostMapping("create")
    public MoneyTransaction createMoneyTransaction(@RequestBody MoneyTransaction MoneyTransaction) {
        return moneyTransactionService.create(MoneyTransaction);
    }

    @GetMapping("{yearWeek}")
    public WeekSpend getWeekSpend(@PathVariable(value = "yearWeek") Integer yearWeek) {
        return moneyTransactionService.getWeekSpend(yearWeek);
    }

    @PutMapping("update")
    public MoneyTransaction update(@RequestBody MoneyTransaction MoneyTransaction) {
        return moneyTransactionService.update(MoneyTransaction);
    }
}

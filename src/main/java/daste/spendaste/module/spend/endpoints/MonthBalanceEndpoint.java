package daste.spendaste.module.spend.endpoints;

import daste.spendaste.module.spend.entities.MonthBalance;
import daste.spendaste.module.spend.models.MonthBudget;
import daste.spendaste.module.spend.services.MonthBalanceService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("sp/api/month-balance")
public class MonthBalanceEndpoint {
    private final MonthBalanceService monthBalanceService;

    public MonthBalanceEndpoint(MonthBalanceService monthBalanceService) {
        this.monthBalanceService = monthBalanceService;
    }

    @GetMapping("{yearMonth}")
    public MonthBalance getMonthBalance(@PathVariable(value = "yearMonth") Integer yearMonth) {
        return monthBalanceService.getOrCreateMonthBalance(yearMonth);
    }

    @GetMapping("calculate/{yearMonth}")
    public MonthBalance calculateMonthBalance(@PathVariable(value = "yearMonth") Integer yearMonth) {
        return monthBalanceService.calculateMonthBalance(yearMonth);
    }

    @PutMapping("budget/{yearMonth}")
    public MonthBalance updateMonthBudget(@PathVariable(value = "yearMonth") Integer yearMonth, @RequestBody MonthBudget budget) {
        return monthBalanceService.updateMonthBudget(yearMonth, budget);
    }

    @PostMapping("update")
    public MonthBalance update(@RequestBody MonthBalance monthBalance) {
        return monthBalanceService.updateMonthBalance(monthBalance);
    }
}

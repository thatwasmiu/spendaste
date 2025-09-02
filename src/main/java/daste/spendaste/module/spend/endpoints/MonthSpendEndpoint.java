package daste.spendaste.module.spend.endpoints;

import daste.spendaste.module.spend.entities.MonthBalance;
import daste.spendaste.module.spend.entities.MonthSpend;
import daste.spendaste.module.spend.services.MonthSpendService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("sp/api/month_spend")
public class MonthSpendEndpoint {
    private final MonthSpendService monthSpendService;

    public MonthSpendEndpoint(MonthSpendService monthSpendService) {
        this.monthSpendService = monthSpendService;
    }

    @PostMapping("create")
    public MonthSpend getMonthSpend(@RequestBody MonthSpend monthSpend) {

        return monthSpendService.createMonthSpend(monthSpend);
    }

    @GetMapping("{monthYear}")
    public void getMonthSpend(@PathVariable(value = "monthYear") Long monthYear) {
        Long userId = 0L;
        monthSpendService.getMonthSpend(userId, monthYear);
    }

    @GetMapping("calculate")
    public MonthSpend calculateMonthSpend(@PathVariable(value = "monthYear") Long monthYear) {
        Long userId = 0L;
        return monthSpendService.calculateMonthSpend(userId, monthYear);
    }
}

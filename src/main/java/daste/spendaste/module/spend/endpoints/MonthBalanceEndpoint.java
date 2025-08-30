package daste.spendaste.module.spend.endpoints;

import daste.spendaste.module.spend.entities.MonthBalance;
import daste.spendaste.module.spend.services.MonthBalanceService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("sp/api/aaa")
public class MonthBalanceEndpoint {
    private final MonthBalanceService monthBalanceService;

    public MonthBalanceEndpoint(MonthBalanceService monthBalanceService) {
        this.monthBalanceService = monthBalanceService;
    }

    @GetMapping("month_budget/{monthYear}")
    public MonthBalance getMonthSpend(@PathVariable(value = "monthYear") Long monthYear) {
        Long userId = 0L;
        return monthBalanceService.getMonthBalance(userId, monthYear);
    }
}

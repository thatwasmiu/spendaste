package daste.spendaste.module.spend.endpoints;

import daste.spendaste.module.spend.entities.MonthBalance;
import daste.spendaste.module.spend.services.MonthBalanceService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("sp/api/month_balance")
public class MonthBalanceEndpoint {
    private final MonthBalanceService monthBalanceService;

    public MonthBalanceEndpoint(MonthBalanceService monthBalanceService) {
        this.monthBalanceService = monthBalanceService;
    }

    @GetMapping("{yearMonth}")
    public MonthBalance getMonthBalance(@PathVariable(value = "yearMonth") Integer yearMonth) {
        return monthBalanceService.getMonthBalance(yearMonth);
    }

    @GetMapping("calculate/{yearMonth}")
    public MonthBalance calculateMonthBalance(@PathVariable(value = "yearMonth") Integer yearMonth) {
        return monthBalanceService.calculateMonthBalance(yearMonth);
    }

    @GetMapping("init")
    public void initMonthBalance(@RequestParam(value = "startMonth") Long yearMonth,
                                 @RequestParam(value = "numberOfMonth", defaultValue = "36") Long numberOfMonth
    ) {
        monthBalanceService.initMonthBalances(yearMonth, numberOfMonth);
    }
}

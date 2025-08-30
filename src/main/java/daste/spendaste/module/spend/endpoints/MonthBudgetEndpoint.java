package daste.spendaste.module.spend.endpoints;

import daste.spendaste.module.spend.services.MonthBudgetService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("sp/api/aaa")
public class MonthBudgetEndpoint {

    public final MonthBudgetService monthBudgetService;

    public MonthBudgetEndpoint(MonthBudgetService monthBudgetService) {
        this.monthBudgetService = monthBudgetService;
    }

    @GetMapping("month_budget/{monthYear}")
    public void getMonthSpend(@PathVariable(value = "monthYear") Long monthYear) {
        Long userId = 0L;
        monthBudgetService.getMonthBudget(userId, monthYear);
    }
}

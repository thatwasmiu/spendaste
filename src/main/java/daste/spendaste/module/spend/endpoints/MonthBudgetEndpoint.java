package daste.spendaste.module.spend.endpoints;

import daste.spendaste.module.spend.entities.MonthBudget;
import daste.spendaste.module.spend.services.MonthBudgetService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("sp/api/month_budget")
public class MonthBudgetEndpoint {

    public final MonthBudgetService monthBudgetService;

    public MonthBudgetEndpoint(MonthBudgetService monthBudgetService) {
        this.monthBudgetService = monthBudgetService;
    }

    @GetMapping("{monthYear}")
    public MonthBudget getMonthBudget(@PathVariable(value = "monthYear") Long monthYear) {
        Long userId = 0L;
        return monthBudgetService.getMonthBudget(userId, monthYear);
    }

    @PutMapping("update")
    public MonthBudget updateMonthBudget(@RequestBody MonthBudget monthBudget) {
        Long userId = 0L;
        return monthBudgetService.updateMonthBudget(monthBudget);
    }
}

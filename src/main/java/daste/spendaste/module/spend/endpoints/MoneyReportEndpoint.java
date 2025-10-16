package daste.spendaste.module.spend.endpoints;

import daste.spendaste.module.spend.models.MonthTransactionRatio;
import daste.spendaste.module.spend.models.WeekReport;
import daste.spendaste.module.spend.services.MoneyReportService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("sp/api/money-report")
public class MoneyReportEndpoint {

    private final MoneyReportService moneyReportService;

    public MoneyReportEndpoint(MoneyReportService moneyReportService) {
        this.moneyReportService = moneyReportService;
    }

    @GetMapping("weekly")
    public List<WeekReport> getWeekReport(@RequestParam("yearWeek") Integer yearWeek){
        return moneyReportService.get5WeekReport(yearWeek);
    }

    @GetMapping("ratio")
    public MonthTransactionRatio getTransactionRatio(@RequestParam("yearMonth") Integer yearMonth){
        return moneyReportService.getTransactionRatio(yearMonth);
    }
}

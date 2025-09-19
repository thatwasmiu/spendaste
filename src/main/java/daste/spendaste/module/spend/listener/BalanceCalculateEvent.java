package daste.spendaste.module.spend.listener;

public class BalanceCalculateEvent {
    private final Integer yearMonth;

    public BalanceCalculateEvent(Integer yearMonth) {
        this.yearMonth = yearMonth;
    }

    public Integer getYearMonth() {
        return yearMonth;
    }
}

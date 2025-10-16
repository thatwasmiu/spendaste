package daste.spendaste.module.spend.models;

import java.math.BigDecimal;

public interface MonthTransactionRatio {
    Integer getTotalCount();
    BigDecimal getSpendIncluded();
    BigDecimal getReceiveIncluded();
    Double getSpendToReceive();
    Double getReceiveToSpend();
}

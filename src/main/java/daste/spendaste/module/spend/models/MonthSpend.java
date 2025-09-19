package daste.spendaste.module.spend.models;

import daste.spendaste.module.spend.entities.MoneyTransaction;
import daste.spendaste.module.spend.enums.TransactionMethod;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.math.BigDecimal;

@Embeddable
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
public class MonthSpend implements Serializable {
    BigDecimal digitalSpend = BigDecimal.ZERO;
    BigDecimal cashSpend = BigDecimal.ZERO;
    BigDecimal lastMonthSpend = BigDecimal.ZERO;

    public void addSpending(MoneyTransaction transaction) {
        if (TransactionMethod.CASH.equals(transaction.getMethod())) {
            this.cashSpend = this.cashSpend.add(transaction.getAmount());
        } else {
            this.digitalSpend = this.digitalSpend.add(transaction.getAmount());
        }
    }

    public BigDecimal totalSpend() {
        return cashSpend.add(digitalSpend);
    }
}

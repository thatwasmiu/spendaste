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
public class MonthReceive  implements IMonthTransaction, Serializable {
    BigDecimal digitalReceive = BigDecimal.ZERO;

    BigDecimal cashReceive = BigDecimal.ZERO;

    private void addReceiving(MoneyTransaction transaction) {
        if (TransactionMethod.CASH.equals(transaction.getMethod())) {
            this.cashReceive = this.cashReceive.add(transaction.getAmount());
        } else {
            this.digitalReceive = this.digitalReceive.add(transaction.getAmount());
        }
    }

    public BigDecimal totalReceive() {
        return cashReceive.add(digitalReceive);
    }

    @Override
    public void doTransaction(MoneyTransaction transaction) {
        this.addReceiving(transaction);
    }
}

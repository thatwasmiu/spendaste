package daste.spendaste.module.spend.models;

import daste.spendaste.module.spend.entities.MoneyTransaction;
import daste.spendaste.module.spend.enums.TransactionMethod;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
public class DayTransaction implements Serializable {
    private Long date;
    private List<MoneyTransaction> transactions = new ArrayList<>(6);
    private BigDecimal cashSpend = BigDecimal.ZERO;
    private BigDecimal digitalSpend = BigDecimal.ZERO;
    private BigDecimal digitalReceive = BigDecimal.ZERO;
    private BigDecimal cashReceive = BigDecimal.ZERO;

    public DayTransaction(Long date) {
        this.date = date;
    }

    public void doTransaction(MoneyTransaction transaction) {
        transactions.add(transaction);
        if (transaction.isSpending()) {
            if (TransactionMethod.CASH.equals(transaction.getMethod())) {
                this.cashSpend = this.cashSpend.add(transaction.getAmount());
            } else {
                this.digitalSpend = this.digitalSpend.add(transaction.getAmount());
            }
        } else {
            if (transaction.getMethod().equals(TransactionMethod.CASH)) {
                this.cashReceive = this.cashReceive.add(transaction.getAmount());
            } else {
                this.digitalReceive = this.digitalReceive.add(transaction.getAmount());
            }
        }
    }
}

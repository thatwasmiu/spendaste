package daste.spendaste.module.spend.models;

import daste.spendaste.module.spend.entities.MoneyTransaction;
import daste.spendaste.module.spend.enums.TransactionMethod;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

public class DaySpend implements Serializable {
    private Long date;
    private List<MoneyTransaction> transactions = new ArrayList<>(6);
    private BigDecimal cashSpend = BigDecimal.ZERO;
    private BigDecimal digitalSpend = BigDecimal.ZERO;

    public DaySpend(Long date) {
        this.date = date;
    }

    public void addSpending(MoneyTransaction transaction) {
        transactions.add(transaction);
        if (TransactionMethod.CASH.equals(transaction.getMethod())) {
            this.cashSpend = this.cashSpend.add(transaction.getAmount());
        } else {
            this.digitalSpend = this.digitalSpend.add(transaction.getAmount());
        }
    }


    public Long getDate() {
        return date;
    }

    public List<MoneyTransaction> getTransactions() {
        return transactions;
    }

    public BigDecimal getCashSpend() {
        return cashSpend;
    }

    public BigDecimal getDigitalSpend() {
        return digitalSpend;
    }
}

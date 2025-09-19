package daste.spendaste.module.spend.models;

import daste.spendaste.module.spend.entities.MoneyTransaction;
import daste.spendaste.module.spend.enums.TransactionMethod;

import java.math.BigDecimal;
import java.util.List;

public class WeekSpend {
    private List<MoneyTransaction> transactions;
    private BigDecimal cashSpend = BigDecimal.ZERO;
    private BigDecimal digitalSpend = BigDecimal.ZERO;

    public WeekSpend(List<MoneyTransaction> transactions) {
        this.transactions = transactions;
        transactions.forEach(this::addSpending);
    }

    private void addSpending(MoneyTransaction transaction) {
        if (TransactionMethod.CASH.equals(transaction.getMethod())) {
            this.cashSpend = this.cashSpend.add(transaction.getAmount());
        } else {
            this.digitalSpend = this.digitalSpend.add(transaction.getAmount());
        }
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

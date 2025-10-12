package daste.spendaste.module.spend.models;

import daste.spendaste.module.spend.entities.MoneyTransaction;

public interface IMonthTransaction {
    void doTransaction(MoneyTransaction transaction);
}

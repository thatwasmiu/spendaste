package daste.spendaste.module.spend.enums;

import java.util.Arrays;
import java.util.List;

public enum TransactionType {

    OUTGOING_INCLUDED,   // counted as spending
    OUTGOING_EXCLUDED,   // outgoing but ignored (e.g. transfer to savings)
    OUTGOING_PENDING,    // outgoing but undecided
    INCOMING_INCLUDED,   // counted as income
    INCOMING_EXCLUDED,   // income but ignored (e.g. internal transfer)
    INCOMING_PENDING;    // incoming but undecided

    public static List<TransactionType> spendingTypes() {
        return Arrays.asList(OUTGOING_INCLUDED, OUTGOING_PENDING, OUTGOING_EXCLUDED);
    }

    public static List<TransactionType> receivingTypes() {
        return Arrays.asList(INCOMING_INCLUDED, INCOMING_PENDING, INCOMING_EXCLUDED);
    }
}

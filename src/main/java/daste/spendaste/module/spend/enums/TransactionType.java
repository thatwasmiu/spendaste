package daste.spendaste.module.spend.enums;

public enum TransactionType {
    OUTGOING_INCLUDED,   // counted as spending
    OUTGOING_EXCLUDED,   // outgoing but ignored (e.g. transfer to savings)
    OUTGOING_PENDING,    // outgoing but undecided
    INCOMING_INCLUDED,   // counted as income
    INCOMING_EXCLUDED,   // income but ignored (e.g. internal transfer)
    INCOMING_PENDING     // incoming but undecided
}

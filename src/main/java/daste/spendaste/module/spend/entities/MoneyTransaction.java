package daste.spendaste.module.spend.entities;

import daste.spendaste.core.model.BaseIdEntity;
import daste.spendaste.module.spend.enums.TransactionMethod;
import daste.spendaste.module.spend.enums.TransactionType;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;

@Entity
@Table(name = "sd_money_transaction")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Validated
public class MoneyTransaction extends BaseIdEntity {

    TransactionType type = TransactionType.INCOMING_PENDING;
    @NotNull
    Long userId;
    @NotNull
    TransactionMethod method;
    @NotNull
    Long date;
    Long weekYear;
    Integer yearMonth;
    String name;
    BigDecimal amount;
    Long categoryId;

    public Boolean isSpending() {
        return TransactionType.INCOMING_INCLUDED.equals(type) || TransactionType.INCOMING_PENDING.equals(type);
    }
}

package daste.spendaste.module.spend.entities;

import daste.spendaste.core.model.BaseIdEntity;
import daste.spendaste.core.model.IdEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "sd_money_transaction")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MoneyTransaction extends BaseIdEntity {
    Integer type;

    Long userId;

    Long weekYear;

    String name;

    String amount;

    Long date;

    String ddMmYyyy;

    Long categoryId;
}

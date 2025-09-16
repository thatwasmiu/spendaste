package daste.spendaste.module.spend.entities;

import daste.spendaste.core.model.IdEntity;
import daste.spendaste.module.spend.enums.TransactionType;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "sd_category")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Category extends IdEntity {
    String name;
    TransactionType type;
}

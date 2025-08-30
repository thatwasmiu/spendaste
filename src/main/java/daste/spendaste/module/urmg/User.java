package daste.spendaste.module.urmg;

import daste.spendaste.core.model.BaseIdEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "dst_user")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User extends BaseIdEntity {
    String username;

    String email;

    String password;
}

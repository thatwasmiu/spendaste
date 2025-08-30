package daste.spendaste.module.spend.repositories;

import daste.spendaste.module.spend.entities.PayableReceivable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PayableReceivableRepository extends JpaRepository<PayableReceivable, Long> {

}

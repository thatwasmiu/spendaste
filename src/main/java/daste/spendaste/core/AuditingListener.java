package daste.spendaste.core;

import daste.spendaste.core.model.BaseEntity;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

public class AuditingListener {
    @PrePersist
    public void setCreatedAt(BaseEntity entity) {
        long now = System.currentTimeMillis();
        entity.setCreated(now);
        entity.setUpdated(now);
    }

    @PreUpdate
    public void setUpdatedAt(BaseEntity entity) {
        entity.setUpdated(System.currentTimeMillis());
    }
}

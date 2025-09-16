package daste.spendaste.core.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import java.io.Serializable;

@MappedSuperclass
@JsonIgnoreProperties(ignoreUnknown = true)
//@EntityListeners(AuditingListener.class)
public class BaseEntity implements Serializable {
    private Long created;
    private Long updated;
    private String createdBy;
    private String updatedBy;

    @PrePersist
    public void onCreate() {
        long now = System.currentTimeMillis();
        this.created = now;
        this.updated = now;
    }

    @PreUpdate
    public void onUpdate() {
        this.updated = System.currentTimeMillis();
    }

    public BaseEntity() {}

    public Long getCreated() {
        return created;
    }

    public void setCreated(Long created) {
        this.created = created;
    }

    public Long getUpdated() {
        return updated;
    }

    public void setUpdated(Long updated) {
        this.updated = updated;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }
}

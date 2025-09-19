package daste.spendaste.core.model;

import daste.spendaste.core.security.SecurityUtils;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import org.hibernate.annotations.TenantId;

@MappedSuperclass
//@FilterDef(name = "tenantFilter", parameters = @ParamDef(name = "tenant", type = Long.class))
//@Filter(name = "tenantFilter", condition = "tenant = :tenant")
public class TenantEntity {
    @Column(nullable = false, updatable = false)
    @TenantId
    private String tenant;

    @PrePersist
    public void onCreate() {
        this.tenant = SecurityUtils.getTenant().toString();
    }

    public String getTenant() {
        return tenant;
    }

    public void setTenant(String tenant) {
        this.tenant = tenant;
    }
}

package daste.spendaste.core.model;

import daste.spendaste.core.security.SecurityUtils;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;

@MappedSuperclass
@FilterDef(name = "tenantFilter", parameters = @ParamDef(name = "tenant", type = Long.class))
@Filter(name = "tenantFilter", condition = "tenant = :tenant")
public class TenantEntity {
    @Column(nullable = false, updatable = false)
    private Long tenant;

    @PrePersist
    public void onCreate() {
        this.tenant = SecurityUtils.getTenant();
    }

    public Long getTenant() {
        return tenant;
    }

    public void setTenant(Long tenant) {
        this.tenant = tenant;
    }
}

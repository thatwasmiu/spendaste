package daste.spendaste.core;

import daste.spendaste.core.security.SecurityUtils;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class TenantResolver implements CurrentTenantIdentifierResolver, HibernatePropertiesCustomizer {
    @Override
    public String resolveCurrentTenantIdentifier() {
        String tenant = SecurityUtils.getTenant();
        if (tenant == null) {
            // fallback to default tenant ID
            return "-1";
        }
        return tenant.toString();
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return true;
    }

    @Override
    public void customize(Map<String, Object> hibernateProperties) {
        hibernateProperties.put(AvailableSettings.MULTI_TENANT_IDENTIFIER_RESOLVER, this);
    }
}

package daste.spendaste.config;

import daste.spendaste.core.TenantResolver;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.jpa.boot.spi.EntityManagerFactoryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

//@Configuration
public class HibernateConfig {

//    @Bean
//    public TenantResolver tenantResolver() {
//        return new TenantResolver();
//    }
//
//    @Bean
//    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
//            EntityManagerFactoryBuilder builder, DataSource dataSource, TenantResolver tenantResolver) {
//
//        Map<String, Object> jpaProperties = new HashMap<>();
//        jpaProperties.put(AvailableSettings.MULTI_TENANT, MultiTenancyStrategy.DISCRIMINATOR);
//        jpaProperties.put(AvailableSettings.MULTI_TENANT_IDENTIFIER_RESOLVER, tenantResolver);
//
//        return builder
//                .dataSource(dataSource)
//                .packages("daste.spendaste.core")
//                .properties(jpaProperties)
//                .build();
//    }
}

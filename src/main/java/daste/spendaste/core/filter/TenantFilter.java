package daste.spendaste.core.filter;

import daste.spendaste.core.exception.NoTenantException;
import daste.spendaste.core.security.SecurityUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.hibernate.Session;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class TenantFilter extends OncePerRequestFilter {

//    @PersistenceContext
//    private EntityManager entityManager;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        String rawValue = request.getHeader("X-Tenant-ID");
        if (rawValue != null) {
            Long tenant = Long.valueOf(rawValue);
            SecurityUtils.getCurrentLoginUser().setTenant(tenant);
//            Session session = entityManager.unwrap(Session.class);
//            session.enableFilter("tenantFilter")
//                    .setParameter("tenant", tenant);
//            System.out.println("Session identity: " + System.identityHashCode(session));
        }
        chain.doFilter(request, response);
    }
}
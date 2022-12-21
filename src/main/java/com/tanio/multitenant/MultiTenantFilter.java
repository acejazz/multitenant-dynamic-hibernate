package com.tanio.multitenant;

import jakarta.servlet.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Order(1)
class MultiTenantFilter implements Filter {

    int counter = 0;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {

        int tenantNumber = 1 + ((counter++) % 2);
        String tenantIndex = String.valueOf(tenantNumber);
        TenantContext.setCurrentTenant(tenantIndex);

        try {
            chain.doFilter(request, response);
        } finally {
            TenantContext.setCurrentTenant("");
        }
    }
}

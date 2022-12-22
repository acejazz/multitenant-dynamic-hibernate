package com.tanio.multitenant;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static com.tanio.multitenant.MultiTenantConfiguration.CUSTOMERS_DATASOURCE;
import static com.tanio.multitenant.MultiTenantConfiguration.INVENTORY_DATASOURCE;

@Component
@Order(1)
class MultiTenantFilter implements Filter {

    int counter = 0;

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpServletRequest =  (HttpServletRequest) servletRequest;
        String servletPath = httpServletRequest.getServletPath();
        if (servletPath.contains("customer")) {
            TenantContext.setCurrentTenant(CUSTOMERS_DATASOURCE);
        } else if (servletPath.contains("car")) {
            TenantContext.setCurrentTenant(INVENTORY_DATASOURCE);
        } else {
            throw new RuntimeException("Not possible to determine Tenant");
        }

        try {
            chain.doFilter(servletRequest, response);
        } finally {
            TenantContext.setCurrentTenant(null);
        }

    }
}

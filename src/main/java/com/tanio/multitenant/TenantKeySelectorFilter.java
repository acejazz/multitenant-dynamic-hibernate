package com.tanio.multitenant;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Order(1)
public class TenantKeySelectorFilter implements Filter {

    @Autowired
    private CurrentCombinedDatasource currentCombinedDatasource;

    @Autowired
    private CombinedDataSourceService combinedDataSourceService;

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain) throws ServletException, IOException {
        HttpServletRequest req = (HttpServletRequest) request;
        String tenantId = req.getHeader("X-TenantID");

        if (tenantId.equals("1")) {
            currentCombinedDatasource.set(combinedDataSourceService.getForTenantKey("1"));
        } else if (tenantId.equals("2")) {
            currentCombinedDatasource.set(combinedDataSourceService.getForTenantKey("2"));
        } else {
            throw new RuntimeException("Not possible to determine Tenant");
        }

        try {
            chain.doFilter(request, response);
        } finally {
            currentCombinedDatasource.reset();
        }
    }
}
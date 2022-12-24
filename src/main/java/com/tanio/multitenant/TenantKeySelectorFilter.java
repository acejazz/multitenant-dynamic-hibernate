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
    private CurrentTenantKey currentTenantKey;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpServletRequest =  (HttpServletRequest) request;
        String servletPath = httpServletRequest.getServletPath();

        if (servletPath.contains("customer")) {
            currentTenantKey.set("1");
        } else if (servletPath.contains("car")) {
            currentTenantKey.set("1");
        } else {
            throw new RuntimeException("Not possible to determine Tenant");
        }

        try {
            chain.doFilter(request, response);
        } finally {
            currentTenantKey.reset();
        }
    }
}
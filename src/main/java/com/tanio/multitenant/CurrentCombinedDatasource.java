package com.tanio.multitenant;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import static org.springframework.web.context.WebApplicationContext.SCOPE_REQUEST;

@Component
@Scope(scopeName = SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class CurrentCombinedDatasource {
    CombinedDataSource datasource;

    void set(CombinedDataSource datasource) {
        this.datasource = datasource;
    }

    public CombinedDataSource get() {
        return datasource;
    }
}
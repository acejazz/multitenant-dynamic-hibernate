package com.tanio.multitenant;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class MultiTenantDatasource extends AbstractRoutingDataSource {
    private final CurrentTenantKey currentTenantKey;

    public MultiTenantDatasource(CurrentTenantKey currentTenantKey) {
        this.currentTenantKey = currentTenantKey;
    }

    @Override
    protected Object determineCurrentLookupKey() {
        return currentTenantKey.get();
    }
}

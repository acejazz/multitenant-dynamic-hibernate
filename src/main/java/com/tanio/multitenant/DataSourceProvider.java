package com.tanio.multitenant;

import javax.sql.DataSource;

public interface DataSourceProvider {
    DataSource getForTenantKey(String tenantKey);
}
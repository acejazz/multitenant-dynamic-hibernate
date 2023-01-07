package com.tanio.multitenant;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class CombinedDataSourceService {
    private final Map<String, CombinedDataSource> combinedDataSources = new ConcurrentHashMap<>();

    CombinedDataSource getForTenantKey(String tenantKey) {
        return combinedDataSources.get(tenantKey);
    }

    void addCombinedDataSource(CombinedDataSource combinedDatasource, String tenantKey) {
        combinedDataSources.put(tenantKey, combinedDatasource);
    }

    Set<String> validKeys() {
        return combinedDataSources.keySet();
    }
}

package com.tanio.multitenant;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.function.Function;

public class DynamicDataSource extends AbstractRoutingDataSource {
    private final ThreadLocal<CombinedDataSource> dataSource;
    private final Function<CombinedDataSource, DataSource> extractDataSource;

    public DynamicDataSource(ThreadLocal<CombinedDataSource> dataSource, Function<CombinedDataSource, DataSource> extractDataSource) {
        this.dataSource = dataSource;
        this.extractDataSource = extractDataSource;
    }

    @Override
    public Object determineCurrentLookupKey() {
        throw new UnsupportedOperationException();
    }

    @Override
    public DataSource determineTargetDataSource() {
        return extractDataSource.apply(dataSource.get());
    }

    @Override
    public void afterPropertiesSet() {
    }
}

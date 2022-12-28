package com.tanio.multitenant;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.function.Function;
import java.util.function.Supplier;

public class DynamicDataSource extends AbstractRoutingDataSource {
    private final Supplier<CombinedDataSource> threadLocalCombinedDataSource;
    private final Function<CombinedDataSource, DataSource> extractDataSource;

    public DynamicDataSource(Supplier<CombinedDataSource> threadLocalCombinedDataSource,
                             Function<CombinedDataSource, DataSource> extractDataSource) {
        this.threadLocalCombinedDataSource = threadLocalCombinedDataSource;
        this.extractDataSource = extractDataSource;
    }

    @Override
    public Object determineCurrentLookupKey() {
        throw new UnsupportedOperationException();
    }

    @Override
    public DataSource determineTargetDataSource() {
        CombinedDataSource combinedDataSource = threadLocalCombinedDataSource.get();
        return extractDataSource.apply(combinedDataSource);
    }

    @Override
    public void afterPropertiesSet() {
    }
}

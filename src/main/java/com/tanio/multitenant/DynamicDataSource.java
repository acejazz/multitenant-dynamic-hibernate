package com.tanio.multitenant;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.function.Function;
import java.util.function.Supplier;

public abstract class DynamicDataSource extends AbstractRoutingDataSource {
    private final Supplier<CombinedDataSource> extractCombinedDataSourceFromRequest;
    private final Function<CombinedDataSource, DataSource> extractDataSourceFromCombined;

    public DynamicDataSource(Supplier<CombinedDataSource> extractCombinedDataSourceFromRequest,
                             Function<CombinedDataSource, DataSource> extractDataSourceFromCombined) {
        this.extractCombinedDataSourceFromRequest = extractCombinedDataSourceFromRequest;
        this.extractDataSourceFromCombined = extractDataSourceFromCombined;
    }

    @Override
    public Object determineCurrentLookupKey() {
        throw new UnsupportedOperationException();
    }

    @Override
    public DataSource determineTargetDataSource() {
        // Extract current combined data-source (both inventory and customers) from the request
        CombinedDataSource combinedDataSource = extractCombinedDataSourceFromRequest.get();

        // Extract the single data-source from the combined
        return extractDataSourceFromCombined.apply(combinedDataSource);
    }

    @Override
    public void afterPropertiesSet() {
    }
}

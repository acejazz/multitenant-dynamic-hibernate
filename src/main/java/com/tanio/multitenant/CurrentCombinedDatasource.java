package com.tanio.multitenant;

import org.springframework.stereotype.Component;

@Component
public class CurrentCombinedDatasource {
    ThreadLocal<CombinedDataSource> datasource = new ThreadLocal<>();

    void set(CombinedDataSource datasource) {
        this.datasource.set(datasource);
    }

    public ThreadLocal<CombinedDataSource> get() {
        return datasource;
    }

    void reset() {
        datasource.remove();
    }
}

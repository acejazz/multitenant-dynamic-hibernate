package com.tanio.multitenant;

import javax.sql.DataSource;

public record CombinedDataSource(DataSource customersDatasource,
                                 DataSource inventoryDatasource) {
}

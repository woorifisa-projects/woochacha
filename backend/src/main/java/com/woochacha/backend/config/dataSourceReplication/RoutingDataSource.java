package com.woochacha.backend.config.dataSourceReplication;

import com.woochacha.backend.common.DataSourceType;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.support.TransactionSynchronizationManager;

public class RoutingDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        return TransactionSynchronizationManager.isCurrentTransactionReadOnly()
                ? DataSourceType.SLAVE : DataSourceType.MASTER;
    }
}

package com.template.common;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class MultipleDataSource extends AbstractRoutingDataSource{
    

    protected Object determineCurrentLookupKey() {
        
        return DataSourceContextHolder.getDataSourceType();    
        
    }
    
    
}

package com.company.api.search;

public interface DataTable {
    DataEntity getEntity(int entity_id);
    int getEntityNumber();
    int getResultsNumber();
    int getPageResultsNumber();
    int getPageId();
}

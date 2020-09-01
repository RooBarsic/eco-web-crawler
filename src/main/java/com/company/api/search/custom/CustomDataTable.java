package com.company.api.search.custom;

import com.company.api.search.DataEntity;
import com.company.api.search.DataTable;

public class CustomDataTable implements DataTable {
    @Override
    public DataEntity getEntity(int entity_id) {
        return null;
    }

    @Override
    public int getEntityNumber() {
        return 0;
    }

    @Override
    public int getResultsNumber() {
        return 0;
    }

    @Override
    public int getPageResultsNumber() {
        return 0;
    }

    @Override
    public int getPageId() {
        return 0;
    }
}

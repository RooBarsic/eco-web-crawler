package com.company.api.search.custom;

import com.company.api.search.DataEntity;
import com.company.api.search.DataTable;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CustomDataTable implements DataTable {
    private final List<CustomDataEntity> dataEntityList;

    CustomDataTable(@NotNull final List<CustomDataEntity> dataEntities) {
        dataEntityList = new ArrayList<>();
        dataEntityList.addAll(dataEntities);
    }

    @Override
    public DataEntity getEntity(int entity_id) {
        if (entity_id < dataEntityList.size())
            return dataEntityList.get(entity_id);
        return null;
    }

    @Override
    public int getEntityNumber() {
        return dataEntityList.size();
    }

    @Override
    public int getResultsNumber() {
        return dataEntityList.size();
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

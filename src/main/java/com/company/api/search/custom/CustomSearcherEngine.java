package com.company.api.search.custom;

import com.company.api.search.DataTable;
import com.company.api.search.SearchEngine;
import org.jetbrains.annotations.NotNull;

public class CustomSearcherEngine implements SearchEngine {

    @Override
    public DataTable search(@NotNull String request) {

        return null;
    }

    @Override
    public String generateRequestUrl(@NotNull String request) {
        return null;
    }

    @Override
    public String getSearchEngineInfo() {
        return "Custom Web Crawler";
    }
}

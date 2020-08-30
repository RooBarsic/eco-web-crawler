package com.company.api.search;

import org.jetbrains.annotations.NotNull;

import java.net.URL;

public interface SearchEngine {
    DataTable search(@NotNull final String request);
    String generateRequestUrl(@NotNull final String request);
    String getSearchEngineInfo();
}

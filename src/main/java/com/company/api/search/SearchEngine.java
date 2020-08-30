package com.company.api.search;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface SearchEngine {
    DataTable search(@NotNull final String request);
    String generateRequestUrl(@NotNull final String request);
}

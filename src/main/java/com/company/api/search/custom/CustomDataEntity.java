package com.company.api.search.custom;

import com.company.api.search.DataEntity;
import org.jetbrains.annotations.NotNull;

public class CustomDataEntity implements DataEntity {
    private final String title;
    private final String overview;
    private final String url;

    CustomDataEntity(@NotNull final String title, @NotNull final String overview, @NotNull final String url) {
        this.title = title;
        this.overview = overview;
        this.url = url;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getOverview() {
        return overview;
    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return "{title: " + getTitle() + "} {overview: " + getOverview() + "} {url: " + getUrl() + "}";
    }
}

package com.company.api.open.handler;

import com.company.api.search.DataEntity;
import com.company.api.search.DataTable;
import com.company.api.search.SearchEngine;
import com.sun.net.httpserver.HttpExchange;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class SearchCustomHttpHandlerCommand implements CustomHttpHandlerCommand {
    public static final String API_PATH = "/api/search";

    @NotNull
    private final List<SearchEngine> searchEngineList;

    public SearchCustomHttpHandlerCommand(final @NotNull List<SearchEngine> searchEngineList) {
        this.searchEngineList = searchEngineList;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        System.out.println(" -------- got search request from web ");
        final StringBuilder responseBuilder = new StringBuilder();
        int responseCode = 405;

        if (exchange.getRequestMethod().equals("GET")) {

            Map<String, String> paramByKey = splitQuery(exchange.getRequestURI().getRawQuery());

            if (paramByKey.containsKey("q")) {
                responseCode = 200;
                final String query = paramByKey.get("q");

                SearchEngine searchEngine = searchEngineList.get(0);
                DataTable dataTable = searchEngine.search(query);
                for (int i = 1; i < searchEngineList.size(); i++) {
                    searchEngine = searchEngineList.get(i);
                    dataTable = searchEngine.search(query);
                    if (dataTable.getEntityNumber() > 1)
                        break;
                }

                for (int i = 0; i < dataTable.getEntityNumber(); i++) {
                    DataEntity dataEntity = dataTable.getEntity(i);
                    responseBuilder
                            .append("<h3>")
                            .append(dataEntity.getTitle())
                            .append("</h3>")
                            .append(dataEntity.getOverview())
                            .append("<br/>")
                            .append("<a href=\"")
                            .append(dataEntity.getUrl())
                            .append("\">")
                            .append(dataEntity.getUrl())
                            .append("</a>")
                            .append("<br/><br/>");
                }
            }
        }
        if (responseCode == 405) {
            responseBuilder.append("Wrong method usage. Use /help");
        }

        endResponse(exchange, responseBuilder.toString(), responseCode);
    }

}

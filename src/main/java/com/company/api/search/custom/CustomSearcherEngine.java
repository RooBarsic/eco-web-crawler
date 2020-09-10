package com.company.api.search.custom;

import com.company.api.search.DataTable;
import com.company.api.search.SearchEngine;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CustomSearcherEngine implements SearchEngine {

    @Override
    public DataTable search(@NotNull String request) {
        List<CustomDataEntity> customDataEntityList = new ArrayList<>();
        try {
            WebClient client = new WebClient();
            client.getOptions().setCssEnabled(false);
            client.getOptions().setJavaScriptEnabled(false);
            String productUrl = "https://www.google.com/search?q=" + generateUrlArgument(request);

            HtmlPage page = client.getPage(productUrl);
            System.out.println("title = " + page.getTitleText());

            System.out.println(" element = " + page.asText());

            List<String > rows = getRows(page.asText());

            int idx = 4;
            StringBuilder buffer = new StringBuilder();
            while(idx < rows.size() && !rows.get(idx).contains("http") && !rows.get(idx).contains("›")) {
                buffer.append(rows.get(idx)).append("\n");
                idx++;
            }

            String title = page.getTitleText();
            String description = parseDescription(buffer.toString());
            String url = productUrl;

            if (!(removeLastFormatters(title).equals("") &&
                    removeLastFormatters(description).equals("") &&
                    removeLastFormatters(url).equals(""))) {

                customDataEntityList.add(new CustomDataEntity(page.getTitleText(), buffer.toString(), productUrl));
            }

            while(idx < rows.size()) {
                if (rows.get(idx).contains("http") || rows.get(idx).contains("›")) {
                    title = parseTitle(rows.get(idx));
                    url = parseUrl(rows.get(idx));
                    description = parseDescription(rows.get(idx + 1));

                    if (!(removeLastFormatters(title).equals("") &&
                            removeLastFormatters(description).equals("") &&
                            removeLastFormatters(url).equals(""))) {

                        customDataEntityList.add(new CustomDataEntity(title, description, url));
                    }
                    idx++;
                }
                idx++;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return new CustomDataTable(customDataEntityList);
    }

    private String parseTitle(final String row) {
        if (row.contains("http")) {
            String[] buffer = row.split("http", 2);
            return buffer[0];
        }
        else {
            String[] buffer = row.split(" › ");
            buffer = buffer[0].split(" ");
            final String urlStartPath = buffer[buffer.length - 1];
            return row.split(urlStartPath, 3)[0];
        }
    }

    private String generateUrlArgument(final String arg) {
        final StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < arg.length(); i++) {
            if (arg.charAt(i) == ' ')
                stringBuilder.append('+');
            else
                stringBuilder.append(arg.charAt(i));
        }
        return stringBuilder.toString();
    }

    private String parseUrl(final String row) {
        System.out.println(" url parser ::: got url : " + row);
        //https://avatar.fandom.com › wiki › Азула
        //Harry Potter At Home - Wizarding World www.wizardingworld.com › collections › harry-potter-at-home
        String[] buffer;
        String[] buffer2;
        StringBuilder urlBuilder = new StringBuilder();
        if (row.contains("http")) {
            buffer = row.split("http", 2);
            if (buffer.length != 2) {
                return "-------";
            }
            buffer2 = buffer[1].split(" › ");
            if (row.contains("https"))
                urlBuilder.append("https");
            else
                urlBuilder.append("http");
        }
        else {
            buffer2 = row.split(" › ");
            buffer = buffer2[0].split(" ");
            buffer2[0] = buffer[buffer.length - 1];
            urlBuilder.append("http://");
        }

        for (int i = 0; i < buffer2.length; i++) {
            urlBuilder.append(removeLastFormatters(buffer2[i])).append("/");
        }
        urlBuilder.deleteCharAt(urlBuilder.length() - 1);

        System.out.println(" url parser ::: final url :" + removeLastFormatters(urlBuilder.toString()));
        return removeLastFormatters(urlBuilder.toString());
    }

    private String removeLastFormatters(final String str) {
        StringBuilder stringBuilder = new StringBuilder(str);
        int i = stringBuilder.length() - 1;
        while (i >= 0 && isCharFormatter(stringBuilder.charAt(i))) {
            stringBuilder.deleteCharAt(i);
            i--;
        }

        return stringBuilder.toString();
    }

    private boolean isCharFormatter(char e) {
        switch (e) {
            case '\n' :
            case '\t' :
            case '\r' :
                return true;
        }
        return false;
    }

    private String parseDescription(final String description) {
        StringBuilder descriptionBuilder = new StringBuilder();
        descriptionBuilder.append(description.charAt(0))
                .append(description.charAt(1));
        for(int i = 2; i < description.length(); i++) {
            descriptionBuilder.append(description.charAt(i));
            String stopCode = "" + description.charAt(i-2) + description.charAt(i-1) + description.charAt(i);
            if (stopCode.equals("...") || stopCode.equals("…") || description.charAt(i) == '…')
                break;
        }
        return descriptionBuilder.toString();
    }

    private List<String> getRows(String text) {
        List<String> rowsList = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < text.length(); i++) {
            char e = text.charAt(i);
            if (e == '\n') {
                rowsList.add(stringBuilder.toString());
                stringBuilder.delete(0, stringBuilder.length());
            }
            else {
                stringBuilder.append(e);
            }
        }
        return rowsList;
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

package com.company;

import com.company.api.search.DataEntity;
import com.company.api.search.DataTable;
import com.company.api.search.google.GoogleSearchEngine;

public class Main {

    public static void main(String[] args) {
        System.out.println(" Hello web. ");
        exampleGoogle();
    }

    public static void exampleGoogle() {
        String key = "AIzaSyCxPAxCwgtOfWbsew51B74O07VJKGkJRpY";
        GoogleSearchEngine googleSearchEngine = new GoogleSearchEngine(key);
        DataTable dataTable = googleSearchEngine.search("Moana");

        for(int i = 0; i < dataTable.getEntityNumber(); i++){
            DataEntity entity = dataTable.getEntity(i);
            System.out.println("i = " + i + "\n" +
                    "title = " + entity.getTitle() + "\n" +
                    "overview = " + entity.getOverview() + "\n" +
                    "url = " + entity.getUrl() + "\n");
        }

    }
}

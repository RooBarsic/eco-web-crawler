package com.company;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.openqa.selenium.TakesScreenshot;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;

public class Test {
    public static void run() throws IOException {

        WebClient client = new WebClient();
        client.getOptions().setCssEnabled(false);
        client.getOptions().setJavaScriptEnabled(false);
        String productUrl = "https://www.google.com/search?q=Azula";

        HtmlPage page = client.getPage(productUrl);
        System.out.println("title = " + page.getTitleText());
        HtmlElement productNode = ((HtmlElement) page
                .getFirstByXPath("//*[@itemtype='https://schema.org/Product']"));
        System.out.println(" element = " + page.asXml());
        URL imageUrl = new URL((((HtmlElement) productNode.getFirstByXPath("./img")))
                .getAttribute("src"));
        HtmlElement offers = ((HtmlElement) productNode.getFirstByXPath("./span[@itemprop='offers']"));

        BigDecimal price = new BigDecimal(((HtmlElement) offers.getFirstByXPath("./span[@itemprop='price']")).asText());
        String productName = (((HtmlElement) productNode.getFirstByXPath("./span[@itemprop='name']")).asText());
        String currency = (((HtmlElement) offers.getFirstByXPath("./*[@itemprop='priceCurrency']")).getAttribute("content"));
        String productSKU = (((HtmlElement) productNode.getFirstByXPath("./span[@itemprop='sku']")).asText());

        Product product = new Product(price, productName, productSKU, imageUrl, currency);
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(product) ;
        System.out.println(jsonString);
    }
}

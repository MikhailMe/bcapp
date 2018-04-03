package Oracles;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class MyScoreOracle {

    public static void main(String[] args) throws IOException {

        Document document = Jsoup.connect("https://www.myscore.ru/football/russia/premier-league/").get();
        Elements body = document.select("#tournament-page-data-summary-results");
        System.out.println(body);

    }

}

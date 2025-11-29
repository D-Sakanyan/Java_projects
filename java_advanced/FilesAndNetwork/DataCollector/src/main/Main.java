package main;

import file_proccessing.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.List;

public class Main {
    static Document document;

    static {
        try {
            document = Jsoup.connect("https://skillbox-java.github.io/").get();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static Element pathMetrodata = document.select(
            "div.b-vd-metro-wrapper " +
                    "div.b-vd-metro " +
                    "div.b-metro-stations-by-lines.t-metro-section.js-metro-section " +
                    "div.content-wrapper"
    ).first();
    static Element header = pathMetrodata.select("h2").last();
    static Element metrodata = header.select("div#metrodata " + "div.t-text-simple").first();

    public static void main(String[] args) {
        ParseMetroLines parseMetroLines = new ParseMetroLines();
        List<MetroLines> lines = parseMetroLines.collectLines();
        ParseMetroStations parseMetroStations = new ParseMetroStations();
        List<MetroStations> stations = parseMetroStations.collectStations();
        ParseJson parseJson = new ParseJson();
        List<JsonObject> jsonObject = parseJson.jsonParse();
        ParseCsv parseCsv = new ParseCsv();
        List<CsvObject> csvObject = parseCsv.csvParse();
        MetroData metroData = new MetroData();
        metroData.metroData(lines, stations);
        StationsProperties properties = new StationsProperties();
        properties.stationsProperties(jsonObject, csvObject, stations, lines);
    }
}

package main;

import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class ParseMetroLines {
    public static List<MetroLines> collectLines() {
        List<MetroLines> lines = new ArrayList<>();
        Elements metroLine = Main.metrodata
                .select("div[class^=js-toggle-depend] " +
                        "span[class^=js-metro-line]");
        metroLine
                .forEach(lineElem -> {
                    String numLine = lineElem.attr("data-line");
                    String nameLine = lineElem.text();
                    lines.add(new MetroLines(numLine, nameLine));
                });
        return lines;
    }
}

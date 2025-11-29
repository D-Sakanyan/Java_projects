package main;

import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class ParseMetroStations {
    public static List<MetroStations> collectStations() {
        List<MetroStations> stations = new ArrayList<>();

        Elements metroStation = Main.metrodata
                .select("div[class^=js-depend] " +
                        "div[class^=js-metro-stations] " +
                        "p.single-station");
        metroStation.stream()
                .forEach(metrostations -> {
                    String numStation = metrostations.select("span.num").text();
                    String nameStation = metrostations.select("span.name").text();
                    String lineNum = metrostations.parent().attr("data-line");
                    MetroStations stationsOfMetro = new MetroStations(numStation, nameStation, lineNum);

                    Elements connections = metrostations
                            .select("span[class^=t-icon-metroln]");
                    connections.forEach(conn -> {
                        String className = conn.className();
                        String lineNumber = className
                                .replaceAll(".*t-icon-metroln[\\s_]ln-(\\S+).*", "$1");
                        String name = conn.attr("title");
                        stationsOfMetro.setConnections(new Connections(name, lineNumber));
                    });
                    stations.add(stationsOfMetro);
                });
        return stations;
    }
}

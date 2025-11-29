package main;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class MetroData {
    public static void metroData(List<MetroLines> lines, List<MetroStations> stations) {
        Map<String, Object> result = new LinkedHashMap<>();
        Map<String, List<String>> stationsByLines = new LinkedHashMap<>();

        lines.forEach(line -> {
            List<String> stationsLine = stations.stream()
                    .filter(station ->
                            line.getNumLine()
                                    .equals(station.getLineNum()))
                    .map(MetroStations::getName).collect(Collectors.toList());
            stationsByLines.put(line.getNumLine(), stationsLine);
        });
        result.put("Stations", stationsByLines);

        List<List<Map<String, Object>>> connectionsMap = new ArrayList<>();
        stations.forEach(station -> {
            List<Connections> connections = station.getConnections();
            connections.forEach(conn -> {
                List<Map<String, Object>> pair = new ArrayList<>();
                Map<String, Object> stat1 = Map.of(
                        "line", station.getLineNum(),
                        "station", station.getName()
                );
                Map<String, Object> stat2 = Map.of(
                        "line", conn.getLine(),
                        "station", conn.getStationName()
                );
                pair.add(stat1);
                pair.add(stat2);
                connectionsMap.add(pair);
            });
        });
        result.put("Connections", connectionsMap);

        List<Map<String, Object>> linesJson = new ArrayList<>();
        lines.forEach(line -> {
            Map<String, Object> lineMap = new HashMap<>();
            lineMap.put("number", line.getNumLine());
            lineMap.put("name", line.getNameLine());
            linesJson.add(lineMap);
        });
        result.put("lines", linesJson);

        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        try {
            mapper.writeValue(new File("C:\\" +
                    "SkillBox\\" + "java_core_advanced\\" +
                    "FilesAndNetwork\\DataCollector\\data\\map.json"), result);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

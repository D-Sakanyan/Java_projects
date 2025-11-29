package main;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import file_proccessing.CsvObject;
import file_proccessing.JsonObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class StationsProperties {
    public static void stationsProperties(List<JsonObject> name_depth, List<CsvObject> name_date, List<MetroStations> stations, List<MetroLines> lines) {
        Map<String, Object> result = new LinkedHashMap<>();
        List<Map<String, Object>> properties = new ArrayList<>();
        stations.forEach(station -> {
            String name = station.getName();
            String lineNum = station.getLineNum();
            String lineName = lines.stream()
                    .filter(line -> line.getNumLine().equals(lineNum))
                    .map(MetroLines::getNameLine)
                    .findFirst()
                    .orElse("");
            Boolean hasConnection = !station.getConnections().isEmpty();
            String date = name_date.stream()
                    .filter(csv -> csv.getName().trim().equalsIgnoreCase(name.trim()))
                    .map(CsvObject::getDate)
                    .findFirst()
                    .orElse("");
            String depth = name_depth.stream()
                    .filter(json -> json.getStation_name().trim().equalsIgnoreCase(name.trim()))
                    .map(JsonObject::getDepth)
                    .findFirst()
                    .orElse("");
            Map<String, Object> putMap = new LinkedHashMap<>();
            putMap.put("name", name);
            putMap.put("line", lineName);
            putMap.put("date", date);
            putMap.put("depth", depth);
            putMap.put("hasConnection", hasConnection);
            properties.add(putMap);
        });
        result.put("Stations", properties);

        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        try {
            mapper.writeValue(new File("C:\\" +
                    "SkillBox\\" + "java_core_advanced\\" +
                    "FilesAndNetwork\\DataCollector\\data\\stations.json"), result);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

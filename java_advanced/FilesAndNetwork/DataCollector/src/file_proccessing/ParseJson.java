package file_proccessing;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileReader;
import java.util.*;

public class ParseJson {
    public static List<JsonObject> jsonParse() {
        ObjectMapper mapper = new ObjectMapper();
        List<JsonObject> jsonObjectList = new ArrayList<>();
        try {
            String[] files = {
                    "C:\\SkillBox\\java_core_advanced\\FilesAndNetwork\\DataCollector\\data\\stations-data\\data\\2\\4\\depths-1.json",
                    "C:\\SkillBox\\java_core_advanced\\FilesAndNetwork\\DataCollector\\data\\stations-data\\data\\7\\1\\depths-2.json",
                    "C:\\SkillBox\\java_core_advanced\\FilesAndNetwork\\DataCollector\\data\\stations-data\\data\\4\\6\\depths-3.json"
            };
            for (String file : files) {
                List<JsonObject> jsonObjects = mapper.readValue(
                        new FileReader(file),
                        new TypeReference<List<JsonObject>>() {
                        }
                );
                jsonObjectList.addAll(jsonObjects);
            }
        } catch (
                Exception e) {
            e.printStackTrace();
        }
        return jsonObjectList;
    }
}
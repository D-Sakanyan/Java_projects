package file_proccessing;

import com.opencsv.bean.CsvToBeanBuilder;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class ParseCsv {
    public static List<CsvObject> csvParse() {
        List<CsvObject> csvObjectList = new ArrayList<>();
        try {
            String[] files = {
                    "C:\\SkillBox\\java_core_advanced\\FilesAndNetwork\\DataCollector\\data\\stations-data\\data\\4\\6\\dates-1.csv",
                    "C:\\SkillBox\\java_core_advanced\\FilesAndNetwork\\DataCollector\\data\\stations-data\\data\\0\\5\\dates-2.csv",
                    "C:\\SkillBox\\java_core_advanced\\FilesAndNetwork\\DataCollector\\data\\stations-data\\data\\9\\6\\dates-3.csv"
            };
            for (String file : files) {
                List<CsvObject> csvObjects = new CsvToBeanBuilder<CsvObject>(
                        new FileReader(file))
                        .withType(CsvObject.class)
                        .build().parse();
                csvObjectList.addAll(csvObjects);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return csvObjectList;
    }
}

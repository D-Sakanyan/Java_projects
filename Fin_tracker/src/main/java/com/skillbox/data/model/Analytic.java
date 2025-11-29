package com.skillbox.data.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.skillbox.controller.dto.ApplicationFiles;
import com.skillbox.data.repository.AnalyticRepository;
import lombok.Setter;

import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

/**
 * Класс, хранящий результаты расчета аналитики транзакций
 */
@Setter
public class Analytic implements AnalyticRepository {
    private LocalDateTime now = LocalDateTime.now();
    private String groupName;
    private String aggregateOptionName;
    private String filterDescription;
    private Map<?, BigDecimal> aggregation;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        sb.append("===================================\n");
        sb.append("Дата: ").append(now.format(formatter)).append("\n");
        sb.append("Имя: ").append(groupName)
                .append(" (").append(aggregateOptionName).append(")\n");
        sb.append("Фильтр: ").append(filterDescription).append("\n");
        sb.append("-----------------------------------\n");
        sb.append("Аналитика:\n");
        if (aggregation != null) {
            aggregation.forEach((key, value) -> sb.append(String.format("%s: %.2f%n", key, value)));
        }
        return sb.toString();
    }

    @Override
    public void save(Analytic analytic) {
        ApplicationFiles applicationFiles = new ApplicationFiles();
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .setPrettyPrinting()
                .create();

        try (FileWriter writer = new FileWriter(applicationFiles.getOutputFilename())) {
            gson.toJson(analytic, writer);
            System.out.println("Вы успешно сохранили аналитику!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
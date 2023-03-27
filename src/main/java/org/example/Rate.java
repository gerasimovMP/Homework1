package org.example;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Rate {
    private final Path path;
    private float average = 0;
    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("0.00");
    private static final int DAYS_A_WEEK = 7;

    public Rate(String path) {
        this.path = Paths.get(path);
    }

    public void rateValues(String typeOFRate) {
        switch (typeOFRate) {
            case Constants.TOMORROW -> rateAverageForTomorrow();
            case Constants.WEEK -> rateAverageForWeek();
            default -> throw new RuntimeException("Ошибка");
        }
    }

    private String getLastDateFromFile() {
        String lastDate = null;
        try (Stream<String> csvStream = Files.lines(path)) {
            lastDate = csvStream
                    .skip(1)
                    .findFirst()
                    .map(x -> x.split(";")[1]).orElse(null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lastDate;
    }

    private List<Float> getValues() {
        List<Float> values = new ArrayList<>();
        try (Stream<String> csvStream = Files.lines(path)) {
            values = csvStream.skip(1)
                    .limit(DAYS_A_WEEK)
                    .map(x -> Float.parseFloat(x.split(";")[2].replace("\"", "").replace(",", ".")))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return values;
    }

    private String getNewDate(int plusDays) {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        final DateTimeFormatter newDateFormatter = DateTimeFormatter.ofPattern("E dd.MM.yyyy");
        LocalDate date = LocalDate.parse(getLastDateFromFile(), formatter).plusDays(plusDays);
        return StringUtils.capitalize(date.format(newDateFormatter));
    }

    private void rateAverageForTomorrow() {
        List<Float> values = getValues();
        for (Float value : values) {
            average += value;
        }
        System.out.println(getNewDate(1) + " - " + DECIMAL_FORMAT.format(average / DAYS_A_WEEK));
    }

    private void rateAverageForWeek() {
        List<Float> values = getValues();
        for (int index = 0; index < DAYS_A_WEEK; index++) {
            average = 0;
            for (Float value : values) {
                average += value;
            }
            values.remove(values.size() - 1);
            values.add(0, (float) (Math.round((average / DAYS_A_WEEK) * 10000.0) / 10000.0));
            System.out.println(getNewDate(index + 1) + " - " + DECIMAL_FORMAT.format(average / DAYS_A_WEEK));
        }
    }
}

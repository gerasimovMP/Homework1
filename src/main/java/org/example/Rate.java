package org.example;
import org.apache.commons.lang3.StringUtils;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class Rate {
    private Path path;
    private float average = 0;
    private LocalDate date;
    private final DecimalFormat decfor = new DecimalFormat("0.00");
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private final DateTimeFormatter newDateFormatter = DateTimeFormatter.ofPattern("E dd.MM.yyyy");

    public Rate(String path) {
        this.path = Paths.get(path);
    }

    private String getLastDateFromFile() throws IOException {
        return Files
                .lines(path)
                .skip(1)
                .findFirst()
                .map(x -> x.split(";")[1]).orElse(null).replace("\"", "");
    }

    private List<Float> getValues() throws IOException {
        return Files
                .lines(path)
                .skip(1)
                .limit(7)
                .map(x -> Float.parseFloat(x.split(";")[2].replace("\"", "").replace(",", ".")))
                .collect(Collectors.toList());
    }

    private String getNewDate(int plusDays) throws IOException {
        date = LocalDate.parse(getLastDateFromFile(), formatter).plusDays(plusDays);
        return StringUtils.capitalize(date.format(newDateFormatter));
    }

    public void rateAverageForTomorrow() throws IOException {
        List<Float> values = getValues();
        for (Float value : values) {
            average += value;
        }
        System.out.println(getNewDate(1) + " - " + decfor.format(average / 7));
    }

    public void rateAverageForWeek() throws IOException {
        List<Float> values = getValues();
        for (int index = 0; index < 7; index++) {
            average = 0;
            for (Float value : values) {
                average += value;
            }
            values.remove(values.size() - 1);
            values.add(0, (float) (Math.round((average / 7) * 10000.0) / 10000.0));
            System.out.println(getNewDate(index + 1) + " - " + decfor.format(average / 7));
        }
    }
}
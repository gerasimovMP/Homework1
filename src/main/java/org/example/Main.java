package org.example;

import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) throws IOException {
        Pattern pattern = Pattern.compile("rate [A-Z]{3} (week|tomorrow)");
        Scanner sc = new Scanner(System.in);
        String path;
        String typeOfRate;
        String userString;
        Rate rate;

        System.out.print("Введи строку: ");
        userString = sc.nextLine();
        if (!pattern.matcher(userString).matches()) {
            throw new IOException("Введена некорректная строка");
        }
        path = userString.split(" ")[1] + ".csv";
        typeOfRate = userString.split(" ")[2];
        rate = new Rate(path);

        switch (typeOfRate){
            case "tomorrow":
                rate.rateAverageForTomorrow();
                break;
            case "week" :
                rate.rateAverageForWeek();
                break;
            default:
                throw new IOException("Ошибка");
        }
    }
}
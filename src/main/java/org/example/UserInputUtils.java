package org.example;

import java.util.Scanner;
import java.util.regex.Pattern;

public class UserInputUtils {
    public String getUserInput() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Введи строку: ");
        String inputStr = sc.nextLine();
        checkUserInput(inputStr);
        return inputStr;
    }

    public String getPath(String userInput) {
        return userInput.split(" ")[1] + ".csv";
    }

    public String getTypeOfRate(String userInput) {
        return userInput.split(" ")[2];
    }

    private void checkUserInput(String userInput) {
        final Pattern pattern = Pattern.compile("rate [A-Z]{3} (" + Constants.WEEK + "|" + Constants.TOMORROW + ")");
        if (!pattern.matcher(userInput).matches()) {
            throw new RuntimeException("Введена некорректная строка");
        }
    }
}
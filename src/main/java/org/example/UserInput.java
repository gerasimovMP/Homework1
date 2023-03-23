package org.example;

import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class UserInput {
    private final Pattern pattern = Pattern.compile("rate [A-Z]{3} (week|tomorrow)");
    private final Scanner sc = new Scanner(System.in);

    protected String getUserInput() throws IOException {
        System.out.print("Введи строку: ");
        String inputStr = sc.nextLine();
        checkUserInput(inputStr);
        return inputStr;
    }

    protected String getPath(String userInput) {
        return userInput.split(" ")[1] + ".csv";
    }

    protected String getTypeOfRate(String userInput) {
        return userInput.split(" ")[2];
    }

    private void checkUserInput(String userInput) throws IOException {
        if (!pattern.matcher(userInput).matches()) {
            throw new IOException("Введена некорректная строка");
        }
    }

}

package org.example;

import java.util.regex.Pattern;

public class UserInputUtils {
    public Boolean checkUserInput(String userInput) {
        boolean stringIsOk = false;
        final Pattern pattern = Pattern.compile("rate [A-Z]{3} (" + Constants.WEEK + "|" + Constants.TOMORROW + ") -alg");
        if (pattern.matcher(userInput).matches()) {
            stringIsOk = true;
        }
        return stringIsOk;
    }

    public String getPath(String userInput) {
        checkUserInput(userInput);
        return userInput.split(" ")[1] + ".csv";
    }

    public String getTypeOfRate(String userInput) {
        checkUserInput(userInput);
        return userInput.split(" ")[2];
    }
}
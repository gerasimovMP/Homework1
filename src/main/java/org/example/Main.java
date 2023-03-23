package org.example;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        UserInput userInput = new UserInput();
        String userString = userInput.getUserInput();
        Rate rate = new Rate(userInput.getPath(userString));
        rate.rateValues(userInput.getTypeOfRate(userString));
    }
}
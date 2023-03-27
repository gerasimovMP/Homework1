package org.example;


public class Main {
    public static void main(String[] args) {
        UserInputUtils userInputUtils = new UserInputUtils();
        String userString = userInputUtils.getUserInput();
        Rate rate = new Rate(userInputUtils.getPath(userString));
        rate.rateValues(userInputUtils.getTypeOfRate(userString));
    }
}

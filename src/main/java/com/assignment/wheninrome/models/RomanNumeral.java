package com.assignment.wheninrome.models;

/**
 * Models the response values for converting an integer into a Roman numeral.
 */
public class RomanNumeral {

    private final String input; // Per the specifications, the JSON response "input" value should be a String
    private final String output;

    public RomanNumeral(int input, String output) {
        this.input = input + "";
        this.output = output;
    }

    public String getInput() {
        return input;
    }

    public String getOutput() {
        return output;
    }
}
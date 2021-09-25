package com.assignment.wheninrome;

import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * Service responsible for performing logical operations related to Roman numerals.
 */
@Service
public class RomanNumeralService {

    private static final String[] onesPlaceRomanString = {"","I","II","III","IV","V","VI","VII","VIII","IX"};
    private static final String[] tensPlaceRomanString = { "","X","XX","XXX","XL","L","LX","LXX","LXXX","XC"};
    private static final String[] hundredsPlaceRomanString = { "","C","CC","CCC","CD","D","DC","DCC","DCCC","CM"};
    private static final String[] thousandsPlaceRomanString = {"","M","MM","MMM"};
    private static HashMap<Integer, String[]> romanNumeralChars = new HashMap<>();

    public RomanNumeralService() {
        romanNumeralChars.put(1, onesPlaceRomanString);
        romanNumeralChars.put(10, tensPlaceRomanString);
        romanNumeralChars.put(100, hundredsPlaceRomanString);
        romanNumeralChars.put(1000, thousandsPlaceRomanString);
    }

    /**
     * Converts an integer value between 1 and 3999 into its Roman numeral representation.
     *
     * @param inputValue - an integer between 1-3999.
     * @return Roman numeral.
     * @throws IllegalArgumentException - Thrown if integer value is outside the range of 1-3999.
     */
    public String convertToRomanNumeral(int inputValue) throws IllegalArgumentException {

        if (inputValue < 1 || inputValue > 3999) {
            throw new IllegalArgumentException("Only integer values between 1 and 3999 can be " +
                    "converted to Roman numerals.");
        }

        StringBuilder romanNumeral = new StringBuilder();

        // placeValue represents thousands, hundreds, tens, and ones places.
        for (int placeValue = 1000; placeValue > 0; placeValue /= 10) {
            int valueOfCurrentPlace = inputValue / placeValue;
            String currentPlaceValueInRoman = romanNumeralChars.get(placeValue)[valueOfCurrentPlace];
            romanNumeral.append(currentPlaceValueInRoman);
            inputValue -= valueOfCurrentPlace * placeValue;
        }

        return romanNumeral.toString();
    }

}

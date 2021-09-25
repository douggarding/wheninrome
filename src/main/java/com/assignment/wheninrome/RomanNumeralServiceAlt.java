package com.assignment.wheninrome;

import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * Service responsible for performing logical operations related to Roman numerals.
 */
@Service
public class RomanNumeralServiceAlt {

    private static final Character[] onesPlaceRomanChars = {'I', 'V', 'X'};
    private static final Character[] tensPlaceRomanChars = {'X', 'L', 'C'};
    private static final Character[] hundredsPlaceRomanChars = {'C', 'D', 'M'};
    private static final Character[] thousandsPlaceRomanChars = {'M', Character.MIN_VALUE, Character.MIN_VALUE};
    private static HashMap<Integer, Character[]> romanNumeralChars = new HashMap<>();

    public RomanNumeralServiceAlt() {
        romanNumeralChars.put(1, onesPlaceRomanChars);
        romanNumeralChars.put(10, tensPlaceRomanChars);
        romanNumeralChars.put(100, hundredsPlaceRomanChars);
        romanNumeralChars.put(1000, thousandsPlaceRomanChars);
    }

    /**
     * Converts an integer value between 1 and 3999 into its Roman numeral representation.
     *
     * @param inputValue - an integer between 1-3999.
     * @return Roman numeral.
     */
    public String convertToRomanNumeral(int inputValue) {
        // TODO: throw exception for anything higher than 3999 or less than 1.

        String romanValue = "";

        // placeValue represents thousands, hundreds, tens, and ones places.
        for (int placeValue = 1000; placeValue > 0; placeValue /= 10) {
            int valueOfCurrentPlace = inputValue / placeValue;
            romanValue += calculateValue(placeValue, valueOfCurrentPlace);
            inputValue -= valueOfCurrentPlace * placeValue;
        }

        return romanValue;
    }

    /**
     * Helper method that calculates a number to its Roman numeral place value.
     *
     * @param placeValue - The place value that's being evaluated. Acceptable values rae 1000, 100, 10, or 1.
     * @param valueOfCurrentPlace - The value of the current place. For example if the number is 25, and we're
     *                            evaluating the tens place, then this value would be "2".
     * @return - The current place's value as a Roman numeral.
     */
    private String calculateValue(int placeValue, int valueOfCurrentPlace) {

        Character[] chars = this.romanNumeralChars.get(placeValue);
        char romanOneValue = chars[0];
        char romanFiveValue = chars[1];
        char romanTenValue = chars[2];

        switch(valueOfCurrentPlace) {
            case 1:
                return "" + romanOneValue;
            case 2:
                return "" + romanOneValue + romanOneValue;
            case 3:
                return "" + romanOneValue + romanOneValue + romanOneValue;
            case 4:
                return "" + romanOneValue + romanFiveValue;
            case 5:
                return "" + romanFiveValue;
            case 6:
                return "" + romanFiveValue + romanOneValue;
            case 7:
                return "" + romanFiveValue + romanOneValue + romanOneValue;
            case 8:
                return "" + romanFiveValue + romanOneValue + romanOneValue + romanOneValue;
            case 9:
                return "" + romanOneValue + romanTenValue;
            default:
                return ""; // Zero in Roman Numerals has no representation.
        }
    }

}

package com.assignment.wheninrome;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

class RomanNumeralServiceTest {

    RomanNumeralService romanNumeralService = new RomanNumeralService();

    @Test
    void convertToRomanNumeral_OnesValues() {
        assertEquals("I", romanNumeralService.convertToRomanNumeral(1));
        assertEquals("II", romanNumeralService.convertToRomanNumeral(2));
        assertEquals("III", romanNumeralService.convertToRomanNumeral(3));
        assertEquals("IV", romanNumeralService.convertToRomanNumeral(4));
        assertEquals("V", romanNumeralService.convertToRomanNumeral(5));
        assertEquals("VI", romanNumeralService.convertToRomanNumeral(6));
        assertEquals("VII", romanNumeralService.convertToRomanNumeral(7));
        assertEquals("VIII", romanNumeralService.convertToRomanNumeral(8));
        assertEquals("IX", romanNumeralService.convertToRomanNumeral(9));
        // Needed to check one's 0 place. Return's the tens place value of 1, and the ones place value of 0
        assertEquals("X", romanNumeralService.convertToRomanNumeral(10));
    }

    @Test
    void convertToRomanNumeral_TensValues() {
        assertEquals("X", romanNumeralService.convertToRomanNumeral(10));
        assertEquals("XX", romanNumeralService.convertToRomanNumeral(20));
        assertEquals("XXX", romanNumeralService.convertToRomanNumeral(30));
        assertEquals("XL", romanNumeralService.convertToRomanNumeral(40));
        assertEquals("L", romanNumeralService.convertToRomanNumeral(50));
        assertEquals("LX", romanNumeralService.convertToRomanNumeral(60));
        assertEquals("LXX", romanNumeralService.convertToRomanNumeral(70));
        assertEquals("LXXX", romanNumeralService.convertToRomanNumeral(80));
        assertEquals("XC", romanNumeralService.convertToRomanNumeral(90));
        // Needed to check ten's 0 place. Return's the hundreds place value of 1, and the tens place value of 0
        assertEquals("C", romanNumeralService.convertToRomanNumeral(100));
    }

    @Test
    void convertToRomanNumeral_HundredsValues() {
        assertEquals("C", romanNumeralService.convertToRomanNumeral(100));
        assertEquals("CC", romanNumeralService.convertToRomanNumeral(200));
        assertEquals("CCC", romanNumeralService.convertToRomanNumeral(300));
        assertEquals("CD", romanNumeralService.convertToRomanNumeral(400));
        assertEquals("D", romanNumeralService.convertToRomanNumeral(500));
        assertEquals("DC", romanNumeralService.convertToRomanNumeral(600));
        assertEquals("DCC", romanNumeralService.convertToRomanNumeral(700));
        assertEquals("DCCC", romanNumeralService.convertToRomanNumeral(800));
        assertEquals("CM", romanNumeralService.convertToRomanNumeral(900));
        // Needed to check hundred's 0 place. Return's the thousands place value of 1, and the hundreds place value of 0
        assertEquals("M", romanNumeralService.convertToRomanNumeral(1000));
    }

    @Test
    void convertToRomanNumeral_ThousandsValues() {
        assertEquals("M", romanNumeralService.convertToRomanNumeral(1000));
        assertEquals("MM", romanNumeralService.convertToRomanNumeral(2000));
        assertEquals("MMM", romanNumeralService.convertToRomanNumeral(3000));
    }

    @Test
    void convertToRomanNumeral_LargeValue() {
        assertEquals("MMCMXXXVII", romanNumeralService.convertToRomanNumeral(2937));
    }

    @Test
    void convertToRomanNumeral_LargestValue() {
        assertEquals("MMMCMXCIX", romanNumeralService.convertToRomanNumeral(3999));
    }

    @Test
    void convertToRomanNumeral_LargeValueWithZerosInMiddle() {
        assertEquals("MMMVII", romanNumeralService.convertToRomanNumeral(3007));
    }

    @Test
    void convertToRomanNumeral_NegativeNumber() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            romanNumeralService.convertToRomanNumeral(-1);
        });
    }

    @Test
    void convertToRomanNumeral_NumberTooSmall_Zero() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            romanNumeralService.convertToRomanNumeral(0);
        });
    }

    @Test
    void convertToRomanNumeral_NumberTooLarge_4000() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            romanNumeralService.convertToRomanNumeral(4000);
        });
    }

    /**
     * For sanity's sake, this test checks that the service can correctly produce every single Roman numeral value
     * between 1-3999. The fact that there's a relatively small range of possible values that the service can convert,
     * and the outcome of each possible value is well known and documented, that makes this test both easy and feasible
     * to create.
     *
     * List of all Roman Numerals used in this test were taken from:
     * https://linuxwebdevelopment.com/numbers-1-to-3999-roman-numerals/
     */
    @Test
    void checkAllValues() throws IOException {
        File file = new File("src/test/java/com/assignment/wheninrome/AllRomanNumerals.txt");
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

        String listRomanNumeral;
        int decimalNumber = 0;
        while ((listRomanNumeral = bufferedReader.readLine()) != null) {
            decimalNumber++;
            String serviceRomanNumeral = romanNumeralService.convertToRomanNumeral(decimalNumber);
            assertEquals(listRomanNumeral, serviceRomanNumeral);
        }

        // Make sure we checked 3999 values
        assertEquals(3999, decimalNumber);
    }

}
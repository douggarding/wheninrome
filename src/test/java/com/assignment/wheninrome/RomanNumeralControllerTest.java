package com.assignment.wheninrome;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import static com.assignment.wheninrome.RomanNumeralController.BAD_PARAMETER_VALUE_MESSAGE;
import static com.assignment.wheninrome.RomanNumeralController.MISSING_PARAMETER_MESSAGE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RomanNumeralControllerTest {

    @Mock
    private RomanNumeralService romanNumeralService;

    @InjectMocks
    RomanNumeralController romanNumeralController;

    @Test
    void romanNumeral_ConvertsNumber() {
        when(romanNumeralService.convertToRomanNumeral(50)).thenReturn("L");
        RomanNumeral romanNumeral = romanNumeralController.romanNumeral(50);
        assertEquals("50", romanNumeral.getInput());
        assertEquals("L", romanNumeral.getOutput());
    }

    @Test
    void romanNumeral_ThrowsIllegalArgumentException() {
        when(romanNumeralService.convertToRomanNumeral(4000)).thenThrow(new IllegalArgumentException());
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            romanNumeralController.romanNumeral(4000);;
        });
    }

    @Test
    void handleIInvalidParameter() {
        IllegalArgumentException e = new IllegalArgumentException();
        ResponseEntity<String> response = romanNumeralController.handleInvalidParameter(e);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(BAD_PARAMETER_VALUE_MESSAGE, response.getBody());
    }

    @Test
    void handleMissingParameter() {
        MissingServletRequestParameterException e = new MissingServletRequestParameterException("","");
        ResponseEntity<String> response = romanNumeralController.handleMissingParameter(e);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(MISSING_PARAMETER_MESSAGE, response.getBody());
    }
}
package com.assignment.wheninrome;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;

import static com.assignment.wheninrome.RomanNumeralController.BAD_PARAMETER_VALUE_RESPONSE_MESSAGE;
import static com.assignment.wheninrome.RomanNumeralController.MISSING_PARAMETER_RESPONSE_MESSAGE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT) // Prevents complaining when a function doesn't use a mocked item
class RomanNumeralControllerTest {

    @Mock
    private RomanNumeralService romanNumeralService;
    @Mock
    private MeterRegistry meterRegistry;
    @Mock
    private Counter counter;

    @InjectMocks
    RomanNumeralController romanNumeralController;

    @BeforeEach
    void setUp() {
        when(meterRegistry.counter(any(), any(), any())).thenReturn(counter);
    }

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
    void handleInvalidParameter() {
        IllegalArgumentException e = new IllegalArgumentException();
        ResponseEntity<String> response = romanNumeralController.handleInvalidParameter(e);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(BAD_PARAMETER_VALUE_RESPONSE_MESSAGE, response.getBody());
    }

    @Test
    void handleMissingParameter() {
        MissingServletRequestParameterException e = new MissingServletRequestParameterException("","");
        ResponseEntity<String> response = romanNumeralController.handleMissingParameter(e);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(MISSING_PARAMETER_RESPONSE_MESSAGE, response.getBody());
    }

    @Test
    void countExceptionForMetrics() {
        romanNumeralController.countExceptionForMetrics(new Exception());
        verify(counter, times(1)).increment();
    }

}
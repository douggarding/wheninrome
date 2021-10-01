package com.assignment.wheninrome;

import javax.validation.ConstraintViolationException;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

/**
 * Controller that handles incoming HTTP requests for Roman numerals.
 */
@Validated
@RestController
public class RomanNumeralController {

    protected static final String ROMAN_NUMERAL_PATH = "/romannumeral";
    protected static final String ROMAN_NUMERAL_PARAM = "query";
    protected static final String MISSING_PARAMETER_MESSAGE = "Please include a parameter in the form of: " +
            "/romannumeral?query={integer}";
    protected static final String BAD_PARAMETER_VALUE_MESSAGE = "Only integers between 1 and 3999 can be " +
            "converted to Roman numerals.";

    static final Logger LOG = LoggerFactory.getLogger(RomanNumeralController.class);

    private RomanNumeralService romanNumeralService;
    private MeterRegistry registry;

    public RomanNumeralController(MeterRegistry registry, RomanNumeralService romanNumeralService) {
        this.romanNumeralService = romanNumeralService;
        this.registry = registry;
    }

    /**
     * Handles incoming GET requests to convert integers into Roman numerals. This functionality adheres to the
     * Wikipedia (https://en.wikipedia.org/wiki/Roman_numerals) definition, description, and formatting of
     * Roman numerals.
     *
     * Calls to this endpoint can be made by formatting queries as such: /romannumeral?query={integer}. Successful
     * responses will include the input integer and the output Roman numeral in the JSON format as such:
     * {
     *     "input" : "7",
     *     "output" : "VII"
     * }
     *
     * @param input - Integer value that's to be converted to a Roman numeral. Must be between 1 and 3999.
     * @return - JSON object containing the input integer and the output Roman numeral.
     * @throws IllegalArgumentException - Thrown when an integer incompatible with Roman numerals is given.
     */
    @GetMapping(ROMAN_NUMERAL_PATH)
    @Timed(value="romanNumeral.time", description = "Time taken to fully execute the GET romanNumeral endpoint.")
    public RomanNumeral romanNumeral(@RequestParam(ROMAN_NUMERAL_PARAM) @Min(1) @Max(3999) int input)
            throws IllegalArgumentException {
        LOG.info("Begin processing request to convert " + input + " to a Roman numeral.");
        String output = romanNumeralService.convertToRomanNumeral(input);
        return new RomanNumeral(input, output);
    }

    /**
     * Generates a 400 Bad Request response when the request parameter is either malformed or the value falls outside
     * the valid Roman numeral range of 1-3999.
     *
     * MethodArgumentTypeMismatchException occurs when a "query" parameter is incorrectly formatted (such as being
     * blank or a non-integer). ConstraintViolationException and IllegalArgumentException occur when the given parameter
     * is out of range.
     *
     * @return ResponseEntity containing an HTTP status and an error message from the IllegalArgumentException.
     */
    @ExceptionHandler({ConstraintViolationException.class, MethodArgumentTypeMismatchException.class,
            IllegalArgumentException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleInvalidParameter(Exception e) {
        // Exception messages contain more info about the failed parameter that was passed.
        LOG.error("failed to convert value to Roman numeral.", e);
        countExceptionForMetrics(e);

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(BAD_PARAMETER_VALUE_MESSAGE);
    }

    /**
     * Generates a 400 Bad Request response when a MissingServletRequestParameterException is thrown from this class.
     * MissingServletRequestParameterException occurs when no "query" parameter was included with the request.
     *
     * @return ResponseEntity containing an HTTP status and an error message describing how to include a properly
     * formatted parameter with the query.
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleMissingParameter(Exception e) {
        LOG.error("Parameter \"query\" was absent from URL request for converting a value to a Roman numeral.", e);
        countExceptionForMetrics(e);

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(MISSING_PARAMETER_MESSAGE);
    }

    protected void countExceptionForMetrics(Exception e) {
        String exceptionType = e.getClass().getSimpleName();
        Counter exceptionCounter = registry
                .counter("romanNumeral.exceptions.count", "ExceptionType", exceptionType);
        exceptionCounter.increment();
    }

}
package com.assignment.wheninrome;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

/**
 * Controller that handles incoming HTTP requests for Roman numerals.
 */
@RestController
public class RomanNumeralController {

    @Autowired
    private RomanNumeralService romanNumeralService;

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
    @GetMapping("/romannumeral")
    public RomanNumeral romanNumeral(@RequestParam(value = "query") int input) throws IllegalArgumentException {
        String output = romanNumeralService.convertToRomanNumeral(input);
        return new RomanNumeral(input, output);
    }

    /**
     * Generates a 400 Bad Request response when an IllegalArgumentException is thrown from this class. This should
     * occur when the query  integer is given that's outside the range of possible Roman numerals (1-3999).
     *
     * @param exception - IllegalArgumentException that was thrown
     * @return ResponseEntity containing an HTTP status and an error message from the IllegalArgumentException.
     */
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST) // TODO: Perhaps should be a 422 "bad entity" response?
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exception.getMessage());
    }

    /**
     * Generates a 400 Bad Request response when a MissingServletRequestParameterException or a
     * MethodArgumentTypeMismatchException is thrown from this class.
     *
     * MethodArgumentTypeMismatchException occurs when a "query" parameter is incorrectly formatted (such as being
     * blank, a non-integer type, etc.). MissingServletRequestParameterException occurs when no "query" parameter was
     * included with the request.
     *
     * @return ResponseEntity containing an HTTP status and an error message describing what a properly formatted query
     * looks like.
     */
    @ExceptionHandler({MissingServletRequestParameterException.class, MethodArgumentTypeMismatchException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleMissingParameter() {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("Please include a parameter in the form of: /romannumeral?query={integer}");
    }

}
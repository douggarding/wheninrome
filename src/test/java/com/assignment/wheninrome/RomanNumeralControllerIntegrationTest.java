package com.assignment.wheninrome;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static com.assignment.wheninrome.RomanNumeralController.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = RomanNumeralController.class)
@MockitoSettings(strictness = Strictness.LENIENT) // Prevents complaining when a function doesn't use a mocked item
class RomanNumeralControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private RomanNumeralService romanNumeralService;
    @MockBean
    private MeterRegistry meterRegistry;
    @Mock
    private Counter counter;

    @BeforeEach
    void setUp() {
        when(meterRegistry.counter(any(), any(), any())).thenReturn(counter);
    }

    @Test
    void getRomanNumeral_whenValidInput_thenReturns200() throws Exception {
        when(romanNumeralService.convertToRomanNumeral(123)).thenReturn("CXXIII");

        MvcResult mvcResult = mockMvc.perform(get(ROMAN_NUMERAL_PATH)
                        .contentType("application/json")
                        .param(ROMAN_NUMERAL_PARAM, "123"))
                .andExpect(status().isOk())
                .andReturn();

        String jsonExpectedResult = (objectMapper.writeValueAsString(new RomanNumeral(123, "CXXIII")));
        String jsonActualResult = mvcResult.getResponse().getContentAsString();
        assertEquals(jsonExpectedResult, jsonActualResult);
    }

    @Test
    void getRomanNumeral_whenParameterTooLarge_thenReturns400() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get(ROMAN_NUMERAL_PATH)
                        .contentType("application/json")
                        .param(ROMAN_NUMERAL_PARAM, "4000"))
                .andExpect(status().isBadRequest())
                .andReturn();

        String actualResultMessage = mvcResult.getResponse().getContentAsString();
        assertEquals(BAD_PARAMETER_VALUE_MESSAGE, actualResultMessage);
        verify(counter, times(1)).increment(); // Assert metrics saved
    }

    @Test
    void getRomanNumeral_whenParameterTooSmall_thenReturns400() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get(ROMAN_NUMERAL_PATH)
                        .contentType("application/json")
                        .param(ROMAN_NUMERAL_PARAM, "0"))
                .andExpect(status().isBadRequest())
                .andReturn();

        String actualResultMessage = mvcResult.getResponse().getContentAsString();
        assertEquals(BAD_PARAMETER_VALUE_MESSAGE, actualResultMessage);
    }

    @Test
    void getRomanNumeral_whenParameterIsFloatingPoint_thenReturns400() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get(ROMAN_NUMERAL_PATH)
                        .contentType("application/json")
                        .param(ROMAN_NUMERAL_PARAM, "2.0"))
                .andExpect(status().isBadRequest())
                .andReturn();

        String actualResultMessage = mvcResult.getResponse().getContentAsString();
        assertEquals(BAD_PARAMETER_VALUE_MESSAGE, actualResultMessage);
        verify(counter, times(1)).increment(); // Assert Exception metric saved
    }

    @Test
    void getRomanNumeral_whenParameterMalformed_thenReturns400() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get(ROMAN_NUMERAL_PATH)
                        .contentType("application/json")
                        .param(ROMAN_NUMERAL_PARAM, "123A"))
                .andExpect(status().isBadRequest())
                .andReturn();

        String actualResultMessage = mvcResult.getResponse().getContentAsString();
        assertEquals(BAD_PARAMETER_VALUE_MESSAGE, actualResultMessage);
        verify(counter, times(1)).increment(); // Assert Exception metric saved
    }

    @Test
    void getRomanNumeral_whenParameterValueIsEmpty_thenReturns400() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get(ROMAN_NUMERAL_PATH)
                        .contentType("application/json")
                        .param(ROMAN_NUMERAL_PARAM, ""))
                .andExpect(status().isBadRequest())
                .andReturn();

        String actualResultMessage = mvcResult.getResponse().getContentAsString();
        assertEquals(BAD_PARAMETER_VALUE_MESSAGE, actualResultMessage);
        verify(counter, times(1)).increment(); // Assert Exception metric saved
    }

    @Test
    void getRomanNumeral_whenMissingParameter_thenReturns400() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get(ROMAN_NUMERAL_PATH)
                        .contentType("application/json"))
                .andExpect(status().isBadRequest())
                .andReturn();

        String actualResultMessage = mvcResult.getResponse().getContentAsString();
        assertEquals(MISSING_PARAMETER_MESSAGE, actualResultMessage);
        verify(counter, times(1)).increment(); // Assert Exception metric saved
    }

}
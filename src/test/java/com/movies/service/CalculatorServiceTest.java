package com.movies.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CalculatorServiceTest {
	
	CalculatorService calculatorService;

	@BeforeEach
	void setUp() throws Exception {
		
		calculatorService = new CalculatorService();
	}

	@Test
	void testMultiply() {
		
		int actualResult = calculatorService.multiply(4, 5);
		
		assertEquals(20, actualResult);
		
	}

}

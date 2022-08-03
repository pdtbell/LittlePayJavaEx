/**
 * 
 */
package com.exercises.littlepay;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.nio.file.NoSuchFileException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * @author pdtbe
 *
 */
class AppTest {

	@Test
	@DisplayName("Test NoSuchFileException thrown for invalid taps.csv file")
	void parseTapsCSVExceptionTest() {
		CSVInput tapsCSVInput = new CSVInput();
		// set up user
		
	    Throwable exception = assertThrows(NoSuchFileException.class, () -> tapsCSVInput.parseTapsCSV("not_a_real_file.csv"));
	    //assertEquals(exception, NoSuchFileException.class);
	}


}

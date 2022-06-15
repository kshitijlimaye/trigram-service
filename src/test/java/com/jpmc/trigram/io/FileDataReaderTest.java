package com.jpmc.trigram.io;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FileDataReaderTest {
	FileDataReader fileDataReader;
	
	@BeforeEach
	void init() {
		this.fileDataReader = new FileDataReader();
	}
	
	@Test
	void whenReadFileMissing_shouldRaiseIOException() {
		assertThrows(IOException.class, () -> fileDataReader.readFile("MISSING_FILE_TO_READ.txt"));
	}
	
	@Test
	void whenReadFilePresent_shouldNotThrowIOException() throws IOException {
		assertDoesNotThrow(() -> fileDataReader.readFile(ClassLoader.getSystemResource("test_read.txt").getFile()));
	}
	
	@Test
	void whenReadFilePresent_shouldReturnStringBuilder() throws IOException {
		StringBuilder result = fileDataReader.readFile(ClassLoader.getSystemResource("test_read.txt").getFile());
		assertNotEquals(0,result.toString().length());
	}
}

package com.jpmc.trigram.io;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FileDataWriterTest {
	FileDataWriter fileDataWriter;
	
	@BeforeEach
	void init() {
		this.fileDataWriter = new FileDataWriter();
	}
	
	@AfterAll
	static void clearUpFiles() {
		File file1 = new File("test_output.txt");
		File file2 = new File("test_output_not_writable.txt");
		if(file1.exists()) file1.delete();
		if(file2.exists()) file2.delete();
	}
	
	@Test
	void whenValidInputContents_shouldNotThrowException() throws IOException {
		Set<List<String>> set = new HashSet<List<String>>();
		set.add(Arrays.asList("Hello","world","from","UK"));
		File file = new File("test_output.txt");
		if(file.exists()) file.delete();
		assertDoesNotThrow(() -> fileDataWriter.writeToFile("test_output.txt", set));
	}
	
	@Test
	void whenFileNotWritable_shouldThrowException() throws IOException {
		Set<List<String>> set = new HashSet<List<String>>();
		set.add(Arrays.asList("Hello","world","from","UK"));
		fileDataWriter.writeToFile("test_output_not_writable.txt", set);
		File file = new File("test_output_not_writable.txt");
		file.setWritable(false);
		assertThrows(IOException.class,() -> fileDataWriter.writeToFile("test_output_not_writable.txt", set));
	}
	
	@Test
	void whenValidInputContents_shouldWriteToFile() throws IOException {
		Set<List<String>> set = new HashSet<List<String>>();
		set.add(Arrays.asList("Hello","world","from","UK"));
		File file = new File("test_output.txt");
		if(file.exists()) file.delete();
		boolean result = fileDataWriter.writeToFile("test_output.txt", set);
		assertEquals(true, file.exists());
		assertEquals(true, result);
	}
}

package com.jpmc.trigram;

import java.io.IOException;

import com.jpmc.trigram.exception.InsufficientDataException;
import com.jpmc.trigram.io.FileDataReader;
import com.jpmc.trigram.io.FileDataWriter;
import com.jpmc.trigram.service.StoryGenerator;
import com.jpmc.trigram.service.TrigramAnalyzer;
import com.jpmc.trigram.service.TrigramService;

import lombok.extern.log4j.Log4j2;

/**
 * This is starter class to trigger processing
 * Following assumption has been made
 * Input contents will be provided through a "input.txt" file 
 * Output story is generated into "output.txt" file
 * 
 * @author Kshitij Limaye
 *
 */

@Log4j2
public class App {

	public static void main(String[] args) {
		FileDataReader fileDataReader = new FileDataReader();
		FileDataWriter fileDataWriter = new FileDataWriter();
		TrigramAnalyzer trigramAnalyzer = new TrigramAnalyzer();
		StoryGenerator storyGenerator = new StoryGenerator();
		
		TrigramService obj = new TrigramService(trigramAnalyzer, storyGenerator, fileDataReader, fileDataWriter);
		
		try {
			log.debug("Inside main - starting the program");
			obj.process("input.txt","output.txt");
		} catch (IOException | InsufficientDataException e) {
			log.error(e.getMessage());
		}
	}
}

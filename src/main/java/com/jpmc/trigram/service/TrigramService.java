package com.jpmc.trigram.service;

import java.io.IOException;

import com.jpmc.trigram.exception.InsufficientDataException;
import com.jpmc.trigram.io.FileDataReader;
import com.jpmc.trigram.io.FileDataWriter;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

/**
 * This class interacts with all other business classes to
 * get the job done. It sequentially calls file read, trigram analysis, 
 * story creation and file write functions.
 * @author Kshitij Limaye
 *
 */

@AllArgsConstructor
@Log4j2
public class TrigramService {
	
	private TrigramAnalyzer trigramAnalyzer;
	private StoryGenerator storyGenerator;
	private FileDataReader fileDataReader;
	private FileDataWriter fileDataWriter;
	
	/**
	 * This is manager method that sequentially triggers file read, trigram analysis, 
	 * story generation and file write operations.
	 * @param String inputFile - path of the file along with file name
	 * @param String outputFile - path of the file along with file name
	 * @throws IOException when there is failure during read/write of the file
	 * @throws InsufficientDataException when the input file contents are insufficient for trigram analysis
	 */
	public void process(String inputFile, String outputFile) throws IOException, InsufficientDataException {
		log.info("Starting the trigram program");	
		StringBuilder sb = this.fileDataReader.readFile(inputFile);		
		log.info("File contents read");
		this.trigramAnalyzer.analyzeContent(sb.toString());
		log.info("Trigram analysis completed for the file contents");
		this.storyGenerator.generateStory(trigramAnalyzer.getRandomWord(), trigramAnalyzer.getAnalyzedContent());
		log.info("A new story using trigrams has been created");
		this.fileDataWriter.writeToFile(outputFile, storyGenerator.getCombinations());
		log.info("The output has been written into the file");
	}
}

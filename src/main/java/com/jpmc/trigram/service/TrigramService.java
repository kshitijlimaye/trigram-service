package com.jpmc.trigram.service;

import java.io.IOException;

import com.jpmc.trigram.exception.InsufficientDataException;
import com.jpmc.trigram.io.FileDataReader;
import com.jpmc.trigram.io.FileDataWriter;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;

/**
 * Trigram Service - this service generates a story based on the trigram
 * analysis of the input text content. Algorithm uses random selection
 * technique, hence for same input content, you may get different result every time
 * 
 * Input contents need to be provided through a "input.txt" file 
 * Output story is generated into "output.txt" file
 * 
 * @author Kshitij Limaye
 *
 */

@Getter
@Log4j2
public class TrigramService {
	
	private TrigramAnalyzer trigramAnalyzer;
	private StoryGenerator storyGenerator;
	private FileDataReader fileDataReader;
	private FileDataWriter fileDataWriter;
	
	public TrigramService() {
		this.trigramAnalyzer = new TrigramAnalyzer();
		this.storyGenerator = new StoryGenerator();
		this.fileDataReader = new FileDataReader();
		this.fileDataWriter = new FileDataWriter();
	}
	

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

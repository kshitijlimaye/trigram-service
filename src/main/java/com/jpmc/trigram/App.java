package com.jpmc.trigram;

import java.io.IOException;

import com.jpmc.trigram.exception.InsufficientDataException;
import com.jpmc.trigram.service.TrigramService;

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

@Log4j2
public class App {

	public static void main(String[] args) {
		TrigramService obj = new TrigramService();
		try {
			log.debug("Inside main - starting the program");
			obj.process("input.txt","output.txt");
		} catch (IOException | InsufficientDataException e) {
			log.error(e.getMessage());
		}
	}
}

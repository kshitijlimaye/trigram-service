package com.jpmc.trigram.io;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
@NoArgsConstructor
@Log4j2
public class FileDataReader {
	
	/**
	 * This method reads the input file line by line into a StringBuilder
	 * @param String inputFile - path of the file along with file name
	 * @return StringBuilder which holds all the contents of the file
	 * @throws IOException if program fails to read the file
	 */
	public StringBuilder readFile(String inputFile) throws IOException {
		log.debug("Inside readFile - starting the file read operation");
		StringBuilder builder = new StringBuilder();
		try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(inputFile),StandardCharsets.UTF_8))) {
			String line = br.readLine();
			while (line != null) {
				builder.append(line + " ");
				line = br.readLine();
			}
		} catch (IOException e) {
			log.error("Could not read the file!");
			throw e;
		}
		log.debug("Inside readFile - file read completed without any issues");
		return builder;
	}

}

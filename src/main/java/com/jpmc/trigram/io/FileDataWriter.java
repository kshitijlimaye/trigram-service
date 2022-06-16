package com.jpmc.trigram.io;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

/**
 * This class handles the file write operation
 * @author Kshitij_Limaye
 *
 */
@AllArgsConstructor
@Log4j2
public class FileDataWriter {
	
	/**
	 * This method writes the output story into the file
	 * @param outputFile - path of the file along with file name
	 * @param combinations - list of possible stories
	 * @throws IOException if program fails to write to file
	 */
	public boolean writeToFile(String outputFile, Set<List<String>> combinations) throws IOException {
		log.debug("Inside writeToFile - starting the file write operation");
		try (BufferedWriter br = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFile),StandardCharsets.UTF_8))) {
			for (List<String> list : combinations) {
				br.write(String.join(" ", list));
			}
		} catch (IOException e) {
			log.error("Could not write to the file!");
			throw e;
		}
		log.debug("Inside writeToFile - file read completed without any issues");
		return true;
	}
}

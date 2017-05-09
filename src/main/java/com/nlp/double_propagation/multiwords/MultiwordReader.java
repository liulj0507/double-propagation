package com.nlp.double_propagation.multiwords;

import com.google.common.collect.Lists;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class MultiwordReader {

	public static List<String>readMultiwordFile(String pathToFile){
		File f=new File(pathToFile);
		try {
			List<String> lines = FileUtils.readLines(f, "UTF-8");
			List<String> multiwords = Lists.newArrayList();
				for(String line:lines){
					multiwords.add(line.trim());
				}
				return multiwords;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
}

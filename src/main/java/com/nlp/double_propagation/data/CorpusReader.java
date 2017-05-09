package com.nlp.double_propagation.data;

import java.util.List;

public interface CorpusReader {

	public String readCorpusFileContent(String corpusFilePath);
	
	public List<String> readCorpusDirectoryFileContent(String dirPath);
	
}

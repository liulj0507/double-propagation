package com.nlp.double_propagation.analysis;

import com.nlp.double_propagation.model.IntraSentenceWordRelations;

import java.util.List;

public interface CorpusAnalyzer {

	public List<IntraSentenceWordRelations> analyzeCorpus(List<String> texts, String language);
	
}

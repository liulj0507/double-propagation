package com.nlp.double_propagation.algorithm.rules;

import com.nlp.double_propagation.model.IntraSentenceWordRelations;
import com.nlp.double_propagation.model.Word;

import java.util.List;

public interface DoublePropagationRule {

	public List<RuleCheckResult> applyRule(Word word, IntraSentenceWordRelations intraSentenceWordRelations);
	
}

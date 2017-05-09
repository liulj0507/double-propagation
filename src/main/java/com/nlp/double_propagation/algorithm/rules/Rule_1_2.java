package com.nlp.double_propagation.algorithm.rules;

import com.google.common.collect.Lists;
import com.nlp.double_propagation.model.*;

import java.util.List;

/**
 * This rule is different to the one in Qiu et al. 2011
 * It extracts a target word which is the subject of a verb of which the seed opinion is the direct object.
 */

public class Rule_1_2 implements DoublePropagationRule{

	public static final String RULE_NAME=Rule_1_2.class.getName();
	
	private static final PartOfSpeech REQUIRED_TARGETWORD_POSTAG=PartOfSpeech.NOUN;
	public static final DoublePropagationWordType TRIGGERING_WORD_TYPE = DoublePropagationWordType.OPINION;
	
	@Override
	public List<RuleCheckResult> applyRule(Word word, IntraSentenceWordRelations intraSentenceWordRelations) {

		List<RuleCheckResult>ruleCheckResults=Lists.newArrayList();
		
		List<Word> intermediateWords = intraSentenceWordRelations.getWordsGoverningThis(word, RelationTypes.DirectObject);
		for(Word intermediateWord:intermediateWords){
			List<Word> candidateWords = intraSentenceWordRelations.getWordsGovernedByThis(intermediateWord, RelationTypes.NominalSubject);
			for(Word candidateWord:candidateWords){
				if(candidateWord.getPostag()==REQUIRED_TARGETWORD_POSTAG){
					RuleCheckResult ruleCheckResult = new RuleCheckResult();
					ruleCheckResult.setTriggeringWord(word);
					ruleCheckResult.setTriggeringWordType(TRIGGERING_WORD_TYPE);
					ruleCheckResult.setExtractedWord(candidateWord);
					ruleCheckResult.setExtractedWordType(DoublePropagationWordType.TARGET);
					ruleCheckResult.setRelType(RelationTypes.NominalSubject);
					ruleCheckResult.setUsedRule(RULE_NAME);
					ruleCheckResults.add(ruleCheckResult);
				}
			}
		}
		return ruleCheckResults;
	}

	
	
}

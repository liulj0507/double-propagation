package com.nlp.double_propagation.algorithm.rules;

import com.google.common.collect.Lists;
import com.nlp.double_propagation.model.*;

import java.util.List;

/**
 * Extract an opinion word modifying a word which already is being modified by a known opinion word (with the same kind of relation)
 * E.g. This is a sexy, cool, great mp3 player.
 */
public class Rule_4_2 implements DoublePropagationRule{

	public static final String RULE_NAME=Rule_4_2.class.getName();
	
	private static final PartOfSpeech REQUIRED_TARGETWORD_POSTAG=PartOfSpeech.ADJECTIVE;
	public static final DoublePropagationWordType TRIGGERING_WORD_TYPE = DoublePropagationWordType.OPINION;
	
	@Override
	public List<RuleCheckResult> applyRule(Word word, IntraSentenceWordRelations intraSentenceWordRelations) {
		List<RuleCheckResult>ruleCheckResults=Lists.newArrayList();
		
		for(RelationTypes relType:RelationTypes.values()){
			List<Word> intermediateWords = intraSentenceWordRelations.getWordsGoverningThis(word, relType);
			for(Word intermediateWord:intermediateWords){
				List<Word> candidateWords = intraSentenceWordRelations.getWordsGovernedByThis(intermediateWord, relType);
				for(Word candidateWord:candidateWords){
					if(!candidateWord.equals(word) && candidateWord.getPostag()==REQUIRED_TARGETWORD_POSTAG){
						RuleCheckResult ruleCheckResult = new RuleCheckResult();
						ruleCheckResult.setTriggeringWord(word);
						ruleCheckResult.setTriggeringWordType(TRIGGERING_WORD_TYPE);
						ruleCheckResult.setExtractedWord(candidateWord);
						ruleCheckResult.setExtractedWordType(DoublePropagationWordType.OPINION);
						ruleCheckResult.setRelType(relType);
						ruleCheckResult.setUsedRule(RULE_NAME);
						ruleCheckResults.add(ruleCheckResult);
					}
				}
			}
		}
		return ruleCheckResults;
	}

	
	
}

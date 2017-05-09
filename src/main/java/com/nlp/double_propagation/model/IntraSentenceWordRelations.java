package com.nlp.double_propagation.model;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import org.apache.log4j.Logger;
import com.nlp.double_propagation.multiwords.MultiwordChecker;

import java.util.*;

/**
 * Class that abstracts the relations (dependency) between words, provided by a
 * parsing tool. This way the rest of the code can be tool-agnostic. The only
 * tool-dependent code should be in the mapper that fills this structure from
 * the actual tool result
 */
public class IntraSentenceWordRelations {

	private static Logger log=Logger.getLogger(IntraSentenceWordRelations.class);
	
	//Map with the relations where the word is the governor (i.e. the origin of the arc) nsubj(has,something) --> has is the governor
	private Multimap<String, WordToWordRelation> governorWordRelationMap;
	//Map with the relations where the word is the dependent (i.e. the destination of the arc) nsubj(has,something) --> something is the dependent
	private Multimap<String, WordToWordRelation> dependentWordRelationMap;
	private List<Word> sentenceWords;
	
	private Map<String,Integer>spanToWordNumberMap;

	private IntraSentenceWordRelations() {
		super();
		governorWordRelationMap = ArrayListMultimap.create();
		dependentWordRelationMap = ArrayListMultimap.create();
		sentenceWords = Lists.newArrayList();
	}

	/**
	 * THIS REQUIRES A LOT OF LOGIC TO MAP THE INFO COMING FROM WHATEVER SOURCE
	 * (STANFORD PARSER...) TO THIS STRUCTURE
	 * 
	 * @return
	 */
	public static IntraSentenceWordRelations createRelations() {
		return new IntraSentenceWordRelations();
	}

	/**
	 * Adds the input relation for later use
	 * @param wordToWordRelation
	 */
	public void addWordToWordRelation(WordToWordRelation wordToWordRelation) {
		governorWordRelationMap.put(wordToWordRelation.getSourceWord().getSpanSignature(), wordToWordRelation);
		dependentWordRelationMap.put(wordToWordRelation.getTargetWord().getSpanSignature(), wordToWordRelation);
		addToSentenceWordsInOrder(wordToWordRelation);
	}
	
	private void addToSentenceWordsInOrder(WordToWordRelation wordToWordRelation){
		Word sourceWord=wordToWordRelation.getSourceWord();
		Word targetWord=wordToWordRelation.getTargetWord();
		addWordInOrder(sourceWord);
		addWordInOrder(targetWord);
	}
	
	private void addWordInOrder(Word word){
		//special case, the list is empty
				if(sentenceWords.isEmpty()){
					sentenceWords.add(word);
				}else{
					//general case, look for the first word that goes after the word we want to add, and add at that point
					boolean added=false;
					boolean theVerySameWordAlreadyInTheList=false;
					for(int i=0;i<sentenceWords.size();i++){
						Word iesimWord=sentenceWords.get(i);
						if(word.isSameWordInTheSentenceThan(iesimWord)){
							//if is the same word, because it participates in many relations as the governor (i.e. the beautiful eye -> the->eye, beautiful->eye)
							//the break, it must not be added again
							theVerySameWordAlreadyInTheList=true;
							break;
						}else if(word.isPreviousWordInTheSentenceThan(iesimWord)){
							sentenceWords.add(i, word);
							added=true;
							break;
						}
					}
					//special case, the word is the last of the current list
					boolean weHaveBrokenTheLoopOnPurpose=added || theVerySameWordAlreadyInTheList;
					if(!weHaveBrokenTheLoopOnPurpose){
						sentenceWords.add(word);
					}
				}
	}

	//ATTENTION: The semantics of this method is to get the words that are dependents to the given one in a certain type of relations
	public List<Word> getWordsGovernedByThis(Word aWord, RelationTypes relType) {
		Collection<WordToWordRelation> relatedWords = governorWordRelationMap.get(aWord.getSpanSignature());
		Iterator<WordToWordRelation> relatedWordsIterator = relatedWords.iterator();
		List<Word> results = Lists.newArrayList();
		while (relatedWordsIterator.hasNext()) {
			WordToWordRelation wordToWordRelation = relatedWordsIterator.next();
			if (wordToWordRelation.getRelType() == relType) {
				results.add(wordToWordRelation.getTargetWord());
			}
		}
		return results;
	}
	
	//ATTENTION: Here the index (i.e. the map) is about dependent words, so it must return the SOURCE WORD (as opposite to the getWordsGovernedBy)
	public List<Word> getWordsGoverningThis(Word aWord, RelationTypes relType){
		Collection<WordToWordRelation> relatedWords = dependentWordRelationMap.get(aWord.getSpanSignature());
		Iterator<WordToWordRelation> relatedWordsIterator = relatedWords.iterator();
		List<Word> results = Lists.newArrayList();
		while (relatedWordsIterator.hasNext()) {
			WordToWordRelation wordToWordRelation = relatedWordsIterator.next();
			if (wordToWordRelation.getRelType() == relType) {
				results.add(wordToWordRelation.getSourceWord());
			}
		}
		return results;
	}
	
//	public List<Word>getSentenceWords(){
//		return sentenceWords;
//	}
	
	public Word getWord(int wordNumber){
		return sentenceWords.get(wordNumber);
	}
	
	public int getNumberOfWordsInThisSentence(){
		return sentenceWords.size();
	}
	
	protected void populateSpanToWordNumberMap(){
		spanToWordNumberMap=new HashMap<String,Integer>();
		for(int wordNumber=0;wordNumber<sentenceWords.size();wordNumber++){
			Word word=sentenceWords.get(wordNumber);
			String spanSignature=word.getSpanSignature();
			spanToWordNumberMap.put(spanSignature, wordNumber);
		}
	}
	
	public Integer getWordNumber(Word word){
		if(spanToWordNumberMap==null){
			populateSpanToWordNumberMap();
		}
		Integer wordNumber=spanToWordNumberMap.get(word.getSpanSignature());
		if(wordNumber==null){
			log.debug("Returning null word number for: "+word+"\nSpanToWordNumberMap content: "+spanToWordNumberMap+"\n"+sentenceWords);
			log.warn("SIZE OF MAPS: governorsMap:"+governorWordRelationMap.size() + " dependentsMap:"+dependentWordRelationMap.size()+" SIZE OF SENTENCE WORDS: "+sentenceWords.size());
		}
		
		return wordNumber;
	}
	
	/*
	 * Multiword list should be loaded and managed in other place, not here
	 */
	public void detectAndMergeMultiwords(MultiwordChecker multiwordChecker){
//		for(Entry<String, WordToWordRelation> entry:governorWordRelationMap.entries()){
//			log.debug("GOVERNOR_MAP:"+entry);
//		}
//		for(Entry<String, WordToWordRelation> entry:dependentWordRelationMap.entries()){
//			log.debug("DEPENDENT_MAP:"+entry);
//		}
		//MultiwordChecker multiwordChecker=MultiwordChecker.createMultiworChecker(null);
		List<Word> mergedWords = multiwordChecker.checkMultiwords(sentenceWords);
		//now check the multiwords (composing words inside each Word object) to perform the reassignment of the dependencies
		for(Word mergedWord:mergedWords){
			if(mergedWord.isMultiword()){
				List<Word> composingWords = mergedWord.getComposingWords();
				for(Word innerWord:composingWords){
					Iterator<WordToWordRelation> governorRels = governorWordRelationMap.get(innerWord.getSpanSignature()).iterator();
					while(governorRels.hasNext()){
						WordToWordRelation rel = governorRels.next();
						rel.setSourceWord(mergedWord);
						if(!composingWords.contains(rel.getTargetWord()) && !rel.getSourceWord().isSameWordInTheSentenceThan(rel.getTargetWord())){
							
							governorWordRelationMap.put(mergedWord.getSpanSignature(), rel);
						}
					}
					log.debug("REMOVING INNER WORD FROM GOVERNORMAP: "+innerWord);
					governorWordRelationMap.removeAll(innerWord);
					Iterator<WordToWordRelation> dependentRels = dependentWordRelationMap.get(innerWord.getSpanSignature()).iterator();
					while(dependentRels.hasNext()){
						WordToWordRelation rel = dependentRels.next();
						rel.setTargetWord(mergedWord);
						if(!composingWords.contains(rel.getSourceWord()) && !rel.getSourceWord().isSameWordInTheSentenceThan(rel.getTargetWord())){
							
							dependentWordRelationMap.put(mergedWord.getSpanSignature(), rel);
						}
					}
					log.debug("REMOVING INNER WORD FROM DEPENDENTMAP: "+innerWord);
					dependentWordRelationMap.removeAll(innerWord);
				}
			}
		}
		this.sentenceWords=mergedWords;
		populateSpanToWordNumberMap();
		
//		for(Entry<String, WordToWordRelation> entry:governorWordRelationMap.entries()){
//			log.debug("GOVERNOR_MAP:"+entry);
//		}
//		for(Entry<String, WordToWordRelation> entry:dependentWordRelationMap.entries()){
//			log.debug("DEPENDENT_MAP:"+entry);
//		}
	}

}

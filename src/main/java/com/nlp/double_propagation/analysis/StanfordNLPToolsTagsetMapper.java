package com.nlp.double_propagation.analysis;

import com.nlp.double_propagation.model.PartOfSpeech;
import com.nlp.double_propagation.model.RelationTypes;

import java.util.HashMap;

import static com.nlp.double_propagation.model.PartOfSpeech.*;
import static com.nlp.double_propagation.model.RelationTypes.*;
import static com.nlp.double_propagation.model.RelationTypes.AdjectivalModifier;

public class StanfordNLPToolsTagsetMapper extends AbstractTagsetMapper{

	
	public StanfordNLPToolsTagsetMapper() {
		super();
	}

	@Override
	protected void loadMappings() {
		this.postagsMap=new HashMap<PartOfSpeech,String>();
		postagsMap.put(ADJECTIVE, "J");
		postagsMap.put(NOUN, "N");
		postagsMap.put(ADVERB, "R");
		postagsMap.put(VERB, "V");
		
		
		this.dependencyRelationsMap=new HashMap<RelationTypes,String>();
		dependencyRelationsMap.put(AdjectivalModifier, "amod");
		//MISSING THE REST OF THE DEPENDENCY RELATION MAPPINGS
	}

}

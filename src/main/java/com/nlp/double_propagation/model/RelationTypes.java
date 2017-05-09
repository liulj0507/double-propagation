package com.nlp.double_propagation.model;

public enum RelationTypes {

	AdjectivalModifier, //amod in Stanford
	NounCompoundModifier, //nn in Stanford
	NominalSubject,  //nsubj in Stanford
	DirectObject, //dobj in Stanford
	Conjunction, //conj in Stanford
	
	PartWhole,
	NoPattern,
	//MISCELLANEOUS ONE, FOR UNINTERESTING AND UNMAPPED RELATIONS
	OTHER;

}

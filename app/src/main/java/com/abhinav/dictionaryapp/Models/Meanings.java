package com.abhinav.dictionaryapp.Models;

import java.util.List;

public class Meanings {

    String partOfSpeech = "";
    List<Definitions> definitions = null;
    List<String> antonyms = null;
    List<String> synonyms = null;

    public List<String> getAntonyms() {
        return antonyms;
    }

    public void setAntonyms(List<String> antonyms) {
        this.antonyms = antonyms;
    }

    public List<String> getSynonyms() {
        return synonyms;
    }

    public void setSynonyms(List<String> synonyms) {
        this.synonyms = synonyms;
    }

    public String getPartOfSpeech() {
        return partOfSpeech;
    }

    public void setPartOfSpeech(String partOfSpeech) {
        this.partOfSpeech = partOfSpeech;
    }

    public List<Definitions> getDefinitions() {
        return definitions;
    }

    public void setDefinitions(List<Definitions> definitionsList) {
        this.definitions = definitionsList;
    }
}

package com.crackingMBA.training.pojo;

import java.util.ArrayList;

/**
 * Created by vijayp on 7/7/17.
 */

public class VocabGamesModel {

    private ArrayList<VocabGames> VocabGames;

    public ArrayList<VocabGames> getVocabGamesArrayList() {
        return VocabGames;
    }

    @Override
    public String toString() {
        return "VocabGamesModel{" +
                "vocabGamesArrayList=" + VocabGames +
                '}';
    }

    public void setVocabGamesArrayList(ArrayList<VocabGames> vocabGamesArrayList) {
        this.VocabGames = vocabGamesArrayList;
    }
}

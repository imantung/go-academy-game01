package com.gojek.academy.refactoring.game01.game;

// TODO: 2. refused bequest
public class CardBoard implements City {

    private String [][] map;


    public CardBoard(int totalRow, int totalColumn) {
        this.map = new String[totalRow][totalColumn];
    }

    @Override
    public String[][] getMap() {
        return map;
    }

}

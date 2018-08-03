package com.gojek.academy.refactoring.game01.game;

// TODO: 5. a lazy class
public class Sizer {

    public static final int TILE_SIZE = 120;
    public static final int INFO_BOX_HEIGHT = 70;

    public int getTileSize(){
        return TILE_SIZE;
    }

    public int getInfoBoxHeight(){
        return INFO_BOX_HEIGHT;
    }

    public int getGameSceneWidth(int column){
        return column * TILE_SIZE;
    }

    public int getGameSceneHeight(int row){
        return row * TILE_SIZE + INFO_BOX_HEIGHT;
    }


}

package com.gojek.academy.refactoring.game01.ui;

import javafx.scene.control.ToggleButton;
import javafx.scene.text.Font;

public class CardButton extends ToggleButton{

    private int row;
    private int column;

    public CardButton(int row, int column){
        this.row = row;
        this.column = column;

        setFont(Font.font ("Calibri", 18));
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }
}

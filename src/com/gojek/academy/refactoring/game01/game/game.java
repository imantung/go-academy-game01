package com.gojek.academy.refactoring.game01.game;

import java.util.Random;

// TODO: 1. Mind you language
public class game {

    private int total_card;
    private CardBoard Board;

    public game(int totalRow, int totalColumn){

        this.total_card = totalRow * totalColumn / 2;
        this.Board = new CardBoard(totalRow, totalColumn);

        System.out.println(total_card);
        System.out.println(Board);

        //TODO: 6. Feature Envy
        for(int i = 0; i < totalRow; i++){
            for(int j = 0; j < totalColumn; j++){
                Board.getMap()[i][j] = "x";
            }
        }

        // reshuffle cards
        Random random = new Random();

        for(int i = 0; i < this.total_card; i ++){

            for(int j = 0; j < 2; j++){
                int row;
                int column;


                while(true){
                    row = random.nextInt(totalRow);
                    column = random.nextInt(totalColumn);

                    //TODO: 6. Feature Envy
                    if(Board.getMap()[row][column] == "x"){
                        Board.getMap()[row][column] = i + "";
                        break;
                    }
                }
            }
        }
    }


    public int getTotal_card() {
        return total_card;
    }

    //TODO: 6. Feature Envy
    public String getCard(int row, int column){
        return Board.getMap()[row][column];
    }
}

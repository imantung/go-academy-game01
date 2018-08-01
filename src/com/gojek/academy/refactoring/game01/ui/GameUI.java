package com.gojek.academy.refactoring.game01.ui;

import com.gojek.academy.refactoring.game01.game.Game;
import com.gojek.academy.refactoring.game01.game.Sizer;
import com.gojek.academy.refactoring.game01.ui.event.GameEvent;
import com.gojek.academy.refactoring.game01.ui.event.GameLoseHandler;
import com.gojek.academy.refactoring.game01.ui.event.GameWinHandler;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.util.Optional;

public class GameUI extends BorderPane implements EventHandler<ActionEvent> {

    private int totalRow;
    private int totalColumn;
    private Game game;
    private CardButton[][] buttonBoard;
    private String currentCard;
    private CardButton prevCardButton;
    private int totalCard;
    private Label lblTotalCard;

    public GameUI(int totalRow, int totalColumn){

        this.totalRow = totalRow;
        this.totalColumn = totalColumn;

        this.game = new Game(totalRow, totalColumn);
        this.totalCard = game.getTotalCard();

        this.buttonBoard = new CardButton[totalRow][totalColumn];

        // TODO: a lazy class
        Sizer sizer = new Sizer();


        lblTotalCard = new Label();
        lblTotalCard.setText("Open '"+totalCard+"' number more");


        Button btnGiveUp = new Button();
        btnGiveUp.setText("Give Up");
        btnGiveUp.setOnAction((ActionEvent) -> {
            // TODO: 1. duplicate code
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Give Up");
            alert.setHeaderText("Are you sure to give up the game?");

            ButtonType buttonTypeYes = new ButtonType("Yes", ButtonBar.ButtonData.APPLY);
            ButtonType buttonTypeCancel = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

            alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeCancel);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == buttonTypeYes){
                fireEvent(new GameEvent(GameEvent.GAME_LOSE_TYPE));
            }
        });

        Button btnExit = new Button();
        btnExit.setText("Exit");
        btnExit.setOnAction((ActionEvent) ->{
            // TODO: 1. duplicate code
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Give Up");
            alert.setHeaderText("Are you sure to give up the game?");

            ButtonType buttonTypeYes = new ButtonType("Yes", ButtonBar.ButtonData.APPLY);
            ButtonType buttonTypeCancel = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

            alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeCancel);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == buttonTypeYes){
                fireEvent(new GameEvent(GameEvent.GAME_LOSE_TYPE));
            }
        });

        // info box
        HBox infoBox = new HBox();
        infoBox.setPadding(new Insets(15, 12, 15, 12));
        infoBox.setSpacing(10);
        infoBox.setPrefHeight(sizer.getInfoBoxHeight());
        infoBox.getChildren().add(btnGiveUp);
        infoBox.getChildren().add(btnExit);

        // grid
        GridPane gamePane = new GridPane();
        gamePane.setVgap(totalRow);
        gamePane.setHgap(totalColumn);



        for(int row = 0; row < totalRow; row++){
            for(int column = 0; column < totalColumn; column++){
                CardButton button = new CardButton(row, column);
                button.setOnAction(this);
                button.setPrefWidth(sizer.getTileSize());
                button.setPrefHeight(sizer.getTileSize());

                gamePane.add(button, column, row);

                buttonBoard[row][column] = button;
            }
        }


        // set to ui
        setTop(infoBox);
        setCenter(gamePane);
        setBottom(lblTotalCard);
    }

    @Override
    public void handle(ActionEvent event){
        CardButton button = (CardButton) event.getSource();

        if(button.isSelected()){
            int row = button.getRow();
            int column = button.getColumn();


            String cardValue = game.getCard(row, column);
            button.setText(cardValue);

            System.out.println(currentCard + " == " + cardValue);


            if(currentCard == null){
                currentCard = cardValue;
                prevCardButton = button;
            } else if(currentCard.equals(cardValue)){
                totalCard --;

                button.setDisable(true);
                prevCardButton.setDisable(true);

                lblTotalCard.setText("Open '"+totalCard+"' number more");

                if (totalCard <= 0){
                    fireEvent(new GameEvent(GameEvent.GAME_WIN_TYPE));
                }

                currentCard = null;
                prevCardButton = null;
            } else{
                System.out.println("not match");

                Thread thread = new Thread(() ->{
                    try{
                        Thread.sleep(500);
                    } catch(Exception e){
                    }

                    Platform.runLater(() ->{
                        resetBoard();
                    });

                });
                thread.start();

                currentCard = null;
                prevCardButton = null;
            }
        } else{
            button.setText("");
        }
    }

    private void resetBoard(){
        for(int row = 0; row < totalRow; row ++){
            for(int column = 0; column < totalColumn; column ++){

                CardButton button = buttonBoard[row][column];
                if(!button.isDisable()){
                    button.setSelected(false);
                    button.setText("");
                }
                button.setSelected(false);
            }
        }
    }

    public void setOnWinGame(GameWinHandler handler){
        setEventHandler(GameEvent.GAME_WIN_TYPE, event -> event.invokeWinGame(handler));
    }

    public void setOnLoseGame(GameLoseHandler handler){
        setEventHandler(GameEvent.GAME_LOSE_TYPE, event -> event.invokeLoseGame(handler));
    }
}

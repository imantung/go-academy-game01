package com.gojek.academy.refactoring.game01.ui;

import com.gojek.academy.refactoring.game01.game.game;
import com.gojek.academy.refactoring.game01.game.Sizer;
import com.gojek.academy.refactoring.game01.ui.event.GameEvent;
import com.gojek.academy.refactoring.game01.ui.event.GameLoseHandler;
import com.gojek.academy.refactoring.game01.ui.event.GameWinHandler;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;

public class GameUI extends BorderPane implements EventHandler<ActionEvent> {

    private int totalRow;
    private int totalColumn;
    private int timeCount;

    private com.gojek.academy.refactoring.game01.game.game game;
    private CardButton[][] buttonBoard;
    private String currentCard;
    private CardButton prevCardButton;
    private int totalCard;
    private Label lblTotalCard;
    private Label lblTimer;

    private Timer timer;


    public GameUI(int totalRow, int totalColumn, int timeCount0){
        this.timeCount = timeCount0;

        this.totalRow = totalRow;
        this.totalColumn = totalColumn;

        this.game = new game(totalRow, totalColumn);
        this.totalCard = game.getTotal_card();

        this.buttonBoard = new CardButton[totalRow][totalColumn];

        // TODO: 5. a lazy class
        Sizer sizer = new Sizer();

        lblTotalCard = new Label();
        lblTotalCard.setText("Open '"+totalCard+"' number more");

        Button btnGiveUp = new Button();
        btnGiveUp.setText("Give Up");
        btnGiveUp.setOnAction((ActionEvent) -> {
            // TODO: 3. duplicate code
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
        btnExit.setAlignment(Pos.CENTER_RIGHT);
        btnExit.setOnAction((ActionEvent) ->{
            // TODO: 3. duplicate code
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

        lblTimer = new Label();
        lblTimer.setFont(Font.font ("Calibri", 18));
        lblTimer.setText("Time count: " + timeCount);
        lblTimer.setPadding(new Insets(15, 12, 0, 12));

        timer = new Timer(true);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() ->{
                    System.out.println("111");
                    timeCount --;
                    lblTimer.setText("Time count: " + timeCount);

                    if(timeCount <= 0){
                        timer.cancel();
                        fireEvent(new GameEvent(GameEvent.GAME_LOSE_TYPE));
                    }
                });
            }
        }, 0 , 1000);

        // info box
        HBox hbox = new HBox();
        hbox.setPadding(new Insets(15, 12, 15, 12));
        hbox.setSpacing(10);

        hbox.getChildren().add(btnGiveUp);
        hbox.getChildren().add(btnExit);

        VBox vbox = new VBox();
        vbox.getChildren().add(lblTimer);
        vbox.getChildren().add(hbox);

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
        setTop(vbox);
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

            if(currentCard == null){
                currentCard = cardValue;
                prevCardButton = button;
            } else if(currentCard.equals(cardValue)){
                totalCard --;

                button.setDisable(true);
                prevCardButton.setDisable(true);

                lblTotalCard.setText("Open '"+totalCard+"' number more");

                if (totalCard <= 0){
                    timer.cancel();
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

package com.gojek.academy.refactoring.game01.ui;

import com.gojek.academy.refactoring.game01.ui.event.GameEvent;
import com.gojek.academy.refactoring.game01.ui.event.StartEvent;
import com.gojek.academy.refactoring.game01.ui.event.StartHandler;
import com.sun.tools.javadoc.Start;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class MainUI extends VBox {

    private Label lblInfo;

    public MainUI(){
        Button btnEasy = new Button();
        btnEasy.setFont(Font.font ("Calibri", 24));
        btnEasy.setText("Easy");
        btnEasy.setOnAction((ActionEvent) -> {
            // TODO: 2. magic number
            fireEvent(new StartEvent(2,3, (int) Math.round(2 * 3 * 1.5) ));
        });

        Button btnMedium = new Button();
        btnMedium.setFont(Font.font ("Calibri", 24));
        btnMedium.setText("Medium");
        btnMedium.setOnAction((ActionEvent) -> {
            // TODO: 2. magic number
            fireEvent(new StartEvent(3,4, (int) Math.round(3 * 4 * 1.5)));
        });

        Button btnHard = new Button();
        btnHard.setFont(Font.font ("Calibri", 24));
        btnHard.setText("Hard");
        btnHard.setOnAction((ActionEvent) -> {
            // TODO: 2. magic number
            fireEvent(new StartEvent(4,5, (int) Math.round(4 * 5 * 1.5)));
        });

        HBox ctrlBox = new HBox();
        ctrlBox.setPrefWidth(Double.MAX_VALUE);
        ctrlBox.setPrefHeight(120);
        ctrlBox.setAlignment(Pos.CENTER);

        ctrlBox.getChildren().add(btnEasy);
        ctrlBox.getChildren().add(btnMedium);
        ctrlBox.getChildren().add(btnHard);

        Label lblTitle = new Label();
        lblTitle.setPrefHeight(120);
        lblTitle.setFont(Font.font ("Times", 42));
        lblTitle.setAlignment(Pos.CENTER);
        lblTitle.setPrefWidth(Double.MAX_VALUE);
        lblTitle.setText("Memory game");

        lblInfo = new Label();
        lblInfo.setPrefWidth(Double.MAX_VALUE);
        lblInfo.setAlignment(Pos.CENTER);
        lblInfo.setFont(Font.font ("Calibri", 18));

        getChildren().add(lblTitle);
        getChildren().add(lblInfo);
        getChildren().add(ctrlBox);
    }

    public void setWin(){
        lblInfo.setText("You WIN!");
        lblInfo.setTextFill(Color.GREEN);
    }

    public void setLose(){
        lblInfo.setText("You LOSE!");
        lblInfo.setTextFill(Color.RED);
    }


    public void setOnPlay(final StartHandler handler){
        setEventHandler(StartEvent.START_TYPE, event -> event.invoke(handler));
    }
}

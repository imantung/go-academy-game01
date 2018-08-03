package com.gojek.academy.refactoring.game01;

import com.gojek.academy.refactoring.game01.game.Sizer;
import com.gojek.academy.refactoring.game01.ui.GameUI;
import com.gojek.academy.refactoring.game01.ui.MainUI;
import com.gojek.academy.refactoring.game01.ui.event.GameLoseHandler;
import com.gojek.academy.refactoring.game01.ui.event.StartHandler;
import com.gojek.academy.refactoring.game01.ui.event.GameWinHandler;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application implements StartHandler, GameWinHandler, GameLoseHandler{


    private MainUI mainUI;
    private Scene sceneMain;
    private Stage stage;


    @Override
    public void start(final Stage stage) {
        this.stage = stage;

        mainUI = new MainUI();
        mainUI.setOnPlay(this);

        sceneMain = new Scene(mainUI, 400, 300);

        stage.setTitle("Memory game");
        stage.setScene(sceneMain);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void onStart(int totalRow, int totalColumn, int timeCount) {
        GameUI gameUI = new GameUI(totalRow, totalColumn, timeCount);
        gameUI.setOnWinGame(this);
        gameUI.setOnLoseGame(this);


        // TODO: 3. remove the lazy class
        Sizer sizer = new Sizer();

        Scene sceneGame = new Scene(gameUI, sizer.getGameSceneWidth(totalColumn), sizer.getGameSceneHeight(totalRow));
        stage.setScene(sceneGame);
    }

    @Override
    public void onWinGame() {
        mainUI.setWin();
        stage.setScene(sceneMain);
    }

    @Override
    public void onLoseGame() {
        mainUI.setLose();
        stage.setScene(sceneMain);
    }
}
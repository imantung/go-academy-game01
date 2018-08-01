package com.gojek.academy.refactoring.game01.ui.event;

import javafx.event.Event;
import javafx.event.EventType;

public class GameEvent extends Event{


    public static final EventType<GameEvent> GAME_WIN_TYPE = new EventType<>("game_win");
    public static final EventType<GameEvent> GAME_LOSE_TYPE = new EventType<>("game_lose");


    public GameEvent( EventType<GameEvent> type){
        super(type);
    }




    public void invokeWinGame(GameWinHandler handler){
        handler.onWinGame();
    }

    public void invokeLoseGame(GameLoseHandler handler){
        handler.onLoseGame();
    }


}

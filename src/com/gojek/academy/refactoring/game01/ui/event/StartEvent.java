package com.gojek.academy.refactoring.game01.ui.event;

import javafx.event.Event;
import javafx.event.EventType;

public class StartEvent extends Event {
    public static final EventType<StartEvent> START_TYPE = new EventType<>("start");

    private int totalRow;
    private int totalColumn;

    public StartEvent(int totalRow, int totalColumn){
        super(START_TYPE);

        this.totalRow = totalRow;
        this.totalColumn = totalColumn;
    }

    public void invoke(StartHandler handler){
        handler.onStart(totalRow, totalColumn);
    }
}

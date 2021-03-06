package com.wix.gempuzzle.client.ui;

import com.wix.gempuzzle.api.Board;

public interface UI {
    void displayBoard(Board board);
    void displayCongratulationMessage();
    void displayInputMessage();
    void displayWrongMoveMessage();
    void displayUnknownMoveMessage();
}

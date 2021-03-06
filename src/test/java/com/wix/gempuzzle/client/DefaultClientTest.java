package com.wix.gempuzzle.client;

import com.wix.gempuzzle.api.GameStatus;
import com.wix.gempuzzle.api.Move;
import com.wix.gempuzzle.api.MoveResult;
import com.wix.gempuzzle.client.ui.UI;
import com.wix.gempuzzle.game.domain.*;
import com.wix.gempuzzle.game.Game;
import org.junit.Test;

import java.util.Optional;
import java.util.function.Supplier;

import static org.mockito.Mockito.*;

public class DefaultClientTest {
    UI ui = mock(UI.class);
    Game game = mock(Game.class);
    Supplier<Optional<Move>> userInput = mock(Supplier.class);
    Client client = new DefaultClient(ui, game, userInput);
    BoardDto board = new BoardDto(new TileDto[0][0], 0, 0);

    @Test(timeout = 100)
    public void shouldDisplayWonIfGameEngineReturnsWonStatus() throws Exception {
        when(userInput.get()).thenReturn(Optional.of(Move.UP));
        when(game.startNewGame()).thenReturn(board);
        when(game.handleMove(Move.UP)).thenReturn(new MoveResult(board, GameStatus.WON));

        client.playGame();

        verify(ui).displayBoard(board);
        verify(ui).displayInputMessage();
        verify(ui).displayCongratulationMessage();
        verifyNoMoreInteractions(ui);
    }

    @Test(timeout = 100)
    public void shouldDisplayUnknownActionIfUserTypedWrongLetter() throws Exception {
        when(userInput.get()).thenReturn(Optional.empty(),Optional.of(Move.RIGHT));
        when(game.startNewGame()).thenReturn(board);
        when(game.handleMove(Move.RIGHT)).thenReturn(new MoveResult(board, GameStatus.WON));

        client.playGame();

        verify(ui, times(2)).displayBoard(board);
        verify(ui, times(2)).displayInputMessage();
        verify(ui).displayUnknownMoveMessage();
        verify(ui).displayCongratulationMessage();
        verifyNoMoreInteractions(ui);
    }

    @Test(timeout = 100)
    public void shouldDisplayBadActionIfUserMadeWrongAction() throws Exception {
        when(userInput.get()).thenReturn(Optional.of(Move.UP), Optional.of(Move.LEFT));
        when(game.startNewGame()).thenReturn(board);
        when(game.handleMove(Move.UP)).thenReturn(new MoveResult(board, GameStatus.BAD_MOVE));
        when(game.handleMove(Move.LEFT)).thenReturn(new MoveResult(board, GameStatus.WON));

        client.playGame();

        verify(ui, times(2)).displayBoard(board);
        verify(ui, times(2)).displayInputMessage();
        verify(ui).displayWrongMoveMessage();
        verify(ui).displayCongratulationMessage();
        verifyNoMoreInteractions(ui);
    }

    @Test(timeout = 100)
    public void shouldProceedIfActionWasOk() throws Exception {
        when(userInput.get()).thenReturn(Optional.of(Move.UP), Optional.of(Move.DOWN));
        when(game.startNewGame()).thenReturn(board);
        when(game.handleMove(Move.UP)).thenReturn(new MoveResult(board, GameStatus.OK_MOVE));
        when(game.handleMove(Move.DOWN)).thenReturn(new MoveResult(board, GameStatus.WON));

        client.playGame();

        verify(ui, times(2)).displayBoard(board);
        verify(ui, times(2)).displayInputMessage();
        verify(ui).displayCongratulationMessage();
        verifyNoMoreInteractions(ui);
    }
}
package com.isi.java.tictactoe.view;

import com.isi.java.tictactoe.model.IGameModel;
import com.isi.java.tictactoe.model.PlayerSymbol;

public interface IGameView
{
	void updateTurn(PlayerSymbol player);

	void updateScore(PlayerSymbol player, int score);

	void updateSquare(PlayerSymbol player, int x, int y);

	void gameDraw();

	void gameWon(PlayerSymbol winner);

	void setPlayerName(String player1Name, String player2Name);

	void setModel(IGameModel model);

	void updateWinningStrikes(int x1, int y1, int x2, int y2, int x3, int y3);
}

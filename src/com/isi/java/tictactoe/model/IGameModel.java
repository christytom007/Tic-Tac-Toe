package com.isi.java.tictactoe.model;

public interface IGameModel
{
	void makeMove(int x, int y);

	void reset();

	void setPlayerName(String playerName);
}

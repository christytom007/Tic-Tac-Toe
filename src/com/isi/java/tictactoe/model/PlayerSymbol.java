package com.isi.java.tictactoe.model;

public enum PlayerSymbol
{
	X, O;

	public String value()
	{
		switch (this)
		{
		case X:
			return "X";
		case O:
			return "O";

		default:
			return "";
		}
	}
}

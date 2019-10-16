package com.isi.java.tictactoe.model;

public enum GameMode
{
	LOCAL_PVP, LOCAL_PVC, NETWORK_PVP_SERVER, NETWORK_PVP_CLIENT;

	public boolean isNetwork()
	{
		if (this == NETWORK_PVP_CLIENT || this == NETWORK_PVP_SERVER)
			return true;
		else
			return false;
	}
}

package com.isi.java.tictactoe.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import com.isi.java.tictactoe.model.IGameModel;

public class Client
{
	private PrintWriter out;
	private boolean isConnected;
	private IGameModel model;
	private int gameSize;

	private String playerName;

	public Client()
	{
		gameSize = 0;
		isConnected = false;
	}

	public void joinGame(String ipAddress)
	{
		new Thread(() -> {
			try (Socket serverSocket = new Socket(ipAddress, 4444))
			{
				isConnected = true;
				serverHandler(serverSocket);
			}
			catch (IOException e)
			{
				System.out.println("ERROR : Couldn't connect to server");
			}
		}).start();
	}

	public void setModel(IGameModel model)
	{
		this.model = model;
	}

	public boolean isConnected()
	{
		return isConnected;
	}

	public void updatePlayerDetails()
	{
		while (model == null)
			;
		model.setPlayerName(playerName);
	}

	public int getGameSize()
	{
		while (gameSize == 0)
			;
		return gameSize;
	}

	private void serverHandler(Socket serverSocket)
	{
		try
		{
			System.out.println("------------- Client -------------");
			BufferedReader in = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
			out = new PrintWriter(serverSocket.getOutputStream(), true);
			System.out.println("Client : Server connected");

			out.println("Client : Connected to server....");
			out.flush();

			String line;
			while ((line = in.readLine()) != null)
			{
				System.out.println(line);

				if (line.contains("!player1Name "))
				{
					playerName = line.substring(13, line.length());
				}
				if (line.contains("!gameSize "))
				{
					gameSize = Integer.parseInt(line.substring(10, line.length()));
				}

			}
		}
		catch (IOException e)
		{
			System.out.println("ERROR : Client Input Stream Error");
		}
	}

	public void sendMessage(String message)
	{
		out.println(message);
	}
}

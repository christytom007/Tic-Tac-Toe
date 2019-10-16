package com.isi.java.tictactoe.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import com.isi.java.tictactoe.model.IGameModel;

public class Server
{
	private PrintWriter out;
	private boolean isConnected;
	private IGameModel model;

	private String playerName;

	public Server()
	{
		isConnected = false;
	}

	public void hostGame()
	{
		Thread serverThread = new Thread(() -> {
			try (ServerSocket serverSocket = new ServerSocket(4444))
			{
				try
				{
					Socket clientSocket = serverSocket.accept();
					System.out.println("Client connected");
					isConnected = true;
					clientHandler(clientSocket);
				}
				catch (IOException e)
				{
					System.out.println("ERROR : Couldn't connect to Client");
				}
			}
			catch (IOException e)
			{
				System.out.println("ERROR : Couldn't create Server Socket");
			}

		});
		serverThread.start();
	}

	public void setModel(IGameModel model)
	{
		this.model = model;
	}

	public void updatePlayerDetails()
	{
		while (playerName == null)
			;
		model.setPlayerName(playerName);
	}

	public boolean isConnected()
	{
		return isConnected;
	}

	private void clientHandler(Socket clientSocket)
	{
		try
		{
			System.out.println("------------- Server -------------");
			BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			out = new PrintWriter(clientSocket.getOutputStream(), true);
			System.out.println("Server: Client connected");

			out.println("Server : Client connected....");

			String line;
			while ((line = in.readLine()) != null)
			{
				System.out.println(line);

				if (line.contains("!player2Name "))
				{
					playerName = line.substring(13, line.length());
				}

			}
		}
		catch (IOException e)
		{
			System.out.println("ERROR : Server Input Stream Error");
		}
	}

	public void sendMessage(String message)
	{
		out.println(message);
	}
}

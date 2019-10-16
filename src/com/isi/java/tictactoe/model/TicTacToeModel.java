package com.isi.java.tictactoe.model;

import java.util.Random;

import com.isi.java.tictactoe.network.Client;
import com.isi.java.tictactoe.network.Server;
import com.isi.java.tictactoe.view.IGameView;

public class TicTacToeModel implements IGameModel {
	private Random randomNumber; // Random number generator

	private IGameView view;
	private Server serverSocket;
	private Client clientSocket;

	private Square[][] grid; // Grid containing all details
	private GameMode gameMode; // Selected game mode
	private int gameSize; // selected game size
	private boolean hasWinner; // Winner flag
	private int noOfAvailableBoardPositions; // Total number of board positions
	private String player1Name = "";
	private String player2Name = "";
	private int player1Score;
	private int player2Score;
	private int tieScore;
	private PlayerSymbol player1;
	private PlayerSymbol player2;
	private GameLogFileHandler logFileHandler;
	private PlayerSymbol currentPlayer;

	/**
	 * Game Model Constructor
	 * 
	 * @param view
	 * @param gameMode
	 * @param gameSize
	 * @param player1Name
	 * @param player2Name
	 * @param player1
	 * @param player2
	 * @param logFileHandler
	 */
	public TicTacToeModel(IGameView view, GameMode gameMode, int gameSize, String player1Name, String player2Name,
			PlayerSymbol player1, PlayerSymbol player2, GameLogFileHandler logFileHandler) {
		this.view = view;
		this.gameMode = gameMode;
		this.gameSize = gameSize;

		this.player1 = player1;
		this.player2 = player2;

		if (player1Name.equals("")) {
			player1Name = "Player 1";
		}
		if (player2Name.equals("") && gameMode == GameMode.LOCAL_PVC) {
			player2Name = "Bot";
		}
		if (player2Name.equals("") && gameMode == GameMode.LOCAL_PVP) {
			player2Name = "Player 2";
		}

		this.player1Name = player1Name;
		this.player2Name = player2Name;

		view.setPlayerName(player1Name, player2Name);

		player1Score = 0;
		player2Score = 0;
		tieScore = 0;
		grid = new Square[gameSize][gameSize];
		this.logFileHandler = logFileHandler;

		logFileHandler.writeLog("Player selected game mode :" + gameMode.toString());
		logFileHandler.writeLog("Player selected Grid size :" + gameSize);
		logFileHandler.writeLog("Player 1 name : " + player1Name + "   Player 2 name : " + player2Name);
		logFileHandler.writeLog("------------------------------------------------------------------------------");
		logFileHandler.writeLog("Game initialized.");

		reset();
	}

	public void setServerSocket(Server serverSocket) {
		this.serverSocket = serverSocket;
	}

	public void setClientSocket(Client clientSocket) {
		this.clientSocket = clientSocket;
	}

	public void initNetworkMode() {

	}

	/**
	 * Reset the board and all the contents
	 */
	@Override
	public void reset() {
		hasWinner = false;
		noOfAvailableBoardPositions = gameSize * gameSize;
		for (int x = 0; x < grid.length; x++) {
			for (int y = 0; y < grid[x].length; y++) {
				grid[x][y] = Square.Empty;
			}
		}

		logFileHandler.writeLog("------------------------------------------------------------------------------");
		logFileHandler.writeLog("Game Restarted.");

		randomizeTurn();

		view.updateTurn(currentPlayer);

		if (gameMode == GameMode.LOCAL_PVC && currentPlayer == player2)
			advancedAITurn();
	}

	/**
	 * Randomize the players first turn
	 */
	private void randomizeTurn() {
		randomNumber = new Random(System.currentTimeMillis());
		int num = randomNumber.nextInt(100);
		if (num > 50) {
			currentPlayer = PlayerSymbol.X;
		} else {
			currentPlayer = PlayerSymbol.O;
		}
		logFileHandler.writeLog("First turn goes to '" + currentPlayer + "'.");
	}

	/**
	 * Simple AI play in Random Square
	 */
	private void simpleAITurn() {
		do {
			int x = randomNumber.nextInt(gameSize);
			int y = randomNumber.nextInt(gameSize);
			if (grid[x][y] == Square.Empty) {
				makeMove(x, y);
				break;
			}
		} while (true);
	}

	/**
	 * Advanced AI Algorithm with simple logical calculations
	 */
	private void advancedAITurn() {
		Square player1 = Square.valueOf(this.player1.value());
		Square player2 = Square.valueOf(this.player2.value());

		// Find the possible winning places for the AI
		for (int row = 0; row < gameSize - 2; row++) {
			for (int column = 0; column < gameSize; column++) {
				if (grid[row][column] == player2 && grid[row + 1][column] == Square.Empty
						&& grid[row + 2][column] == player2) {// Vertical
					makeMove(row + 1, column);
					return;
				} else if (grid[row][column] == Square.Empty && grid[row + 1][column] == player2
						&& grid[row + 2][column] == player2) {
					makeMove(row, column);
					return;
				} else if (grid[row][column] == player2 && grid[row + 1][column] == player2
						&& grid[row + 2][column] == Square.Empty) {
					makeMove(row + 2, column);
					return;
				}
			}
		}
		for (int row = 0; row < gameSize; row++) {
			for (int column = 0; column < gameSize - 2; column++) {
				if (grid[row][column] == player2 && grid[row][column + 1] == Square.Empty
						&& grid[row][column + 2] == player2) {// Horizontal
					makeMove(row, column + 1);
					return;
				} else if (grid[row][column] == Square.Empty && grid[row][column + 1] == player2
						&& grid[row][column + 2] == player2) {// Horizontal
					makeMove(row, column);
					return;
				} else if (grid[row][column] == player2 && grid[row][column + 1] == player2
						&& grid[row][column + 2] == Square.Empty) {// Horizontal
					makeMove(row, column + 2);
					return;
				}
			}
		}
		for (int row = 0; row < gameSize - 2; row++) {
			for (int column = 0; column < gameSize - 2; column++) {
				if (grid[row][column] == player2 && grid[row + 1][column + 1] == Square.Empty
						&& grid[row + 2][column + 2] == player2) {// Diagonal "\"
					makeMove(row + 1, column + 1);
					return;
				} else if (grid[row][column] == Square.Empty && grid[row + 1][column + 1] == player2
						&& grid[row + 2][column + 2] == player2) {// Diagonal "\"
					makeMove(row, column);
					return;
				} else if (grid[row][column] == player2 && grid[row + 1][column + 1] == player2
						&& grid[row + 2][column + 2] == Square.Empty) {// Diagonal "\"
					makeMove(row + 2, column + 2);
					return;
				}
			}
		}
		for (int row = 0; row < gameSize - 2; row++) {
			for (int column = 2; column < gameSize; column++) {
				if (grid[row][column] == player2 && grid[row + 1][column - 1] == Square.Empty
						&& grid[row + 2][column - 2] == player2) {// Diagonal "/"
					makeMove(row + 1, column - 1);
					return;
				} else if (grid[row][column] == Square.Empty && grid[row + 1][column - 1] == player2
						&& grid[row + 2][column - 2] == player2) {// Diagonal "/"
					makeMove(row, column);
					return;
				} else if (grid[row][column] == player2 && grid[row + 1][column - 1] == player2
						&& grid[row + 2][column - 2] == Square.Empty) {// Diagonal "/"
					makeMove(row + 2, column - 2);
					return;
				}
			}
		}

		// Finding possible blocking positions in the opposite player
		for (int row = 0; row < gameSize - 2; row++) {
			for (int column = 0; column < gameSize; column++) {
				if (grid[row][column] == player1 && grid[row + 1][column] == Square.Empty
						&& grid[row + 2][column] == player1) {// Vertical
					makeMove(row + 1, column);
					return;
				}
				if (grid[row][column] == Square.Empty && grid[row + 1][column] == player1
						&& grid[row + 2][column] == player1) {// Vertical
					makeMove(row, column);
					return;
				}
				if (grid[row][column] == player1 && grid[row + 1][column] == player1
						&& grid[row + 2][column] == Square.Empty) {// Vertical
					makeMove(row + 2, column);
					return;
				}
			}
		}
		for (int row = 0; row < gameSize; row++) {
			for (int column = 0; column < gameSize - 2; column++) {
				if (grid[row][column] == player1 && grid[row][column + 1] == Square.Empty
						&& grid[row][column + 2] == player1) {// Horizontal
					makeMove(row, column + 1);
					return;
				} else if (grid[row][column] == Square.Empty && grid[row][column + 1] == player1
						&& grid[row][column + 2] == player1) {// Horizontal
					makeMove(row, column);
					return;
				} else if (grid[row][column] == player1 && grid[row][column + 1] == player1
						&& grid[row][column + 2] == Square.Empty) {// Horizontal
					makeMove(row, column + 2);
					return;
				}
			}
		}
		for (int row = 0; row < gameSize - 2; row++) {
			for (int column = 0; column < gameSize - 2; column++) {
				if (grid[row][column] == player1 && grid[row + 1][column + 1] == Square.Empty
						&& grid[row + 2][column + 2] == player1) {// Diagonal "\"
					makeMove(row + 1, column + 1);
					return;
				} else if (grid[row][column] == Square.Empty && grid[row + 1][column + 1] == player1
						&& grid[row + 2][column + 2] == player1) {// Diagonal "\"
					makeMove(row, column);
					return;
				} else if (grid[row][column] == player1 && grid[row + 1][column + 1] == player1
						&& grid[row + 2][column + 2] == Square.Empty) {// Diagonal "\"
					makeMove(row + 2, column + 2);
					return;
				}
			}
		}
		for (int row = 0; row < gameSize - 2; row++) {
			for (int column = 2; column < gameSize; column++) {
				if (grid[row][column] == player1 && grid[row + 1][column - 1] == Square.Empty
						&& grid[row + 2][column - 2] == player1) {// Diagonal "/"
					makeMove(row + 1, column - 1);
					return;
				} else if (grid[row][column] == Square.Empty && grid[row + 1][column - 1] == player1
						&& grid[row + 2][column - 2] == player1) {// Diagonal "/"
					makeMove(row, column);
					return;
				} else if (grid[row][column] == player1 && grid[row + 1][column - 1] == player1
						&& grid[row + 2][column - 2] == Square.Empty) {// Diagonal "/"
					makeMove(row + 2, column - 2);
					return;
				}
			}
		}

		// if there is no possible winning or blocking positions then do a random move
		simpleAITurn();
	}

	/**
	 * Change turn
	 */
	private void togglePlayer() {
		if (currentPlayer == PlayerSymbol.X) {
			currentPlayer = PlayerSymbol.O;
		} else {
			currentPlayer = PlayerSymbol.X;
		}

		view.updateTurn(currentPlayer);

		logFileHandler.writeLog("Game turn changed to '" + currentPlayer + "'.");
	}

	private void addScore() {
		if (currentPlayer == player1) {
			if (!hasWinner) {
				player1Score += 10;
				logFileHandler.writeLog(currentPlayer + " got 10 points, Total : " + player1Score);
			} else {
				player1Score += 5;
				logFileHandler.writeLog(currentPlayer + " got 5 extra points, Total : " + player1Score);
			}
		} else {
			if (!hasWinner) {
				player2Score += 10;
				logFileHandler.writeLog(currentPlayer + " got 10 points, Total : " + player2Score);
			} else {
				player2Score += 5;
				logFileHandler.writeLog(currentPlayer + " got 5 extra points, Total : " + player2Score);
			}
		}
	}

	/**
	 * Method that find the winner & tie
	 * 
	 * @param currentPlayerConverted current player enum (from PlayerSymbol ->
	 *                               SquareSymbol)
	 * @return true if the method find a winner, else return false
	 */
	private boolean findWinner(Square currentPlayer) {
		for (int row = 0; row < gameSize - 2; row++) {
			for (int column = 0; column < gameSize; column++) {
				if (grid[row][column] == currentPlayer && grid[row + 1][column] == currentPlayer
						&& grid[row + 2][column] == currentPlayer) {// Vertical++++9*
					view.updateWinningStrikes(row, column, row + 1, column, row + 2, column);
					logFileHandler.writeLog(currentPlayer + " got a Strike in [" + row + "," + column + "],["
							+ (row + 1) + "," + column + "],[" + (row + 2) + "," + column + "] position.");
					addScore();
					hasWinner = true;
				}
			}
		}
		for (int row = 0; row < gameSize; row++) {
			for (int column = 0; column < gameSize - 2; column++) {
				if (grid[row][column] == currentPlayer && grid[row][column + 1] == currentPlayer
						&& grid[row][column + 2] == currentPlayer) {// Horizontal
					view.updateWinningStrikes(row, column, row, column + 1, row, column + 2);
					logFileHandler.writeLog(currentPlayer + " got a Strike in [" + row + "," + column + "],[" + row
							+ "," + (column + 1) + "],[" + row + "," + (column + 2) + "] position.");
					addScore();
					hasWinner = true;
				}
			}
		}
		for (int row = 0; row < gameSize - 2; row++) {
			for (int column = 0; column < gameSize - 2; column++) {
				if (grid[row][column] == currentPlayer && grid[row + 1][column + 1] == currentPlayer
						&& grid[row + 2][column + 2] == currentPlayer) {// Diagonal "\"
					view.updateWinningStrikes(row, column, row + 1, column + 1, row + 2, column + 2);
					logFileHandler.writeLog(currentPlayer + " got a Strike in [" + row + "," + column + "],["
							+ (row + 1) + "," + (column + 1) + "],[" + (row + 2) + "," + (column + 2) + "] position.");
					addScore();
					hasWinner = true;
				}
			}
		}
		for (int row = 0; row < gameSize - 2; row++) {
			for (int column = 2; column < gameSize; column++) {
				if (grid[row][column] == currentPlayer && grid[row + 1][column - 1] == currentPlayer
						&& grid[row + 2][column - 2] == currentPlayer) {// Diagonal "/"
					view.updateWinningStrikes(row, column, row + 1, column - 1, row + 2, column - 2);
					logFileHandler.writeLog(currentPlayer + " got a Strike in [" + row + "," + column + "],["
							+ (row + 1) + "," + (column - 1) + "],[" + (row + 2) + "," + (column - 2) + "] position.");
					addScore();
					hasWinner = true;
				}
			}
		}
		if (hasWinner) {
			logFileHandler.writeLog(currentPlayer + " Won the game!!");
			logFileHandler.writeLog("Player 1 Score : " + player1Score + " | Player 2 Score : " + player2Score);

			int score = (currentPlayer.value() == player1.value()) ? player1Score : player2Score;
			view.updateScore(this.currentPlayer, score);

			view.gameWon(this.currentPlayer);

			return true;
		}
		if (noOfAvailableBoardPositions == 0 && !hasWinner) {
			hasWinner = true;
			tieScore++;
			logFileHandler.writeLog("Game complete : Tie  ,Total Ties:" + tieScore);
			view.gameDraw();

			return true;
		}
		return false;
	}

	@Override
	public void makeMove(int x, int y) {
		if (grid[x][y] != Square.Empty || hasWinner)
			return;

		grid[x][y] = Square.valueOf(currentPlayer.value());
		view.updateSquare(currentPlayer, x, y);
		logFileHandler.writeLog(currentPlayer + " Selected grid position [" + x + "," + y + "].");
		noOfAvailableBoardPositions--;
		logFileHandler.writeLog("Number of Grid positions available : " + noOfAvailableBoardPositions);

		if (findWinner(grid[x][y]))
			return; // findWinner return true if the method Find that Game is complete, so that we
					// can skip the further steps

		togglePlayer();

		if (gameMode == GameMode.LOCAL_PVC && currentPlayer == player2)
			advancedAITurn();
	}

	@Override
	public void setPlayerName(String playerName) {
		if (gameMode == GameMode.NETWORK_PVP_SERVER) {
			view.setPlayerName(player1Name, playerName);
		} else {
			view.setPlayerName(playerName, player2Name);
		}
	}
}

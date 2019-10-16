/**
 * 
 */
package com.isi.java.tictactoe.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;

import com.isi.java.tictactoe.Main;
import com.isi.java.tictactoe.model.GameLogFileHandler;
import com.isi.java.tictactoe.model.GameMode;
import com.isi.java.tictactoe.model.IGameModel;
import com.isi.java.tictactoe.model.PlayerSymbol;

public class GameWindow extends JFrame implements IGameView
{
	private IGameModel model;

	private int gameSize; // Contains the currently playing Grid Size
	private PlayerSymbol currentPlayer; // Select the turn
	private boolean hasWinner; // Winner flag
	private GameMode selectedGameMode;
	private MainMenuWindow menuLink;
	private GameLogFileHandler logFileHandler;
	private PlayerSymbol player1;
	private PlayerSymbol player2;

	private JButton homeButton;
	private JButton soundButton;
	private JPanel homeSoundPanel;

	private JButton[][] gameBoardButton;
	private JPanel gameBoardPanel;

	private JLabel player1IconLabel;
	private JLabel player1ScoreLabel;
	private JPanel player1IconScorePanel;
	private JLabel player1NameLabel;
	private JPanel player1AllDetailsPanel;

	private JLabel tieIconLabel;
	private JLabel tieScoreLabel;
	private JPanel tiePanel;

	private JLabel player2IconLabel;
	private JLabel player2ScoreLabel;
	private JPanel player2IconScorePanel;
	private JLabel player2NameLabel;
	private JPanel player2AllDetailsPanel;

	private JPanel allPlayerDetailsPanel;

	private JPanel contentPane;

	public GameWindow(GameMode selectedGameMode, MainMenuWindow menuLink, int gameSize, PlayerSymbol player1, PlayerSymbol player2,
			GameLogFileHandler logFileHandler)
	{
		super("Tic Tac Toe - Lets Play");

		this.selectedGameMode = selectedGameMode;
		this.menuLink = menuLink;
		this.gameSize = gameSize;
		this.logFileHandler = logFileHandler;

		createComponents();
		addListenersToButton();
		setupContainers();
		addComponentsToContainers();

		this.player1 = player1;
		this.player2 = player2;
		hasWinner = false;

		pack();
		setIconImage(new ImageIcon(Main.class.getResource("res/tic-tac-toe.png")).getImage());
		setSize(600, 700);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				Main.playMusic(Main.class.getResourceAsStream("res/pause.wav"));
				int response = JOptionPane.showConfirmDialog(null, "Do you want to go Main menu?", "Go Home", JOptionPane.YES_NO_OPTION);
				Main.playMusic(Main.class.getResourceAsStream("res/pause2.wav"));
				if (response == JOptionPane.YES_OPTION)
				{
					GameWindow.this.menuLink.setVisible(true);
					logFileHandler.writeLog("Player chose to Close the game.");
					logFileHandler.closeLogFile();
					dispose();
				}
			}
		});
		setVisible(true);
	}

	public void createComponents()
	{
		Font commonBoardFont = new Font(Font.SANS_SERIF, Font.BOLD, 50);

		homeButton = new JButton("Home");
		homeButton.setAlignmentX(LEFT_ALIGNMENT);
		soundButton = new JButton("Sound");
		soundButton.setAlignmentX(RIGHT_ALIGNMENT);
		soundButton.setVisible(false);

		gameBoardButton = new JButton[gameSize][gameSize];
		for (int i = 0; i < gameSize; i++)
			for (int j = 0; j < gameSize; j++)
			{
				gameBoardButton[i][j] = new JButton();
				gameBoardButton[i][j].setFont(commonBoardFont);
				gameBoardButton[i][j].setFocusable(false);
			}

		player1IconLabel = new JLabel("X");
		player1IconLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 30));
		player1IconLabel.setForeground(Color.GREEN);
		player1ScoreLabel = new JLabel("0");
		player1ScoreLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
		player1NameLabel = new JLabel("Player 1");
		player1NameLabel.setAlignmentX(0.5f);

		tieIconLabel = new JLabel("Tie");
		tieIconLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
		tieIconLabel.setForeground(Color.BLUE);
		tieIconLabel.setAlignmentX(CENTER_ALIGNMENT);
		tieScoreLabel = new JLabel("0");
		tieScoreLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
		tieScoreLabel.setAlignmentX(CENTER_ALIGNMENT);

		player2IconLabel = new JLabel("O");
		player2IconLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 30));
		player2IconLabel.setForeground(Color.ORANGE);
		player2ScoreLabel = new JLabel("0");
		player2ScoreLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
		player2NameLabel = new JLabel("Player 2");
		player2NameLabel.setAlignmentX(0.5f);
	}

	public void addListenersToButton()
	{
		for (int i = 0; i < gameSize; i++)
			for (int j = 0; j < gameSize; j++)
			{
				gameBoardButton[i][j].addActionListener(new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e)
					{
						JButton btn = (JButton) e.getSource();

						for (int i = 0; i < gameSize; i++)
							for (int j = 0; j < gameSize; j++)
								if (gameBoardButton[i][j] == btn)
								{
									model.makeMove(i, j);
									break;
								}
					}
				});
			}

		homeButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				GameWindow.this.dispatchEvent(new WindowEvent(GameWindow.this, WindowEvent.WINDOW_CLOSING));
			}
		});
	}

	public void setupContainers()
	{
		homeSoundPanel = new JPanel();
		homeSoundPanel.setLayout(new BoxLayout(homeSoundPanel, BoxLayout.LINE_AXIS));
		homeSoundPanel.setBorder(new EmptyBorder(0, 0, 10, 0));

		gameBoardPanel = new JPanel();
		gameBoardPanel.setLayout(new GridLayout(gameSize, gameSize, 5, 5));
		gameBoardPanel.setBorder(new CompoundBorder(new EtchedBorder(), new EmptyBorder(20, 20, 20, 20)));

		player1IconScorePanel = new JPanel();
		player1IconScorePanel.setLayout(new BoxLayout(player1IconScorePanel, BoxLayout.X_AXIS));
		player1IconScorePanel.setBorder(new EmptyBorder(0, 0, 0, 5));
		player1AllDetailsPanel = new JPanel();
		player1AllDetailsPanel.setSize(new Dimension(50, 50));
		player1AllDetailsPanel.setLayout(new BoxLayout(player1AllDetailsPanel, BoxLayout.Y_AXIS));

		tiePanel = new JPanel();
		tiePanel.setLayout(new BoxLayout(tiePanel, BoxLayout.Y_AXIS));

		player2IconScorePanel = new JPanel();
		player2IconScorePanel.setLayout(new BoxLayout(player2IconScorePanel, BoxLayout.X_AXIS));
		player2IconScorePanel.setBorder(new EmptyBorder(0, 0, 0, 5));
		player2AllDetailsPanel = new JPanel();
		player2AllDetailsPanel.setLayout(new BoxLayout(player2AllDetailsPanel, BoxLayout.Y_AXIS));

		allPlayerDetailsPanel = new JPanel();
		allPlayerDetailsPanel.setLayout(new BoxLayout(allPlayerDetailsPanel, BoxLayout.X_AXIS));
		allPlayerDetailsPanel.setBorder(new CompoundBorder(new EtchedBorder(), new EmptyBorder(10, 10, 10, 10)));

		contentPane = (JPanel) getContentPane();
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		contentPane.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
	}

	public void addComponentsToContainers()
	{
		homeSoundPanel.add(homeButton);
		homeSoundPanel.add(Box.createHorizontalGlue());
		homeSoundPanel.add(soundButton);

		contentPane.add(homeSoundPanel);

		for (int y = 0; y < gameSize; y++)
		{
			for (int x = 0; x < gameSize; x++)
			{
				gameBoardPanel.add(gameBoardButton[x][y]);
			}
		}
		contentPane.add(gameBoardPanel);

		contentPane.add(Box.createRigidArea(new Dimension(0, 10)));

		player1IconScorePanel.add(player1IconLabel);
		player1IconScorePanel.add(Box.createRigidArea(new Dimension(5, 0)));
		player1IconScorePanel.add(new JLabel(":"));
		player1IconScorePanel.add(Box.createRigidArea(new Dimension(5, 0)));
		player1IconScorePanel.add(player1ScoreLabel);
		player1AllDetailsPanel.add(player1IconScorePanel);
		player1AllDetailsPanel.add(player1NameLabel);
		player1AllDetailsPanel.add(Box.createRigidArea(new Dimension(80, 1)));
		allPlayerDetailsPanel.add(player1AllDetailsPanel);

		allPlayerDetailsPanel.add(Box.createRigidArea(new Dimension(70, 0)));

		tiePanel.add(tieIconLabel);
		tiePanel.add(tieScoreLabel);
		allPlayerDetailsPanel.add(tiePanel);

		allPlayerDetailsPanel.add(Box.createRigidArea(new Dimension(70, 0)));

		player2IconScorePanel.add(player2IconLabel);
		player2IconScorePanel.add(Box.createRigidArea(new Dimension(5, 0)));
		player2IconScorePanel.add(new JLabel(":"));
		player2IconScorePanel.add(Box.createRigidArea(new Dimension(5, 0)));
		player2IconScorePanel.add(player2ScoreLabel);
		player2AllDetailsPanel.add(player2IconScorePanel);
		player2AllDetailsPanel.add(player2NameLabel);
		player2AllDetailsPanel.add(Box.createRigidArea(new Dimension(80, 1)));
		allPlayerDetailsPanel.add(player2AllDetailsPanel);

		contentPane.add(allPlayerDetailsPanel);
	}

	@Override
	public void updateTurn(PlayerSymbol player)
	{
		currentPlayer = player;
		if (player == player1)
		{
			player1AllDetailsPanel.setBorder(new LineBorder(Color.blue, 1, true));
			player2AllDetailsPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
		}
		else
		{
			player2AllDetailsPanel.setBorder(new LineBorder(Color.blue, 1, true));
			player1AllDetailsPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
		}

	}

	@Override
	public void updateScore(PlayerSymbol player, int score)
	{
		if (player == player1)
			player1ScoreLabel.setText(Integer.toString(score));
		else
			player2ScoreLabel.setText(Integer.toString(score));
	}

	@Override
	public void updateSquare(PlayerSymbol player, int x, int y)
	{
		if (player.value() == "X")
		{
			Main.playMusic(Main.class.getResourceAsStream("res/click1.wav"));
			gameBoardButton[x][y].setForeground(Color.GREEN);
			gameBoardButton[x][y].setText(player.value());
		}
		else
		{
			Main.playMusic(Main.class.getResourceAsStream("res/click2.wav"));
			gameBoardButton[x][y].setForeground(Color.ORANGE);
			gameBoardButton[x][y].setText(player.value());
		}
	}

	@Override
	public void gameDraw()
	{
		hasWinner = true;
		Main.playMusic(Main.class.getResourceAsStream("res/tie.wav"));
		JOptionPane.showMessageDialog(this, "Its a Tie", "Result", JOptionPane.INFORMATION_MESSAGE);
		int tieScore = Integer.parseInt(tieScoreLabel.getText());
		tieScore++;
		tieScoreLabel.setText(Integer.toString(tieScore));
		gameCompleteResponse();
	}

	@Override
	public void gameWon(PlayerSymbol winner)
	{
		hasWinner = true;
		String winnerName;
		if (winner == player1)
		{
			winnerName = player1NameLabel.getText();
			Main.playMusic(Main.class.getResourceAsStream("res/win.wav"));
		}
		else
		{
			winnerName = player2NameLabel.getText();
			Main.playMusic(Main.class.getResourceAsStream("res/loss.wav"));
		}
		JOptionPane.showMessageDialog(this, winnerName + " has Won!!", "Result", JOptionPane.INFORMATION_MESSAGE);
		gameCompleteResponse();
	}

	@Override
	public void setPlayerName(String player1Name, String player2Name)
	{
		player1NameLabel.setText(player1Name);
		player2NameLabel.setText(player2Name);
	}

	@Override
	public void setModel(IGameModel model)
	{
		this.model = model;
	}

	private void resetGame()
	{
		hasWinner = false;
		for (int i = 0; i < gameSize; i++)
			for (int j = 0; j < gameSize; j++)
			{
				gameBoardButton[i][j].setText("");
				gameBoardButton[i][j].setBackground(new JButton().getBackground());
			}
	}

	private void gameCompleteResponse()
	{
		Main.playMusic(Main.class.getResourceAsStream("res/pause.wav"));
		int response = JOptionPane.showConfirmDialog(this, "Do you want to continue playing?", "Continue", JOptionPane.YES_NO_OPTION);
		Main.playMusic(Main.class.getResourceAsStream("res/pause2.wav"));
		if (response == JOptionPane.YES_OPTION)
		{
			resetGame();
			model.reset();
		}
		else
		{
			GameWindow.this.menuLink.setVisible(true);
			logFileHandler.writeLog("Player chose to Close the game.");
			logFileHandler.closeLogFile();
			dispose();
		}
	}

	@Override
	public void updateWinningStrikes(int x1, int y1, int x2, int y2, int x3, int y3)
	{
		gameBoardButton[x1][y1].setBackground(Color.DARK_GRAY);
		gameBoardButton[x2][y2].setBackground(Color.DARK_GRAY);
		gameBoardButton[x3][y3].setBackground(Color.DARK_GRAY);
	}
}

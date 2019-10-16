package com.isi.java.tictactoe.view;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;

import com.isi.java.tictactoe.Main;
import com.isi.java.tictactoe.model.GameLogFileHandler;
import com.isi.java.tictactoe.model.GameMode;
import com.isi.java.tictactoe.model.PlayerSymbol;
import com.isi.java.tictactoe.model.TicTacToeModel;

public class MainMenuWindow extends JFrame
{
	private int gameSize;
	private GameMode selectedGameMode;
	private String player1Name;
	private String player2Name;

	private JPanel gameLogoPanel;
	private JLabel gameLogoLabel;
	private JLabel boardLabel;
	private JPanel boardSizePanel;
	private ButtonGroup gridSizeButtonGroup;
	private JToggleButton ToggleButton3x3;
	private JToggleButton ToggleButton4x4;
	private JToggleButton ToggleButton5x5;
	private JLabel playersLabel;
	private JPanel gameModePanel;
	private ButtonGroup playersButtonGroup;
	private JToggleButton onePlayerToggleButton;
	private JToggleButton twoPlayerToggleButton;
	private JButton playButton;
	private JButton multiplayerButton;
	private JButton aboutButton;
	private JButton creditsButton;
	private JLabel backgroundImageLabel;

	private JPanel contentPane;

	public MainMenuWindow()
	{
		super("Tic Tac Toe");

		createComponents();
		addListenersToButtons();
		setUpContainers();
		addComponentsToContainers();

		gameSize = 3;
		selectedGameMode = GameMode.LOCAL_PVP;
		player1Name = "";
		player2Name = "";

		setSize(600, 650);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				Main.playMusic(Main.class.getResourceAsStream("res/pause.wav"));
				int response = JOptionPane.showConfirmDialog(null, "Do you want to Quit the Game ?", "Exit", JOptionPane.YES_NO_OPTION);
				Main.playMusic(Main.class.getResourceAsStream("res/pause2.wav"));
				if (response == JOptionPane.YES_OPTION) System.exit(0);
			}
		});
		ImageIcon icon = new ImageIcon(Main.class.getResource("res/tic-tac-toe.png"));
		setIconImage(icon.getImage());
		setVisible(true);
	}

	private void createComponents()
	{
		gameLogoLabel = new JLabel("");
		gameLogoLabel.setIcon(new ImageIcon(Main.class.getResource("res/gameLogo.png")));
		gameLogoLabel.setHorizontalAlignment(SwingConstants.CENTER);

		boardLabel = new JLabel("Board Size");
		boardLabel.setHorizontalAlignment(SwingConstants.CENTER);
		boardLabel.setForeground(Color.WHITE);
		boardLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
		boardLabel.setBounds(36, 139, 126, 90);

		gridSizeButtonGroup = new ButtonGroup();
		ToggleButton3x3 = new JToggleButton("3x3");
		ToggleButton3x3.setForeground(new Color(0, 0, 255));
		ToggleButton3x3.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		ToggleButton3x3.setSelected(true);
		gridSizeButtonGroup.add(ToggleButton3x3);

		ToggleButton4x4 = new JToggleButton("4x4");
		ToggleButton4x4.setForeground(new Color(0, 0, 153));
		ToggleButton4x4.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		gridSizeButtonGroup.add(ToggleButton4x4);

		ToggleButton5x5 = new JToggleButton("5x5");
		ToggleButton5x5.setForeground(new Color(102, 0, 0));
		ToggleButton5x5.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		gridSizeButtonGroup.add(ToggleButton5x5);

		playersLabel = new JLabel("Players");
		playersLabel.setHorizontalAlignment(SwingConstants.CENTER);
		playersLabel.setForeground(Color.WHITE);
		playersLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
		playersLabel.setBounds(36, 241, 126, 90);

		playersButtonGroup = new ButtonGroup();
		onePlayerToggleButton = new JToggleButton("P Vs AI");
		onePlayerToggleButton.setForeground(new Color(204, 0, 255));
		onePlayerToggleButton.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		playersButtonGroup.add(onePlayerToggleButton);

		twoPlayerToggleButton = new JToggleButton("P Vs P");
		twoPlayerToggleButton.setForeground(new Color(204, 0, 51));
		twoPlayerToggleButton.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		twoPlayerToggleButton.setSelected(true);
		playersButtonGroup.add(twoPlayerToggleButton);

		playButton = new JButton("PLAY");
		playButton.setForeground(new Color(0, 0, 255));
		playButton.setFont(new Font("Segoe UI", Font.BOLD, 20));
		playButton.setBounds(198, 355, 200, 55);

		multiplayerButton = new JButton("MULTIPLAYER");
		multiplayerButton.setForeground(new Color(204, 0, 0));
		multiplayerButton.setFont(new Font("Segoe UI", Font.BOLD, 20));
		multiplayerButton.setBounds(198, 420, 200, 55);

		aboutButton = new JButton("ABOUT");
		aboutButton.setForeground(new Color(0, 0, 102));
		aboutButton.setFont(new Font("Segoe UI", Font.BOLD, 20));
		aboutButton.setBounds(198, 485, 200, 55);

		creditsButton = new JButton("CREDITS");
		creditsButton.setForeground(new Color(0, 0, 102));
		creditsButton.setFont(new Font("Segoe UI", Font.BOLD, 20));
		creditsButton.setBounds(198, 550, 200, 55);

		backgroundImageLabel = new JLabel("");
		backgroundImageLabel.setIcon(new ImageIcon(Main.class.getResource("res/menuBG.jpg")));
		backgroundImageLabel.setBounds(0, 0, 596, 622);

	}

	private void addListenersToButtons()
	{
		playButton.addActionListener((ActionEvent e) -> {
			Main.playMusic(Main.class.getResourceAsStream("res/play.wav"));
			if (player1Name.equals(""))
			{
				player1Name = JOptionPane.showInputDialog(null, "Please enter Player 1 name.", "Player 1 Name", JOptionPane.INFORMATION_MESSAGE);
				if (player1Name == null) player1Name = "";
			}
			if (player2Name.equals("") && selectedGameMode == GameMode.LOCAL_PVP)
			{
				player2Name = JOptionPane.showInputDialog(null, "Please enter Player 2 name.", "Player 2 Name", JOptionPane.INFORMATION_MESSAGE);
				if (player2Name == null) player2Name = "";
			}

			GameLogFileHandler logFileHandler = new GameLogFileHandler();
			GameWindow gameWindow = new GameWindow(selectedGameMode, this, gameSize, PlayerSymbol.X, PlayerSymbol.O, logFileHandler);
			TicTacToeModel ticTacToeModel = new TicTacToeModel(gameWindow, selectedGameMode, gameSize, player1Name, player2Name, PlayerSymbol.X,
					PlayerSymbol.O, logFileHandler);
			gameWindow.setModel(ticTacToeModel);
			setVisible(false);
		});

		multiplayerButton.addActionListener((ActionEvent e) -> {
			Main.playMusic(Main.class.getResourceAsStream("res/beep.wav"));
			MultiplayerWindow multiplayerWindow = new MultiplayerWindow(this);
			setVisible(false);
		});

		aboutButton.addActionListener((ActionEvent e) -> {
			Main.playMusic(Main.class.getResourceAsStream("res/beep.wav"));
			AboutWindow aboutWindow = new AboutWindow(this);
			setVisible(false);
		});

		creditsButton.addActionListener((ActionEvent e) -> {
			Main.playMusic(Main.class.getResourceAsStream("res/beep.wav"));
			CreditsWindow creditsWindow = new CreditsWindow(this);
			setVisible(false);
		});

		ToggleButton3x3.addActionListener((ActionEvent e) -> {
			Main.playMusic(Main.class.getResourceAsStream("res/toggle.wav"));
			gameSize = 3;
		});
		ToggleButton4x4.addActionListener((ActionEvent e) -> {
			Main.playMusic(Main.class.getResourceAsStream("res/toggle.wav"));
			gameSize = 4;
		});
		ToggleButton5x5.addActionListener((ActionEvent e) -> {
			Main.playMusic(Main.class.getResourceAsStream("res/toggle.wav"));
			gameSize = 5;
		});
		onePlayerToggleButton.addActionListener((ActionEvent e) -> {
			Main.playMusic(Main.class.getResourceAsStream("res/toggle2.wav"));
			selectedGameMode = GameMode.LOCAL_PVC;
		});
		twoPlayerToggleButton.addActionListener((ActionEvent e) -> {
			Main.playMusic(Main.class.getResourceAsStream("res/toggle2.wav"));
			selectedGameMode = GameMode.LOCAL_PVP;
		});

	}

	private void setUpContainers()
	{

		gameLogoPanel = new JPanel();
		gameLogoPanel.setBounds(10, 10, 580, 108);
		gameLogoPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		gameLogoPanel.setOpaque(false);

		boardSizePanel = new JPanel();
		boardSizePanel.setBounds(174, 139, 300, 90);
		boardSizePanel.setLayout(new GridLayout(0, 3, 10, 0));
		boardSizePanel.setOpaque(false);

		gameModePanel = new JPanel();
		gameModePanel.setBounds(174, 241, 300, 90);
		gameModePanel.setLayout(new GridLayout(0, 2, 10, 0));
		gameModePanel.setOpaque(false);

		contentPane = (JPanel) getContentPane();
		contentPane.setLayout(null);
	}

	private void addComponentsToContainers()
	{

		contentPane.add(gameLogoPanel);
		gameLogoPanel.add(gameLogoLabel);
		contentPane.add(boardLabel);
		contentPane.add(boardSizePanel);
		boardSizePanel.add(ToggleButton3x3);
		boardSizePanel.add(ToggleButton4x4);
		boardSizePanel.add(ToggleButton5x5);
		contentPane.add(playersLabel);
		contentPane.add(gameModePanel);
		gameModePanel.add(onePlayerToggleButton);
		gameModePanel.add(twoPlayerToggleButton);
		contentPane.add(playButton);
		contentPane.add(multiplayerButton);
		contentPane.add(aboutButton);
		contentPane.add(creditsButton);
		contentPane.add(backgroundImageLabel);
	}
}

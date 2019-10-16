package com.isi.java.tictactoe.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.regex.Pattern;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;

import com.isi.java.tictactoe.Main;
import com.isi.java.tictactoe.model.GameLogFileHandler;
import com.isi.java.tictactoe.model.GameMode;
import com.isi.java.tictactoe.model.PlayerSymbol;
import com.isi.java.tictactoe.model.TicTacToeModel;
import com.isi.java.tictactoe.network.Client;
import com.isi.java.tictactoe.network.Server;

public class MultiplayerWindow extends JFrame
{

	private MainMenuWindow menuLink;

	private int gameSize;

	private JLabel nameLabel;
	private JButton hostButton;
	private JButton joinButton;
	private JLabel hostAddressLabel;
	private JLabel ipAddressLabel;
	private JButton goBackButton;
	private JLabel gameSizeLabel;
	private JPanel gameSizePanel;
	private JToggleButton toggleButton3x3;
	private JToggleButton toggleButton4x4;
	private JToggleButton toggleButton5x5;
	private JTextField nameTextField;
	private JTextField ipaddressTextField;

	private JPanel contentPane;

	public MultiplayerWindow(MainMenuWindow mainLink)
	{
		super("Tic Tac Toe - Multiplayer Menu");

		this.menuLink = mainLink;
		gameSize = 3;

		createComponents();
		addListenersToButtons();
		setUpContainers();
		addComponentsToContainers();

		setSize(600, 650);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				mainLink.setVisible(true);
				dispose();
			}
		});
		ImageIcon icon = new ImageIcon(Main.class.getResource("res/tic-tac-toe.png"));
		setIconImage(icon.getImage());
		setVisible(true);
	}

	private void createComponents()
	{
		nameLabel = new JLabel("Your Name :");
		nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
		nameLabel.setBounds(28, 97, 147, 34);

		nameTextField = new JTextField();
		nameTextField.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		nameTextField.setBounds(179, 97, 300, 34);
		nameTextField.setColumns(10);

		hostButton = new JButton("Host a Game");
		hostButton.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		hostButton.setBounds(200, 219, 185, 55);

		joinButton = new JButton("Join a Game");
		joinButton.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		joinButton.setBounds(200, 461, 185, 55);

		hostAddressLabel = new JLabel("-----------");
		hostAddressLabel.setHorizontalAlignment(SwingConstants.CENTER);
		hostAddressLabel.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		hostAddressLabel.setBounds(100, 284, 393, 34);

		ipAddressLabel = new JLabel("IP Address :");
		ipAddressLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
		ipAddressLabel.setBounds(28, 393, 147, 34);

		ipaddressTextField = new JTextField();
		ipaddressTextField.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		ipaddressTextField.setColumns(10);
		ipaddressTextField.setBounds(179, 396, 300, 34);

		goBackButton = new JButton("Go Back");
		goBackButton.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		goBackButton.setBounds(10, 10, 100, 48);

		gameSizeLabel = new JLabel("Game Size");
		gameSizeLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
		gameSizeLabel.setBounds(28, 158, 147, 34);

		ButtonGroup gameSizeGroup = new ButtonGroup();
		toggleButton3x3 = new JToggleButton("3x3");
		toggleButton3x3.setFont(new Font("Segoe UI", Font.BOLD, 15));
		gameSizeGroup.add(toggleButton3x3);
		toggleButton3x3.setSelected(true);

		toggleButton4x4 = new JToggleButton("4x4");
		toggleButton4x4.setFont(new Font("Segoe UI", Font.BOLD, 15));
		gameSizeGroup.add(toggleButton4x4);

		toggleButton5x5 = new JToggleButton("5x5");
		toggleButton5x5.setFont(new Font("Segoe UI", Font.BOLD, 15));
		gameSizeGroup.add(toggleButton5x5);
	}

	private void addListenersToButtons()
	{
		goBackButton.addActionListener((ActionEvent e) -> {
			Main.playMusic(Main.class.getResourceAsStream("res/beep.wav"));
			this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
		});
		toggleButton3x3.addActionListener((ActionEvent e) -> {
			Main.playMusic(Main.class.getResourceAsStream("res/toggle.wav"));
			gameSize = 3;
		});
		toggleButton4x4.addActionListener((ActionEvent e) -> {
			Main.playMusic(Main.class.getResourceAsStream("res/toggle.wav"));
			gameSize = 4;
		});
		toggleButton5x5.addActionListener((ActionEvent e) -> {
			Main.playMusic(Main.class.getResourceAsStream("res/toggle.wav"));
			gameSize = 5;
		});

		hostButton.addActionListener((ActionEvent e) -> { // Host
			Main.playMusic(Main.class.getResourceAsStream("res/beep.wav"));

			Server gameServer = new Server();
			try
			{
				InetAddress address = InetAddress.getLocalHost();
				hostAddressLabel.setText("Connect to : " + address);
			}
			catch (UnknownHostException e1)
			{
				e1.printStackTrace();
			}
			gameServer.hostGame();

//			do
//			{
//				try
//				{
//					Thread.sleep(500);
//				}
//				catch (InterruptedException e1)
//				{
//					e1.printStackTrace();
//				}
//			}
//			while (!gameServer.isConnected());
//			if (!gameServer.isConnected())
//			{
//				JOptionPane.showMessageDialog(null, "Unable to establish the connection", "ERROR", JOptionPane.WARNING_MESSAGE);
//				return;
//			}

			String playerName = nameTextField.getText();
			if (playerName == null || playerName.equals("")) playerName = "Player 1";
			gameServer.sendMessage("!player1Name " + playerName);
			gameServer.sendMessage("!gameSize " + gameSize);

			GameLogFileHandler logFileHandler = new GameLogFileHandler();
			GameWindow gameWindow = new GameWindow(GameMode.NETWORK_PVP_SERVER, menuLink, gameSize, PlayerSymbol.X, PlayerSymbol.O, logFileHandler);
			TicTacToeModel ticTacToeModel = new TicTacToeModel(gameWindow, GameMode.NETWORK_PVP_SERVER, gameSize, playerName, "", PlayerSymbol.X,
					PlayerSymbol.O, logFileHandler);
			gameWindow.setModel(ticTacToeModel);

			ticTacToeModel.setServerSocket(gameServer);
			ticTacToeModel.initNetworkMode();

			gameServer.updatePlayerDetails();

			setVisible(false);

		});

		joinButton.addActionListener((ActionEvent e) -> {// Client
			Main.playMusic(Main.class.getResourceAsStream("res/beep.wav"));

			String playerName = nameTextField.getText();
			if (playerName == null || playerName.equals("")) playerName = "Player 2";

			Pattern ipPattern = Pattern.compile("^" + "(localhost" // localhost
					+ "|" + "(([0-9]{1,3}\\.){3})[0-9]{1,3})" // Ip
			); // Port

			String ip = ipaddressTextField.getText();
			if (ip == null || !ipPattern.matcher(ip).matches())
			{
				JOptionPane.showMessageDialog(null, "Invalid IP Address", "ERROR", JOptionPane.WARNING_MESSAGE);
				return;
			}

			Client gameClient = new Client();
			gameClient.joinGame(ip);

			do
			{
				try
				{
					Thread.sleep(500);
				}
				catch (InterruptedException e1)
				{
					e1.printStackTrace();
				}
			}
			while (!gameClient.isConnected());

			if (!gameClient.isConnected())
			{
				JOptionPane.showMessageDialog(null, "Unable to establish the connection", "ERROR", JOptionPane.WARNING_MESSAGE);
				return;
			}

			gameClient.sendMessage("!player2Name " + playerName);
			gameSize = gameClient.getGameSize();

			GameLogFileHandler logFileHandler = new GameLogFileHandler();
			GameWindow gameWindow = new GameWindow(GameMode.NETWORK_PVP_CLIENT, menuLink, gameSize, PlayerSymbol.X, PlayerSymbol.O, logFileHandler);
			TicTacToeModel ticTacToeModel = new TicTacToeModel(gameWindow, GameMode.NETWORK_PVP_CLIENT, gameSize, "", playerName, PlayerSymbol.X,
					PlayerSymbol.O, logFileHandler);

			gameWindow.setModel(ticTacToeModel);
			ticTacToeModel.setClientSocket(gameClient);
			ticTacToeModel.initNetworkMode();

			gameClient.updatePlayerDetails();

			setVisible(false);

		});

	}

	private void setUpContainers()
	{
		gameSizePanel = new JPanel();
		gameSizePanel.setBounds(179, 158, 300, 34);
		gameSizePanel.setLayout(new GridLayout(1, 0, 0, 0));
		gameSizePanel.setOpaque(false);

		contentPane = (JPanel) getContentPane();
		contentPane.setLayout(null);
		contentPane.setBackground(Color.GRAY);
	}

	private void addComponentsToContainers()
	{
		contentPane.add(goBackButton);

		contentPane.add(nameLabel);
		contentPane.add(nameTextField);

		contentPane.add(gameSizeLabel);
		gameSizePanel.add(toggleButton3x3);
		gameSizePanel.add(toggleButton4x4);
		gameSizePanel.add(toggleButton5x5);
		contentPane.add(gameSizePanel);

		contentPane.add(hostButton);
		contentPane.add(hostAddressLabel);

		JSeparator separator = new JSeparator();
		separator.setBounds(0, 349, 586, 9);
		contentPane.add(separator);

		contentPane.add(joinButton);
		contentPane.add(ipAddressLabel);
		contentPane.add(ipaddressTextField);
	}
}

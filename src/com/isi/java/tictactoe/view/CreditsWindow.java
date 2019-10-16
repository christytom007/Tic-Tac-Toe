package com.isi.java.tictactoe.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.isi.java.tictactoe.Main;

public class CreditsWindow extends JFrame
{
	private MainMenuWindow menuLink;
	private JLabel logoLabel;
	private JLabel creditsLabel;

	private JLabel developmentLabel;
	private JLabel lineLabel;

	private JLabel name1Label;
	private JLabel name2Label;
	private JLabel name3Label;
	private JLabel name4Label;

	private JPanel logoCreditsPanel;
	private JPanel developmentPanel;

	private JPanel namePanel;

	private JPanel contentPane;

	public CreditsWindow(MainMenuWindow mainMenuWindow)
	{
		super("Tic Tac Toe - Credits");

		this.menuLink = mainMenuWindow;

		createComponents();
		setUpContainers();
		addComponentsToContainers();

		setSize(600, 650);
		setIconImage(new ImageIcon(Main.class.getResource("res/tic-tac-toe.png")).getImage());
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				menuLink.setVisible(true);
				dispose();
			}
		});

		setVisible(true);

	}

	private void createComponents()
	{
		logoLabel = new JLabel(" ");
		// logoLabel.setIcon(new ImageIcon(""));

		creditsLabel = new JLabel("CREDITS");
		creditsLabel.setFont(new Font("Times New Roman", Font.BOLD, 25));

		developmentLabel = new JLabel("     ULTIMATE TICTACTOE DEVELOPMENT  ");
		lineLabel = new JLabel("______________________________________________________________________________");

		developmentLabel.setFont(new Font("Times New Roman", Font.BOLD, 22));

		name1Label = new JLabel("CHRISTY THOMAS");
		name1Label.setFont(new Font("Times New Roman", Font.BOLD, 18));

		name2Label = new JLabel("SCIANA ELISHA QUADROS");
		name2Label.setFont(new Font("Times New Roman", Font.BOLD, 18));
		name3Label = new JLabel("KARAMPREET KAUR SIDHU");
		name3Label.setFont(new Font("Times New Roman", Font.BOLD, 18));
		name4Label = new JLabel("KARAMJIT KAUR");
		name4Label.setFont(new Font("Times New Roman", Font.BOLD, 18));

	}

	private void setUpContainers()
	{
		logoCreditsPanel = new JPanel();
		logoCreditsPanel.setAlignmentX(CENTER_ALIGNMENT);
		logoCreditsPanel.setLayout(new BoxLayout(logoCreditsPanel, BoxLayout.Y_AXIS));

		developmentPanel = new JPanel();
		developmentPanel.setAlignmentX(CENTER_ALIGNMENT);
		developmentPanel.setLayout(new BoxLayout(developmentPanel, BoxLayout.Y_AXIS));

		namePanel = new JPanel();
		namePanel.setAlignmentX(CENTER_ALIGNMENT);
		namePanel.setLayout(new BoxLayout(namePanel, BoxLayout.Y_AXIS));

		contentPane = (JPanel) getContentPane();
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		// contentPane.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEtchedBorder(),BorderFactory.createEmptyBorder(45,45,45,45)));
		((JComponent) getContentPane()).setBorder(BorderFactory.createMatteBorder(10, 10, 10, 10, Color.black));
	}

	private void addComponentsToContainers()
	{
		// logoCreditsPanel.add(logoLabel);
		// logoCreditsPanel.add(Box.createRigidArea(new Dimension(20,0)));
		logoCreditsPanel.add(creditsLabel);

		developmentPanel.add(developmentLabel);
		developmentPanel.add(lineLabel);

		namePanel.add(name1Label);
		namePanel.add(Box.createRigidArea(new Dimension(0, 30)));
		namePanel.add(name2Label);
		namePanel.add(Box.createRigidArea(new Dimension(0, 30)));
		namePanel.add(name3Label);
		namePanel.add(Box.createRigidArea(new Dimension(0, 30)));
		namePanel.add(name4Label);

		contentPane.add(Box.createRigidArea(new Dimension(0, 40)));
		contentPane.add(logoCreditsPanel);
		contentPane.add(Box.createRigidArea(new Dimension(0, 60)));
		contentPane.add(developmentPanel);
		contentPane.add(Box.createRigidArea(new Dimension(0, 50)));
		contentPane.add(namePanel);

	}

}

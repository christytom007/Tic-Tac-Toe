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

public class AboutWindow extends JFrame
{
	private MainMenuWindow menuLink;

	private JLabel playLabel;
	private JLabel lineLabel;

	private JLabel rule1Label;
	private JLabel rule2Label;

	private JLabel rule3Label;
	private JLabel rule4Label;
	private JLabel rule5Label;

	private JPanel playPanel;
	private JPanel rulePanel;

	private JPanel contentPane;

	public AboutWindow(MainMenuWindow mainMenuWindow)
	{
		super("Tic Tac Toe - About");

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

		playLabel = new JLabel("HOW TO PLAY");
		playLabel.setFont(new Font("Times New Roman", Font.BOLD, 25));

		lineLabel = new JLabel("___________________________");

		rule1Label = new JLabel("  1. Play occurs on 3X3 4X4 or 5X5 grid.");
		rule1Label.setFont(new Font("Times New Roman", Font.BOLD, 18));

		rule2Label = new JLabel("  2. Two players alternate marking empty squares with 'X' and 'O' ");
		rule2Label.setFont(new Font("Times New Roman", Font.BOLD, 18));

		rule3Label = new JLabel(
				"<html>&nbsp; 3. If one player places three, four or five of the same marks in a row,<br/>&nbsp;&nbsp;&nbsp;&nbsp; that player wins.</html> ");
		rule3Label.setFont(new Font("Times New Roman", Font.BOLD, 18));

		rule4Label = new JLabel("  4. If all spaces are filled, game ends in a  draw.");
		rule4Label.setFont(new Font("Times New Roman", Font.BOLD, 18));

		rule5Label = new JLabel(
				"<html>&nbsp; 5. If there are multiple winning combinations, extra 5 points are<br/>&nbsp;&nbsp;&nbsp;&nbsp; added to the score.</html>");
		rule5Label.setFont(new Font("Times New Roman", Font.BOLD, 18));

	}

	private void setUpContainers()
	{

		playPanel = new JPanel();
		playPanel.setAlignmentX(CENTER_ALIGNMENT);
		playPanel.setLayout(new BoxLayout(playPanel, BoxLayout.Y_AXIS));

		rulePanel = new JPanel();
		rulePanel.setAlignmentX(CENTER_ALIGNMENT);
		rulePanel.setLayout(new BoxLayout(rulePanel, BoxLayout.Y_AXIS));

		contentPane = (JPanel) getContentPane();
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		// contentPane.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEtchedBorder(),BorderFactory.createEmptyBorder(45,45,45,45)));
		((JComponent) getContentPane()).setBorder(BorderFactory.createMatteBorder(10, 10, 10, 10, Color.black));
	}

	private void addComponentsToContainers()
	{
		// logoCreditsPanel.add(logoLabel);
		// logoCreditsPanel.add(Box.createRigidArea(new Dimension(20,0)));

		playPanel.add(playLabel);
		playPanel.add(lineLabel);

		rulePanel.add(rule1Label);
		rulePanel.add(Box.createRigidArea(new Dimension(0, 30)));
		rulePanel.add(rule2Label);
		rulePanel.add(Box.createRigidArea(new Dimension(0, 30)));
		rulePanel.add(rule3Label);
		rulePanel.add(Box.createRigidArea(new Dimension(0, 30)));
		rulePanel.add(rule4Label);
		rulePanel.add(Box.createRigidArea(new Dimension(0, 30)));
		rulePanel.add(rule5Label);

		contentPane.add(Box.createRigidArea(new Dimension(0, 40)));
		contentPane.add(playPanel);
		contentPane.add(Box.createRigidArea(new Dimension(0, 60)));
		contentPane.add(rulePanel);

	}

}

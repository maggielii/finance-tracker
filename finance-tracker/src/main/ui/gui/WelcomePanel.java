package ui.gui;

import javax.swing.*;
import java.awt.*;

// Represents the welcome screen
class WelcomePanel extends JPanel {
	
	//EFFECTS: Sets up welcome panel
    public WelcomePanel() {
        setBackground(new Color(2, 48, 32));
        setLayout(new BorderLayout());

        Icon icon = new ImageIcon("images/intro.png");
        JLabel imageLabel = new JLabel(icon);
        add(imageLabel, BorderLayout.CENTER);

        JButton startButton = new JButton("Welcome! Click Here to Start");
        startButton.setBackground(new Color(196, 224, 198));
        startButton.addActionListener(e -> {
            CardLayout cl = (CardLayout) getParent().getLayout();
            cl.show(getParent(), "MainApp");
        });
        add(startButton, BorderLayout.SOUTH);
    }
}

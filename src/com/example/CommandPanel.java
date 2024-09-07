import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class CommandPanel {
    // Main method to create the command panel
    public JPanel createCommandPanel() {
        JPanel commandPanel = new JPanel();
        commandPanel.setLayout(new BoxLayout(commandPanel, BoxLayout.Y_AXIS));
        commandPanel.setBackground(Color.LIGHT_GRAY);

        commandPanel.add(createTabBar());
        commandPanel.add(createTitle("Rooms"));
        commandPanel.add(createButton("Bedroom"));
        commandPanel.add(createButton("Bathroom"));
        commandPanel.add(createButton("Living Room"));
        commandPanel.add(createButton("Kitchen"));
        commandPanel.add(createTitle("Furniture"));
        commandPanel.add(createButton("Door"));
        commandPanel.add(createButton("Sofa"));
        commandPanel.add(createButton("Table & Chairs"));


        return commandPanel;
    }

    // Method to create the title label
    public JLabel createTitle(String title) {
        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(Color.DARK_GRAY);
        titleLabel.setPreferredSize(new Dimension(200, 30));
        return titleLabel;
    }

    public JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setPreferredSize(new Dimension(150, 30));
        button.setMaximumSize(new Dimension(150, 30));
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.setBackground(Color.WHITE);
        button.setForeground(Color.DARK_GRAY);
        button.setFocusPainted(false);
        return button;
    }

    public Component createTabBar(){
        JPanel tabBar = new JPanel();
        tabBar.setLayout(new BoxLayout(tabBar, BoxLayout.X_AXIS));
        tabBar.setBackground(Color.LIGHT_GRAY);
        tabBar.add(createButton("Insert"));
        tabBar.add(createButton("Properties"));
        return tabBar;
    }
}

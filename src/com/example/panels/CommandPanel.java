package panels;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

enum Room {
    bed, bath, living, kit
}

enum Furniture {
    closet, lamp, bed, door, sofa, tc
}

public class CommandPanel {
    // Main method to create the command panel
    public JPanel createCommandPanel() {
        JPanel commandPanel = new JPanel();
        commandPanel.setLayout(new BoxLayout(commandPanel, BoxLayout.Y_AXIS));
        commandPanel.setBackground(Color.LIGHT_GRAY);

        commandPanel.add(createTabBar());
        commandPanel.add(createTitle("Rooms"));
        commandPanel.add(createButton("Bedroom", Room.bed));
        commandPanel.add(createButton("Bathroom", Room.bath));
        commandPanel.add(createButton("Living Room", Room.living));
        commandPanel.add(createButton("Kitchen", Room.kit));
        commandPanel.add(createTitle("Furniture"));
        commandPanel.add(createButton("Door", Furniture.door));
        commandPanel.add(createButton("Sofa", Furniture.sofa));
        commandPanel.add(createButton("Table & Chairs", Furniture.tc));

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

    public <T> JButton createButton(String text, T fixture) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setPreferredSize(new Dimension(150, 30));
        button.setMaximumSize(new Dimension(150, 30));
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.setBackground(Color.WHITE);
        button.setForeground(Color.DARK_GRAY);
        button.setFocusPainted(false);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showConfirmDialog(
                        null,
                        "You selected " + text,
                        "Confirm Exit",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null);
            }
        });

        return button;
    }

    public Component createTabBar() {
        JPanel tabBar = new JPanel();
        tabBar.setLayout(new BoxLayout(tabBar, BoxLayout.X_AXIS));
        tabBar.setBackground(Color.LIGHT_GRAY);
        tabBar.add(createButton("Insert", null));
        tabBar.add(createButton("Properties", null));
        return tabBar;
    }
}

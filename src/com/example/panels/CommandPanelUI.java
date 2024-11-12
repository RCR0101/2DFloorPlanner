package com.example.panels;

import java.awt.*;
import javax.swing.*;

import com.example.services.Util;

public class CommandPanelUI {
    private CommandPanelLogic commandPanelLogic;
    private final CardLayout cardLayout;
    private final JPanel cardPanel;

    public CommandPanelUI(CommandPanelLogic commandPanelLogic) {
        this.commandPanelLogic = commandPanelLogic;
        this.cardLayout = new CardLayout();
        this.cardPanel = new JPanel(cardLayout);
    }

    public JScrollPane createCommandPanel() {
        JPanel commandPanel = new JPanel();
        commandPanel.setLayout(new BoxLayout(commandPanel, BoxLayout.Y_AXIS));
        commandPanel.setBackground(new Color(0, 0, 0)); // Dark background

        commandPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 0, 20)); // Padding for aesthetics

        commandPanel.add(createTabBar());
        commandPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Spacing
        commandPanel.add(cardPanel);

        // Setup card panels
        commandPanelLogic.createInsertPanel(cardPanel);
        commandPanelLogic.createPropertiesPanel(cardPanel);
        JScrollPane scrollPane = new JScrollPane(commandPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        return scrollPane;
    }

    public Component createTabBar() {
        JPanel tabBar = new JPanel();
        tabBar.setLayout(new BoxLayout(tabBar, BoxLayout.X_AXIS));
        tabBar.setBackground(new Color(0, 0, 0));

        // Load icons
        ImageIcon insert = new ImageIcon(Util.getAbsolutePath("assets/icons/insert.png"));
        ImageIcon properties = new ImageIcon(Util.getAbsolutePath("assets/icons/properties.png"));

        // Create the Insert and Properties buttons
        JButton insertButton = new JButton("Insert", Util.resizeImage(insert));
        JButton propertiesButton = new JButton("Properties", Util.resizeImage(properties));

        styleTabButton(insertButton);
        styleTabButton(propertiesButton);

        // Setup button actions (logic)
        insertButton.addActionListener(_ -> commandPanelLogic.handleInsertButtonAction(cardPanel, cardLayout));
        propertiesButton.addActionListener(_ -> cardLayout.show(cardPanel, "Properties"));

        tabBar.add(insertButton);
        tabBar.add(Box.createRigidArea(new Dimension(20, 0)));
        tabBar.add(propertiesButton);

        return tabBar;
    }

    private static void styleTabButton(JButton button) {
        button.setBackground(new Color(0, 0, 0));
        button.setForeground(new Color(220, 220, 220));
        button.setBorder(BorderFactory.createEmptyBorder());
        button.setFocusPainted(false);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        button.setOpaque(true);

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                button.setBackground(new Color(70, 70, 70));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                button.setBackground(new Color(0, 0, 0));
            }
        });
    }
}
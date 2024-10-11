package com.example.panels;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

import com.example.canvas.Canvas;
import com.example.services.Util;

enum Room {
    bedroom, bathroom, living, kit
}

enum Furniture {
    closet, lamp, bed, door, sofa, tc
}

public class CommandPanel {
    private Canvas canvas;
    private final CardLayout cardLayout;
    private final JPanel cardPanel;

    public CommandPanel(Canvas canvas) {
        this.canvas = canvas;
        this.cardLayout = new CardLayout();
        this.cardPanel = new JPanel(cardLayout);
    }

    public JPanel createCommandPanel() {
        JPanel commandPanel = new JPanel();
        commandPanel.setLayout(new BoxLayout(commandPanel, BoxLayout.Y_AXIS));
        commandPanel.setBackground(new Color(0, 0, 0)); // Dark background

        commandPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 0, 20)); // Padding for aesthetics

        commandPanel.add(createTabBar());
        commandPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Spacing
        commandPanel.add(cardPanel);

        // Setup card panels
        createInsertPanel();
        createPropertiesPanel();

        return commandPanel;
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

        tabStyleButton(insertButton);
        tabStyleButton(propertiesButton);

        // When the "Insert" button is clicked, activate custom room mode
        insertButton.addActionListener(e -> {
            canvas.customRoom = true;
            canvas.currentRoom = null; // Reset any current room being edited
            cardLayout.show(cardPanel, "Insert");
            System.out.println("Custom room mode activated.");
        });

        // Show the "Properties" panel when the Properties button is clicked
        propertiesButton.addActionListener(e -> cardLayout.show(cardPanel, "Properties"));

        tabBar.add(insertButton);
        tabBar.add(Box.createRigidArea(new Dimension(20, 0)));
        tabBar.add(propertiesButton);

        return tabBar;
    }

    private void createInsertPanel() {
        JPanel insertPanel = new JPanel();
        insertPanel.setLayout(new BoxLayout(insertPanel, BoxLayout.Y_AXIS));
        insertPanel.setBackground(new Color(0, 0, 0));

        insertPanel.add(createTitle("Rooms"));
        insertPanel.add(createButton("Bedroom", Room.bedroom));
        insertPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        insertPanel.add(createButton("Bathroom", Room.bathroom));
        insertPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        insertPanel.add(createButton("Living Room", Room.living));
        insertPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        insertPanel.add(createButton("Kitchen", Room.kit));

        insertPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        insertPanel.add(createTitle("Furniture"));
        insertPanel.add(createButton("Door", Furniture.door));
        insertPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        insertPanel.add(createButton("Sofa", Furniture.sofa));
        insertPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        insertPanel.add(createButton("Table & Chairs", Furniture.tc));

        cardPanel.add(insertPanel, "Insert");
    }

    private void createPropertiesPanel() {
        JPanel propertiesPanel = new JPanel();
        propertiesPanel.setLayout(new BoxLayout(propertiesPanel, BoxLayout.Y_AXIS));
        propertiesPanel.setBackground(new Color(0, 0, 0));

        JTextArea notice = new JTextArea("Click a room or furniture to modify its properties");
        notice.setBackground(new Color(0, 0, 0));
        notice.setLineWrap(true);
        notice.setAlignmentX(Component.CENTER_ALIGNMENT);
        notice.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        notice.setForeground(new Color(200, 200, 200));
        notice.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        propertiesPanel.add(notice);

        cardPanel.add(propertiesPanel, "Properties");
    }

    public JLabel createTitle(String title) {
        JLabel titleLabel = new JLabel(title);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titleLabel.setForeground(new Color(200, 200, 200));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        return titleLabel;
    }

    public <T> JButton createButton(String text, T fixture) {
        JButton button = new JButton(text);
        styleButton(button);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setPreferredSize(new Dimension(150, 35));
        button.setMaximumSize(new Dimension(150, 35));
        button.setMinimumSize(new Dimension(150, 35));

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                canvas.setSelectedObject(fixture);

                if (e.getSource() == button) {
                    JButton button = (JButton) e.getSource();
                    if (button.getText().equals("Insert")) {
                        canvas.customRoom = true;
                    } else {
                        canvas.defaultRoom = true;
                    }
                }
            }
        });

        return button;
    }

    private static void tabStyleButton(JButton button) {
        button.setBackground(new Color(0, 0, 0));
        button.setForeground(new Color(220, 220, 220));
        button.setBorder(BorderFactory.createEmptyBorder());
        button.setFocusPainted(false);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        button.setOpaque(true);

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(70, 70, 70));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(0, 0, 0));
            }
        });
    }

    private static void styleButton(JButton button) {
        button.setBackground(new Color(45, 45, 45));
        button.setForeground(new Color(220, 220, 220));
        button.setBorder(BorderFactory.createLineBorder(new Color(45, 45, 45), 1));
        button.setFocusPainted(false);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        button.setOpaque(true);

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(70, 70, 70));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(45, 45, 45));
            }
        });
    }
}
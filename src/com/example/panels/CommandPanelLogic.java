package com.example.panels;

import java.awt.*;
import javax.swing.*;
import com.example.canvas.Canvas;
enum Room {
    bedroom, bathroom, living, kit
}

enum Furniture {
    closet, lamp, bed, door, sofa, tc
}
public class CommandPanelLogic {
    private Canvas canvas;

    public CommandPanelLogic(Canvas canvas) {
        this.canvas = canvas;
    }

    public void handleInsertButtonAction(JPanel cardPanel, CardLayout cardLayout) {
        canvas.customRoom = true;
        canvas.currentRoom = null; // Reset any current room being edited
        cardLayout.show(cardPanel, "Insert");
        System.out.println("Custom room mode activated.");
    }

    public void createInsertPanel(JPanel cardPanel) {
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

    public void createPropertiesPanel(JPanel cardPanel) {
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

        button.addActionListener(e -> {
            canvas.setSelectedObject(fixture);

            if (e.getSource() == button) {
                JButton button1 = (JButton) e.getSource();
                if (button1.getText().equals("Insert")) {
                    canvas.customRoom = true;
                } else {
                    canvas.defaultRoom = true;
                }
            }
        });

        return button;
    }

    private static void styleButton(JButton button) {
        button.setBackground(new Color(45, 45, 45));
        button.setForeground(new Color(220, 220, 220));
        button.setBorder(BorderFactory.createLineBorder(new Color(45, 45, 45), 1));
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
                button.setBackground(new Color(45, 45, 45));
            }
        });
    }
}
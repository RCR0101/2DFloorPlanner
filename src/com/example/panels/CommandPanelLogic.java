package com.example.panels;

import java.awt.*;
import javax.swing.*;
import com.example.canvas.Canvas;
enum Room {
    bedroom, bathroom, living, kit
}

enum WallFurniture {
    door, window
}
public class CommandPanelLogic {
    private Canvas canvas;

    public CommandPanelLogic(Canvas canvas) {
        this.canvas = canvas;
    }

    public void handleInsertButtonAction(JPanel cardPanel, CardLayout cardLayout) {
        canvas.customRoom = true;
        canvas.currentRoom = null;
        cardLayout.show(cardPanel, "Insert");
    }

    public void createInsertPanel(JPanel cardPanel) {
        JPanel insertPanel = new JPanel();
        insertPanel.setLayout(new BoxLayout(insertPanel, BoxLayout.Y_AXIS));

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
        insertPanel.add(createButton("Door", WallFurniture.door));
        insertPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        insertPanel.add(createButton("Window", WallFurniture.window));
        insertPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        insertPanel.add(createButton("Queen Bed", FurnitureList.qBed));
        insertPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        insertPanel.add(createButton("Sofa", FurnitureList.sofa));
        insertPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        insertPanel.add(createButton("Dining Table", FurnitureList.dTable));
        insertPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        insertPanel.add(createButton("TV Table", FurnitureList.tvTable));
        insertPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        insertPanel.add(createButton("Toilet", FurnitureList.toilet));
        insertPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        insertPanel.add(createButton("Single Sofa", FurnitureList.sSofa));
        insertPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        insertPanel.add(createButton("King Bed", FurnitureList.kBed));
        insertPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        insertPanel.add(createButton("Study Desk", FurnitureList.sDesk));
        insertPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        insertPanel.add(createButton("Sink", FurnitureList.sink));
        insertPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        insertPanel.add(createButton("Snooker Table", FurnitureList.snTable));
        insertPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        insertPanel.add(createButton("Cot", FurnitureList.cot));
        insertPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        insertPanel.add(createButton("Drawer", FurnitureList.drawer));
        insertPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        insertPanel.add(createButton("Bedroom Drawer", FurnitureList.bDrawer));
        insertPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        insertPanel.add(createButton("Glass Table", FurnitureList.gTable));
        insertPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        insertPanel.add(createButton("L-shaped Sofa", FurnitureList.lSofa));
        insertPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        insertPanel.add(createButton("Bathtub", FurnitureList.bTub));
        insertPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        insertPanel.add(createButton("Kitchen Sink", FurnitureList.kSink));
        insertPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        insertPanel.add(createButton("Stove", FurnitureList.stove));
        insertPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        insertPanel.add(createButton("Shower", FurnitureList.shower));
        insertPanel.add(Box.createRigidArea(new Dimension(0, 40)));

        cardPanel.add(insertPanel, "Insert");
    }

    public void createAlignmentPanel(JPanel cardPanel) {
        JPanel alignmentPanel = new JPanel();
        alignmentPanel.setLayout(new BoxLayout(alignmentPanel, BoxLayout.Y_AXIS));

        JTextArea notice = new JTextArea("Click a room or furniture to modify its properties");
        notice.setLineWrap(true);
        notice.setAlignmentX(Component.CENTER_ALIGNMENT);
        notice.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        notice.setForeground(new Color(200, 200, 200));
        notice.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        alignmentPanel.add(notice);
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
                } else if(!(fixture instanceof FurnitureList)) {
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
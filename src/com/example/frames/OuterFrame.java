package frames;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import panels.CommandPanel;
import canvas.Canvas;

public class OuterFrame implements WindowListener {

    public static void main(String[] args) {
        Canvas canvas = new Canvas(40);
        CommandPanel cmdPanel = new CommandPanel(canvas);
        JFrame frame = new JFrame("2D Floor Planner");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(canvas, BorderLayout.CENTER);
        frame.add(cmdPanel.createCommandPanel(), BorderLayout.EAST);
        frame.addWindowListener(new OuterFrame());
        frame.setVisible(true);
    }

    @Override
    public void windowClosing(WindowEvent e) {
        ImageIcon logo = new ImageIcon(
                "/Users/adalmia/Documents/projects/java_dev/2DFloorPlanner/assets/images/logo.jpg");
        Image resizedImage = logo.getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(resizedImage);
        int response = JOptionPane.showConfirmDialog(
                null,
                "Do you want to save unsaved changes?",
                "Confirm Exit",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                resizedIcon);

        if (response == JOptionPane.YES_OPTION) {
            System.out.println("Saving changes...");
            System.exit(0);
        } else if (response == JOptionPane.NO_OPTION) {
            System.exit(0);
        }
    }

    @Override
    public void windowOpened(WindowEvent e) {
    }

    @Override
    public void windowClosed(WindowEvent e) {
    }

    @Override
    public void windowIconified(WindowEvent e) {
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
    }

    @Override
    public void windowActivated(WindowEvent e) {
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
    }
}

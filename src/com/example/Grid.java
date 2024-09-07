import javax.swing.JComponent;
import java.awt.Color;
import java.awt.Graphics;

class Canvas extends JComponent {

    @Override
    protected void paintComponent(Graphics g) {
    }
}

public class Grid extends Canvas {
    private int gridSize;

    public Grid(int gridSize) {
        this.gridSize = gridSize;  // Set the size of the grid cells
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.GRAY);
        for (int i = 0; i < getWidth(); i += gridSize) {
            g.drawLine(i, 0, i, getHeight());
        }
        for (int j = 0; j < getHeight()*2; j += gridSize) {
            g.drawLine(0, j, getWidth(), j);
        }
    }

    // Method to snap a point to the grid
    public int snapToGrid(int value) {
        return (value / gridSize) * gridSize;
    }
}
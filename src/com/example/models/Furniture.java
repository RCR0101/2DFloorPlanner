package com.example.models;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;
import java.util.ArrayList;
import java.util.Objects;

public class Furniture implements Serializable {
    @Serial
    private static final long serialVersionUID = 7894561230987654321L;
    public double x;
    public double y;
    public double width;
    public double height;
    public transient BufferedImage image;
    private int rotationAngle = 0;
    private String imagePath = "";
    public Room parentRoom;

    @Serial
    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();
    }


    public Furniture(double x, double y, String imagePath) {
        this.x = x;
        this.y = y;
        this.imagePath = imagePath;

        try {
            this.image = ImageIO.read(new File(imagePath));
            this.image = shrinkImage(this.image, 0.3);
            Rectangle boundingBox = computeBoundingBox(this.image);
            this.width = boundingBox.getWidth();
            this.height = boundingBox.getHeight();
            this.image = this.image.getSubimage(boundingBox.x, boundingBox.y, boundingBox.width, boundingBox.height);
            this.parentRoom = null;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Rectangle computeBoundingBox(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();

        int minX = width, minY = height, maxX = 0, maxY = 0;

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int alpha = (image.getRGB(x, y) >> 24) & 0xff;
                if (alpha != 0) {
                    if (x < minX) minX = x;
                    if (x > maxX) maxX = x;
                    if (y < minY) minY = y;
                    if (y > maxY) maxY = y;
                }
            }
        }

        return new Rectangle(minX, minY, maxX - minX + 1, maxY - minY + 1);
    }

    public boolean contains(Point2D p) {
        return p.getX() > x && p.getX() < x + width && p.getY() > y && p.getY() < y + height;
    }

    public boolean intersects(Furniture furniture) {
        Rectangle2D rect1 = new Rectangle2D.Double(x, y, width, height);
        Rectangle2D rect2 = new Rectangle2D.Double(furniture.x, furniture.y, furniture.width, furniture.height);
        return rect1.intersects(rect2);
    }

    private BufferedImage shrinkImage(BufferedImage originalImage, double scale) {
        int newWidth = (int) (originalImage.getWidth() * scale);
        int newHeight = (int) (originalImage.getHeight() * scale);

        BufferedImage scaledImage = new BufferedImage(newWidth, newHeight, originalImage.getType());
        Graphics2D g2d = scaledImage.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.drawImage(originalImage, 0, 0, newWidth, newHeight, null);
        g2d.dispose();

        return scaledImage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Furniture that = (Furniture) o;
        return Double.compare(that.x, x) == 0 &&
                Double.compare(that.y, y) == 0 &&
                Double.compare(that.width, width) == 0 &&
                Double.compare(that.height, height) == 0 &&
                Objects.equals(image, that.image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, width, height, image);
    }
    public void rotate() {
        rotationAngle = (rotationAngle + 90) % 360;
    }

    // Getter for rotation angle
    public int getRotationAngle() {
        return rotationAngle;
    }

    public static Furniture getCopy(Furniture furniture) {
        Furniture copy = new Furniture(furniture.x,furniture.y,furniture.imagePath);
        copy.width = furniture.width;
        copy.height = furniture.height;
        copy.rotationAngle = furniture.rotationAngle;
        copy.parentRoom = furniture.parentRoom;
        return copy;
    }

    public void loadImage() {
        if (imagePath != null) {
            try {
                BufferedImage loadedImage = ImageIO.read(new File(imagePath));
                if (loadedImage != null) {
                    this.image = shrinkImage(loadedImage, 0.3);
                    Rectangle boundingBox = computeBoundingBox(this.image);

                    // Update x and y to match the bounding box's new top-left
                    this.x += boundingBox.getX();
                    this.y += boundingBox.getY();

                    this.width = boundingBox.getWidth();
                    this.height = boundingBox.getHeight();

                    this.image = this.image.getSubimage(
                            boundingBox.x,
                            boundingBox.y,
                            boundingBox.width,
                            boundingBox.height
                    );
                }
            } catch (IOException e) {
                e.printStackTrace();
                this.image = null;
            }
        }
    }

    public void updateParentRoom(ArrayList<Room> rooms) {
        Point2D furnitureCenter = new Point2D.Double(
                this.x + this.width / 2,
                this.y + this.height / 2
        );

        // Check all corners of the furniture
        Point2D[] corners = {
                new Point2D.Double(this.x, this.y),                           // Top-left
                new Point2D.Double(this.x + this.width, this.y),             // Top-right
                new Point2D.Double(this.x, this.y + this.height),            // Bottom-left
                new Point2D.Double(this.x + this.width, this.y + this.height) // Bottom-right
        };

        // First try to find a room that contains all corners
        for (Room room : rooms) {
            boolean allCornersInRoom = true;
            for (Point2D corner : corners) {
                if (!room.contains(corner)) {
                    allCornersInRoom = false;
                    break;
                }
            }
            if (allCornersInRoom) {
                this.parentRoom = room;
                return;
            }
        }

        // If no room contains all corners, check if the center is in a room
        for (Room room : rooms) {
            if (room.contains(furnitureCenter)) {
                this.parentRoom = room;
                return;
            }
        }

        // If no valid room is found, set parentRoom to null
        this.parentRoom = null;
    }
}
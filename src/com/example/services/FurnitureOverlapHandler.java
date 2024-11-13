package com.example.services;

import com.example.models.Furniture;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.util.List;

public class FurnitureOverlapHandler {

    public static boolean isOverlap(Furniture furniture, List<Furniture> furnitureItems) {
        Shape furnitureShape = getRotatedShape(furniture);

        for (Furniture otherFurniture : furnitureItems) {
            if (otherFurniture != furniture) {
                Shape otherFurnitureShape = getRotatedShape(otherFurniture);
                if (intersects(furnitureShape, otherFurnitureShape)) {
                    return true;
                }
            }
        }
        return false;
    }

    private static Shape getRotatedShape(Furniture furniture) {
        Rectangle2D bounds = new Rectangle2D.Double(
                furniture.x,
                furniture.y,
                furniture.width,
                furniture.height
        );

        // Apply rotation to the bounds
        AffineTransform transform = AffineTransform.getRotateInstance(
                Math.toRadians(furniture.getRotationAngle()),
                furniture.x + furniture.width / 2,
                furniture.y + furniture.height / 2
        );

        return transform.createTransformedShape(bounds);
    }

    private static boolean intersects(Shape shape1, Shape shape2) {
        Area area1 = new Area(shape1);
        area1.intersect(new Area(shape2));
        return !area1.isEmpty();
    }
}
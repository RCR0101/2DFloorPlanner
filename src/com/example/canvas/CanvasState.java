package com.example.canvas;

import com.example.models.Furniture;
import com.example.models.Room;

import java.util.ArrayList;
import java.util.List;

public class CanvasState {
    private final List<Room> rooms;
    private final List<Furniture> furnitureItems;

    public CanvasState(List<Room> rooms, List<Furniture> furnitureItems) {
        this.rooms = new ArrayList<>();
        for (Room room : rooms) {
            this.rooms.add(Room.getCopy(room));
        }

        this.furnitureItems = new ArrayList<>();
        for (Furniture furniture : furnitureItems) {
            this.furnitureItems.add(Furniture.getCopy(furniture));
        }
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public List<Furniture> getFurnitureItems() {
        return furnitureItems;
    }
}
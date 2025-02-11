package com.juegovoxel.game;

import java.util.HashMap;

import com.juegovoxel.world.BlockType;


public class Inventory {

    private HashMap<BlockType, Integer> items;

    public Inventory() {
        items = new HashMap<>();
    }

    public void addItem(BlockType type, int amount) {
        items.put(type, items.getOrDefault(type, 0) + amount);
    }

    public boolean removeItem(BlockType type, int amount) {
        if (items.containsKey(type) && items.get(type) >= amount) {
            items.put(type, items.get(type) - amount);
            return true;
        }
        return false;
    }

    public HashMap<BlockType, Integer> getItems() {
        return items;
    }
}

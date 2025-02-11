package com.juegovoxel.world;

import com.juegovoxel.entity.Enemy;
import com.juegovoxel.entity.Player;
import com.juegovoxel.render.Renderer;
import com.juegovoxel.util.PerlinNoise;

import java.util.HashMap;
import java.util.Random;

public class World {
    private HashMap<String, Chunk> chunks;
   // private HashMap<String, Enemy> enemies;
    
    private Random random;

    public World() {
        chunks = new HashMap<>();
       // enemies = new HashMap<>();
        random = new Random();
        generateChunksAround(0, 0);
        //spawnEnemies();
    }

    public void update(Player player) {
        updateChunks(player);
        //updateEnemies(player);
    }

    public void render(Renderer renderer) {
        for (Chunk chunk : chunks.values()) {
            chunk.render(renderer);
        }
        
        //for (Enemy enemy : enemies.values()) {
        //    enemy.render(renderer);
       // }
    }

    private void generateChunksAround(int xPos, int zPos) {
        for (int x = -1; x <= 1; x++) {
            for (int z = -1; z <= 1; z++) {
                String key = Chunk.getKey(x + xPos, z + zPos);
                if (!chunks.containsKey(key)) {
                    chunks.put(key, new Chunk(x + xPos, z + zPos));
                }
            }
        }
    }

    private void updateChunks(Player player) {
        int playerChunkX = (int) player.getPosition().x / Chunk.CHUNK_SIZE;
        int playerChunkZ = (int) player.getPosition().z / Chunk.CHUNK_SIZE;
        generateChunksAround(playerChunkX, playerChunkZ);
    }

	/*
	 * private void spawnEnemies() { Enemy enemy = new Enemy(20, 10, 20);
	 * enemies.put(enemy.getId(), enemy); }
	 */

	/*
	 * private void updateEnemies(Player player) { for (Enemy enemy :
	 * enemies.values()) { enemy.update(player); } }
	 */
    public boolean isBlockAt(int x, int y, int z) {
        int chunkX = Math.floorDiv(x, Chunk.CHUNK_SIZE);
        int chunkZ = Math.floorDiv(z, Chunk.CHUNK_SIZE);
        String key = Chunk.getKey(chunkX, chunkZ);
        Chunk chunk = chunks.get(key);
        if (chunk != null) {
            int localX = Math.floorMod(x, Chunk.CHUNK_SIZE);
            int localY = y; // Asumiendo que y está dentro del rango
            int localZ = Math.floorMod(z, Chunk.CHUNK_SIZE);
            return chunk.isBlockAt(localX, localY, localZ);
        }
        return false;
    }
    
    
}

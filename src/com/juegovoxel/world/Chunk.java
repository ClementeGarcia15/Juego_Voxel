package com.juegovoxel.world;

import com.juegovoxel.phys.AABB;
import com.juegovoxel.render.Renderer;
import com.juegovoxel.util.PerlinNoise;

public class Chunk {
    public static final int CHUNK_SIZE = 16;
    private Block[][][] blocks;
    private int xPosition, zPosition;
    
    public AABB block_col;

    public Chunk(int x, int z) {
        this.xPosition = x;
        this.zPosition = z;
        blocks = new Block[CHUNK_SIZE][CHUNK_SIZE][CHUNK_SIZE];
        generateTerrain();
    }

    private void generateTerrain() {
        for (int x = 0; x < CHUNK_SIZE; x++) {
            for (int z = 0; z < CHUNK_SIZE; z++) {
                int worldX = x + xPosition * CHUNK_SIZE;
                int worldZ = z + zPosition * CHUNK_SIZE;
                double noiseValue = PerlinNoise.perlinNoise(worldX * 0.1, worldZ * 0.1);
                noiseValue = (noiseValue + 1) / 2;
                int maxHeight = CHUNK_SIZE - 1;
                int height = (int) (noiseValue * maxHeight);

                for (int y = 0; y <= height; y++) {
                    if (y < height) {
                        blocks[x][y][z] = new Block(BlockType.DIRT);
                    } else {
                        blocks[x][y][z] = new Block(BlockType.GRASS);
                    }
                }
            }
        }
    }

    public void render(Renderer renderer) {
        for (int x = 0; x < CHUNK_SIZE; x++) {
            for (int y = 0; y < CHUNK_SIZE; y++) {
                for (int z = 0; z < CHUNK_SIZE; z++) {
                    if (blocks[x][y][z] != null) {
                        boolean[] facesToRender = new boolean[6];
                        facesToRender[0] = (x == 0 || blocks[x - 1][y][z] == null);
                        facesToRender[1] = (x == CHUNK_SIZE - 1 || blocks[x + 1][y][z] == null);
                        facesToRender[2] = (y == 0 || blocks[x][y - 1][z] == null);
                        facesToRender[3] = (y == CHUNK_SIZE - 1 || blocks[x][y + 1][z] == null);
                        facesToRender[4] = (z == 0 || blocks[x][y][z - 1] == null);
                        facesToRender[5] = (z == CHUNK_SIZE - 1 || blocks[x][y][z + 1] == null);
                        blocks[x][y][z].render(renderer, x + xPosition * CHUNK_SIZE, y, z + zPosition * CHUNK_SIZE, facesToRender);
                    }
                }
            }
        }
    }

    public boolean isBlockAt(int x, int y, int z) {
        if (x >= 0 && x < CHUNK_SIZE && y >= 0 && y < CHUNK_SIZE && z >= 0 && z < CHUNK_SIZE) {
            return blocks[x][y][z] != null;
        }
        return false;
    }

    public static String getKey(int x, int z) {
        return x + "," + z;
    }
}

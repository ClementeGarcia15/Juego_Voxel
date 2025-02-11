package com.juegovoxel.world;

import com.juegovoxel.render.Renderer;

public class Block {
    private BlockType type;

    public Block(BlockType type) {
        this.type = type;
    }

    public void render(Renderer renderer, int x, int y, int z, boolean[] facesToRender) {
        renderer.renderBlock(type, x, y, z, facesToRender);
    }

    public BlockType getType() {
        return type;
    }
}

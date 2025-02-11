package com.juegovoxel.phys;

import org.lwjgl.opengl.GL11;

public class AABB {
    private float minX, minY, minZ;
    private float maxX, maxY, maxZ;

    public AABB(float x, float y, float z, float width, float height, float depth) {
        this.minX = x;
        this.minY = y;
        this.minZ = z;
        this.maxX = x + width;
        this.maxY = y + height;
        this.maxZ = z + depth;
    }

    public void updatePosition(float x, float y, float z) {
        float width = maxX - minX;
        float height = maxY - minY;
        float depth = maxZ - minZ;
        this.minX = x;
        this.minY = y;
        this.minZ = z;
        this.maxX = x + width;
        this.maxY = y + height;
        this.maxZ = z + depth;
    }

    public boolean collidesWith(AABB other) {
        return (this.minX < other.maxX && this.maxX > other.minX) &&
               (this.minY < other.maxY && this.maxY > other.minY) &&
               (this.minZ < other.maxZ && this.maxZ > other.minZ);
    }

    public void render() {
        // Dibuja un cubo alámbrico en 3D
        GL11.glBegin(GL11.GL_LINES);

        // Cara frontal
        GL11.glVertex3f(minX, minY, minZ); GL11.glVertex3f(maxX, minY, minZ);
        GL11.glVertex3f(maxX, minY, minZ); GL11.glVertex3f(maxX, maxY, minZ);
        GL11.glVertex3f(maxX, maxY, minZ); GL11.glVertex3f(minX, maxY, minZ);
        GL11.glVertex3f(minX, maxY, minZ); GL11.glVertex3f(minX, minY, minZ);

        // Cara trasera
        GL11.glVertex3f(minX, minY, maxZ); GL11.glVertex3f(maxX, minY, maxZ);
        GL11.glVertex3f(maxX, minY, maxZ); GL11.glVertex3f(maxX, maxY, maxZ);
        GL11.glVertex3f(maxX, maxY, maxZ); GL11.glVertex3f(minX, maxY, maxZ);
        GL11.glVertex3f(minX, maxY, maxZ); GL11.glVertex3f(minX, minY, maxZ);

        // Conexión entre caras
        GL11.glVertex3f(minX, minY, minZ); GL11.glVertex3f(minX, minY, maxZ);
        GL11.glVertex3f(maxX, minY, minZ); GL11.glVertex3f(maxX, minY, maxZ);
        GL11.glVertex3f(maxX, maxY, minZ); GL11.glVertex3f(maxX, maxY, maxZ);
        GL11.glVertex3f(minX, maxY, minZ); GL11.glVertex3f(minX, maxY, maxZ);

        GL11.glEnd();
    }

    // Getters (opcional)
    public float getMinX() { return minX; }
    public float getMinY() { return minY; }
    public float getMinZ() { return minZ; }
    public float getMaxX() { return maxX; }
    public float getMaxY() { return maxY; }
    public float getMaxZ() { return maxZ; }
}
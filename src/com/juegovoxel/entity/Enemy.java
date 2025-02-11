package com.juegovoxel.entity;

import java.util.UUID;

import com.juegovoxel.render.Renderer;

public class Enemy {
    private String id;
    private float x, y, z;

    public Enemy(float x, float y, float z) {
        this.id = UUID.randomUUID().toString();
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void update(Player player) {
        // Lógica para seguir al jugador
        float dx = player.getPosition().x - x;
        float dz = player.getPosition().z - z;
        float distance = (float) Math.sqrt(dx * dx + dz * dz);
        if (distance > 0) {
            x += dx / distance * 0.02f;
            z += dz / distance * 0.02f;
        }
    }

    public void render(Renderer renderer) {
        renderer.renderEnemy(x, y, z);
    }

    public String getId() {
        return id;
    }
}

package com.juegovoxel.entity;

import com.juegovoxel.game.Inventory;
import com.juegovoxel.phys.AABB;
import com.juegovoxel.render.Renderer;
import com.juegovoxel.world.World;
import org.lwjgl.util.vector.Vector3f;

public class Player {
    private Vector3f position;
    private Vector3f rotation;
    private Inventory inventory;
    
    public AABB boxColision;

    public float movementSpeed = 0.1f;
    public float mouseSensitivity = 0.1f;
    private float gravity = -0.02f;
    private float verticalSpeed = 0;
    private boolean isFlying = false;

    public Player() {
        position = new Vector3f(0, 20, 0); // Posición inicial
        rotation = new Vector3f(0, 0, 0);   // Rotación inicial
        inventory = new Inventory();
        
       
    }

    public void update(World world) {
        // Aplicar movimiento vertical (gravedad)
        if (!isFlying) {
            verticalSpeed += gravity;
            position.y += verticalSpeed;
            // Detección de colisión con el suelo
            if (isColliding(world, position.x, position.y, position.z)) {
                position.y = (float) Math.floor(position.y);
                verticalSpeed = 0;
            }
        }
    }

    public void render(Renderer renderer) {
        // En un juego en primera persona, normalmente no renderizas al jugador
    }

    public void move(float dx, float dy, float dz, World world) {
        float sinYaw = (float) Math.sin(Math.toRadians(rotation.y));
        float cosYaw = (float) Math.cos(Math.toRadians(rotation.y));

        // Calcular desplazamientos basados en la rotación del jugador
        float deltaX = dx * cosYaw - dz * sinYaw;
        float deltaZ = dx * sinYaw + dz * cosYaw;

        // Actualizar posición considerando colisiones
        if (!isColliding(world, position.x + deltaX, position.y, position.z)) {
            position.x += deltaX;
        }
        if (!isColliding(world, position.x, position.y, position.z + deltaZ)) {
            position.z += deltaZ;
        }
        if (!isColliding(world, position.x, position.y + dy, position.z)) {
            position.y += dy;
        }
    }

    public void rotate(float dPitch, float dYaw) {
        rotation.x += dPitch;
        rotation.y += dYaw;

        // Limitar la rotación del pitch
        if (rotation.x > 90) rotation.x = 90;
        if (rotation.x < -90) rotation.x = -90;

        // Mantener yaw en un rango de 0 a 360
        rotation.y %= 360;
    }

    public void jump() {
        if (verticalSpeed == 0 && !isFlying) {
            verticalSpeed = 0.5f; // Velocidad de salto
        }
    }

    private boolean isColliding(World world, float x, float y, float z) {
        int blockX = (int) Math.floor(x);
        int blockY = (int) Math.floor(y);
        int blockZ = (int) Math.floor(z);
        return world.isBlockAt(blockX, blockY, blockZ);
    }

    public Vector3f getPosition() {
        return position;
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public boolean isFlying() {
        return isFlying;
    }

    public void setFlying(boolean flying) {
        isFlying = flying;
    }
}

package com.juegovoxel.input;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import com.juegovoxel.entity.Player;
import com.juegovoxel.world.World;

public class InputHandler {

    private Player player;
    private World world;
    
    private boolean onGround;

    public InputHandler(Player player, World world) {
        this.player = player;
        this.world = world;
        this.onGround = false;
        Mouse.setGrabbed(true); // Oculta el cursor y lo centra en la pantalla
    }

    public void checkInput() {
        // Movimiento del jugador
        float moveSpeed = 0.05f;
        float dx = 0, dy = 0, dz = 0;

        if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
            dz -= moveSpeed;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
            dz += moveSpeed;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
            dx -= moveSpeed;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
            dx += moveSpeed;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
            if (player.isFlying()) {
                dy += moveSpeed;
            } else {
                player.jump();
            }
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
            if (player.isFlying()) {
                dy -= moveSpeed;
            }
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_F)) {
        	player.setFlying(!player.isFlying());
            
        }
        
        player.move(dx, dy, dz, world);
      

        // Rotación de la cámara con el mouse
        float mouseDX = Mouse.getDX() * player.mouseSensitivity;
        float mouseDY = -Mouse.getDY() * player.mouseSensitivity;
        player.rotate(mouseDY, mouseDX);
    }
}

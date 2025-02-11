package com.juegovoxel.game;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import com.juegovoxel.entity.Player;
import com.juegovoxel.input.InputHandler;
import com.juegovoxel.render.Renderer;
import com.juegovoxel.render.TextureManager;
import com.juegovoxel.world.World;

public class Game {
    private World world;
    private Player player;
    private InputHandler inputHandler;
    private Renderer renderer;

    public void init(int width, int height, float zNear) {
        renderer = new Renderer();
        renderer.initGL(width, height, zNear);
        TextureManager.loadTextures(); // Cargar las texturas aquí
        
        world = new World();
        player = new Player();
        inputHandler = new InputHandler(player, world);
    }

    public void update() {
        inputHandler.checkInput();
        player.update(world);
        world.update(player);
    }

    public void render() {
        renderer.clearScreen();
        renderer.loadIdentity();
        renderer.applyCameraTransform(player);
        world.render(renderer);
        player.render(renderer);
        renderer.renderInventory(player.getInventory());
    }

    public void cleanUp() {
    	// Limpieza de recursos si es necesario
    	renderer.clearScreen();
    	Keyboard.destroy();
    	Mouse.destroy();
    	Display.destroy();
       
    }
}

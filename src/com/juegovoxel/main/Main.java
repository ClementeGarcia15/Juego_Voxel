package com.juegovoxel.main;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import com.juegovoxel.game.Game;

public class Main {
    private Game game;
    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;
    private static final float ZNEAR = 0.0005f;
    public static final String TITLE = "Juego Voxel";

    public Main() {
        initDisplay();
        initGame();
        gameLoop();
        cleanUp();
    }

    private void initDisplay() {
        try {
            Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
            Display.setTitle(TITLE);
            Display.create();
            Keyboard.create();
        } catch (LWJGLException e) {
            e.printStackTrace();
        }
    }

    private void initGame() {
        game = new Game();
        game.init(WIDTH, HEIGHT, ZNEAR);
    }

    private void gameLoop() {
        while (!Keyboard.isKeyDown(Keyboard.KEY_ESCAPE) && !Display.isCloseRequested()) {
            game.update();
            game.render();
            Display.update();
            Display.sync(60); // Limita a 60 FPS
        }
    }

    private void cleanUp() {
        if (game != null) {
            game.cleanUp();
        }
        Display.destroy();
    }

    public static void main(String[] args) {
        new Main();
    }
}

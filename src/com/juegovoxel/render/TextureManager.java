package com.juegovoxel.render;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.lwjgl.BufferUtils;

import java.io.IOException;
import java.util.HashMap;
import java.nio.FloatBuffer;

public class TextureManager {
    private static HashMap<String, Texture> textures = new HashMap<>();

    public static void loadTextures() {
        try {
            textures.put("dirt", loadTexture("dirt"));
            textures.put("grass", loadTexture("grass"));
            textures.put("grass_top", loadTexture("grass_top"));
            textures.put("grass_side", loadTexture("grass_side"));
            // Añadir más texturas según sea necesario
            
            System.out.println("Texturas cargadas: " + textures.keySet());
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Texture loadTexture(String name) throws IOException {
        return TextureLoader.getTexture("PNG", TextureManager.class.getResourceAsStream("/textures/" + name +".png"));
    }

    public static void bindTexture(String name) {
        Texture texture = textures.get(name);
        if (texture != null) {
            texture.bind();
        }else {
        	// Depuración
            System.out.println("No se encontró la textura: " + name);
        }
    }
}

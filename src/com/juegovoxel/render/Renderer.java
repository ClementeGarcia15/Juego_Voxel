package com.juegovoxel.render;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;
import com.juegovoxel.entity.Player;
import com.juegovoxel.game.Inventory;
import com.juegovoxel.world.BlockType;

public class Renderer {

	public void initGL(int width, int height, float zNear) {
	    GL11.glEnable(GL11.GL_TEXTURE_2D);
	    GL11.glEnable(GL11.GL_DEPTH_TEST);
	    GL11.glDepthFunc(GL11.GL_LEQUAL); // Modo de comparación de profundidad correcto
	    GL11.glEnable(GL11.GL_CULL_FACE);
	    GL11.glCullFace(GL11.GL_BACK); // Culling de la cara trasera
	    GL11.glShadeModel(GL11.GL_SMOOTH); // Habilita interpolación de colores

	    // Color de fondo (cielo azul)
	    GL11.glClearColor(0.5f, 0.8f, 1.0f, 1.0f);
	    GL11.glClearDepth(1.0);

	    // Configuración de la proyección
	    GL11.glMatrixMode(GL11.GL_PROJECTION);
	    GL11.glLoadIdentity();
	    GLU.gluPerspective(70.0f, (float) width / height, zNear, 1000.0f);
	    GL11.glViewport(0, 0, width, height); // Define el área de renderizado
	    GL11.glMatrixMode(GL11.GL_MODELVIEW);
	}

    public void clearScreen() {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
    }

    public void loadIdentity() {
        GL11.glLoadIdentity();
    }

    public void applyCameraTransform(Player player) {
        // Rotación de la cámara
        GL11.glRotatef(player.getRotation().x, 1, 0, 0); // Pitch
        GL11.glRotatef(player.getRotation().y, 0, 1, 0); // Yaw

        // Traslación de la cámara
        GL11.glTranslatef(-player.getPosition().x, -player.getPosition().y, -player.getPosition().z);
    }

    public void renderBlock(BlockType type, int x, int y, int z, boolean[] facesToRender) {
        GL11.glPushMatrix();
        GL11.glTranslatef(x, y, z);

        // Cara superior (Y positiva)
        if (facesToRender[3]) {
            TextureManager.bindTexture(type.getTopTexture());
            GL11.glBegin(GL11.GL_QUADS);
                GL11.glNormal3f(0.0f, 1.0f, 0.0f);
                GL11.glTexCoord2f(0, 0); GL11.glVertex3f(0, 1, 1);
                GL11.glTexCoord2f(1, 0); GL11.glVertex3f(1, 1, 1);
                GL11.glTexCoord2f(1, 1); GL11.glVertex3f(1, 1, 0);
                GL11.glTexCoord2f(0, 1); GL11.glVertex3f(0, 1, 0);
            GL11.glEnd();
        }

        // Cara inferior (Y negativa)
        if (facesToRender[2]) {
            TextureManager.bindTexture(type.getBottomTexture());
            GL11.glBegin(GL11.GL_QUADS);
                GL11.glNormal3f(0.0f, -1.0f, 0.0f);
                GL11.glTexCoord2f(0, 0); GL11.glVertex3f(0, 0, 0);
                GL11.glTexCoord2f(1, 0); GL11.glVertex3f(1, 0, 0);
                GL11.glTexCoord2f(1, 1); GL11.glVertex3f(1, 0, 1);
                GL11.glTexCoord2f(0, 1); GL11.glVertex3f(0, 0, 1);
            GL11.glEnd();
        }

        // Cara frontal (Z positiva)
        if (facesToRender[5]) {
            TextureManager.bindTexture(type.getSideTexture());
            GL11.glBegin(GL11.GL_QUADS);
                GL11.glNormal3f(0.0f, 0.0f, 1.0f);
                GL11.glTexCoord2f(0, 0); GL11.glVertex3f(0, 0, 1);
                GL11.glTexCoord2f(-1, 0); GL11.glVertex3f(1, 0, 1);
                GL11.glTexCoord2f(-1, -1); GL11.glVertex3f(1, 1, 1);
                GL11.glTexCoord2f(0, -1); GL11.glVertex3f(0, 1, 1);
            GL11.glEnd();
        }

        // Cara posterior (Z negativa)
        if (facesToRender[4]) {
            TextureManager.bindTexture(type.getSideTexture());
            GL11.glBegin(GL11.GL_QUADS);
                GL11.glNormal3f(0.0f, 0.0f, -1.0f);
                GL11.glTexCoord2f(0, 0); GL11.glVertex3f(1, 0, 0); // Cambiar a (1, 0) y (0, 1)
                GL11.glTexCoord2f(-1, 0); GL11.glVertex3f(0, 0, 0);
                GL11.glTexCoord2f(-1, -1); GL11.glVertex3f(0, 1, 0); 
                GL11.glTexCoord2f(0, -1); GL11.glVertex3f(1, 1, 0);
            GL11.glEnd();
        }

        // Cara izquierda (X negativa)
        if (facesToRender[0]) {
            TextureManager.bindTexture(type.getSideTexture());
            GL11.glBegin(GL11.GL_QUADS);
                GL11.glNormal3f(-1.0f, 0.0f, 0.0f);
                GL11.glTexCoord2f(0, 0); GL11.glVertex3f(0, 0, 0);
                GL11.glTexCoord2f(-1, 0); GL11.glVertex3f(0, 0, 1);
                GL11.glTexCoord2f(-1, -1); GL11.glVertex3f(0, 1, 1); 
                GL11.glTexCoord2f(0, -1); GL11.glVertex3f(0, 1, 0);
            GL11.glEnd();
        }

        // Cara derecha (X positiva)
        if (facesToRender[1]) {
            TextureManager.bindTexture(type.getSideTexture());
            GL11.glBegin(GL11.GL_QUADS);
                GL11.glNormal3f(1.0f, 0.0f, 0.0f);
                GL11.glTexCoord2f(0, 0); GL11.glVertex3f(1, 0, 1);
                GL11.glTexCoord2f(-1, 0); GL11.glVertex3f(1, 0, 0);
                GL11.glTexCoord2f(-1, -1); GL11.glVertex3f(1, 1, 0);
                GL11.glTexCoord2f(0, -1); GL11.glVertex3f(1, 1, 1);
            GL11.glEnd();
        }

        GL11.glPopMatrix();
    }

    public void renderEnemy(float x, float y, float z) {
        // Renderiza un enemigo simple (por ejemplo, un cubo rojo)
        GL11.glPushMatrix();
        GL11.glColor3f(1.0f, 1.0f, 0f); // Color rojo
        GL11.glTranslatef(x, y, z);
        GL11.glBegin(GL11.GL_QUADS);
        
        
        GL11.glVertex3f(0, 0, 0);
        GL11.glVertex3f(1, 0, 0);
        GL11.glVertex3f(1, 1, 0);
        GL11.glVertex3f(0, 1, 0);
        
        
            // Caras del cubo
        GL11.glEnd();
        GL11.glPopMatrix();
    }

    public void renderInventory(Inventory inventory) {
        // Renderiza el inventario en la pantalla
        // Puedes usar una vista ortográfica para dibujar 2D
    }
}

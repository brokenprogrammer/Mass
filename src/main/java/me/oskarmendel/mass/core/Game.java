/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2017 Oskar Mendel
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package me.oskarmendel.mass.core;

import me.oskarmendel.mass.entity.Entity;
import me.oskarmendel.mass.entity.masster.MassterBall;
import me.oskarmendel.mass.gfx.Mesh;
import me.oskarmendel.mass.gfx.Renderer;
import me.oskarmendel.mass.gfx.Screen;
import me.oskarmendel.mass.gfx.Texture;
import me.oskarmendel.mass.util.OBJLoader;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFWErrorCallback;

import static org.lwjgl.glfw.GLFW.*;

/**
 * The Game class initializes the game and starts the game loop.
 * When the loop ends this class disposes the game and its components.
 *
 * @author Oskar Mendel
 * @version 0.00.00
 * @name Game.java
 */
public class Game {

    public static final int SCREEN_WIDTH = 800;
    public static final int SCREEN_HEIGHT = 600;

    public static final String SCREEN_TITLE = "Mass";

    public static final boolean VSYNC = true;

    public static final float CAMERA_POS_STEP = 0.05f;

    /**
     * The error callback for GLFW.
     * Strong reference due to the garbage collector.
     */
    private GLFWErrorCallback errorCallback;

    /**
     * Shows if the game is running.
     */
    private boolean running;

    /**
     * GLFW window or screen used by the game.
     */
    private Screen screen;

    /**
     * Renderer to handle rendering.
     */
    private final Renderer renderer;

    /**
     * Camera of the game to handle the view of the game.
     */
    private final Camera camera;

    /**
     * Handles camera updates.
     */
    private final Vector3f cameraInc;

    /**
     * Default constructor for the game.
     */
    public Game() {
        renderer = new Renderer();
        camera = new Camera();
        cameraInc = new Vector3f(0, 0,0);
    }

    private Entity[] entities;

    /**
     * This method should be called to initialize and start the game.
     */
    public void start() {
        init();
        gameLoop();
        dispose();
    }

    /**
     * Initializes the game.
     */
    public void init() {
        // Set error callback.
        errorCallback = GLFWErrorCallback.createPrint(System.err);
        glfwSetErrorCallback(errorCallback);

        // Initialize GLFW.
        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW.");
        }

        // Create the GLFW screen.
        screen = new Screen(SCREEN_WIDTH, SCREEN_HEIGHT, SCREEN_TITLE, VSYNC);

        // Initialize renderer.
        renderer.init();

        Texture t = Texture.loadTexture("src/main/resources/textures/grassblock.png");
        try {
            Mesh mesh = OBJLoader.loadMesh("src/main/resources/models/cube.obj");

            mesh.setTexture(t);

            MassterBall massterBall = new MassterBall(mesh);
            massterBall.setPosition(0, -1, -2);

            entities = new Entity[]{massterBall};
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Initialization done, set running to true.
        running = true;
    }

    /**
     * The game loop
     */
    public void gameLoop() {
        while(running) {

            // Check if the game should close.
            if (screen.isClosing()) {
                running = false;
            }

            // Handle input
            input();

            // Update game and game logic.
            update();

            render();

            screen.update();
        }
    }

    /**
     * This method releases resources used by the game.
     */
    public void dispose() {
        // Dispose the renderer.
        renderer.dispose();

        // Release the screen and its resources.
        screen.destroy();

        // Terminate GLFW release the error callback.
        glfwTerminate();
        errorCallback.free();
    }

    /**
     * Handles input.
     */
    private void input() {
        cameraInc.set(0, 0,0);

        if (screen.isKeyPressed(GLFW_KEY_W)) {
            cameraInc.z = -1;
        } else if (screen.isKeyPressed(GLFW_KEY_S)) {
            cameraInc.z = 1;
        }

        if (screen.isKeyPressed(GLFW_KEY_A)) {
            cameraInc.x = -1;
        } else if (screen.isKeyPressed(GLFW_KEY_D)) {
            cameraInc.x = 1;
        }

        if (screen.isKeyPressed(GLFW_KEY_Z)) {
            cameraInc.y = -1;
        } else if (screen.isKeyPressed(GLFW_KEY_X)) {
            cameraInc.y = 1;
        }
    }

    /**
     * Updates the game and logic.
     */
    public void update() {
        // Update camera position.
        camera.movePosition(cameraInc.x * CAMERA_POS_STEP, cameraInc.y * CAMERA_POS_STEP, cameraInc.z * CAMERA_POS_STEP);

        // Update the camera view matrix.
        camera.updateViewMatrix();

        for (Entity entity : entities) {
            // Update rotation angle
            float rotation = entity.getRotation().y + 1;
            if ( rotation > 360 ) {
                rotation = 0;
            }
            entity.setRotation(0.0f, rotation, 0.0f);
        }
    }

    /**
     * Renders the game.
     */
    public void render() {
        renderer.render(this.camera, this.entities);
    }
}

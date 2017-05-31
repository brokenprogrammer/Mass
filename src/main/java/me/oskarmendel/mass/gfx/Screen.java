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

package me.oskarmendel.mass.gfx;

import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.NULL;

/**
 * Handles the window of the application.
 *
 * @author Oskar Mendel
 * @version 0.00.00
 * @name Screen.java
 */
public class Screen {

    /**
     * The window handle
     */
    private long id;

    /**
     * Key callback for the window
     */
    private final GLFWKeyCallback keyCallback;

    /**
     * Value showing if v-sync is on or off.
     */
    private boolean vsync;

    /**
     * Creates a new GLFW window and its OpenGL context with
     * specified width, height and title.
     *
     * @param width - Width of the screen.
     * @param height - Height of the screen.
     * @param title - Title of the window.
     * @param vsync - Set to true to put v-sync on.
     */
    public Screen (int width, int height, String title, boolean vsync) {
        this.vsync = vsync;

        // Set resizeable to false.
        glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);

        // Create the window
        id = glfwCreateWindow(width, height, title, NULL, NULL);
        if (id == NULL) {
            glfwTerminate();
            throw new RuntimeException("Failed to create GLFW window");
        }

        // Center window on the screen
        GLFWVidMode vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        glfwSetWindowPos(id, (vidMode.width() - width)/2, (vidMode.height() - height)/2);


        // Create OpenGL context
        glfwMakeContextCurrent(id);
        GL.createCapabilities();

        // Enable v-sync
        if (vsync) {
            glfwSwapInterval(1);
        }

        // Set key callback
        keyCallback = new GLFWKeyCallback() {
            @Override
            public void invoke(long window, int key, int scancode, int action, int mods) {
                if (key == GLFW_KEY_ESCAPE && action == GLFW_PRESS) {
                    glfwSetWindowShouldClose(window, true);
                }
            }
        };
        glfwSetKeyCallback(id, keyCallback);
    }

    /**
     * Update the screen.
     */
    public void update() {
        glfwSwapBuffers(id);
        glfwPollEvents();
    }

    /**
     * Destroys the window and releases the callbacks.
     */
    public void destroy() {
        glfwDestroyWindow(id);
        keyCallback.free();
    }

    /**
     * Returns if the window should close or not.
     *
     * @return true if the window should close, else false.
     */
    public boolean isClosing() {
        return glfwWindowShouldClose(id);
    }
}

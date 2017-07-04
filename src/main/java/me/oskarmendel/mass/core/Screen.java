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

import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

import org.joml.Matrix4f;

/**
 * Handles the window of the application.
 *
 * @author Oskar Mendel
 * @version 0.00.00
 * @name Screen.java
 */
public class Screen {
	
	/**
     * Field of View in Radians
     */
    public static final float FOV = (float) Math.toRadians(60.0f);
    
	/**
     * Distance to near plane.
     */
    public static final float Z_NEAR = 0.01f;
    
    /**
     * Distance to far plane.
     */
    public static final float Z_FAR = 1000.f;

    /**
     * The window handle
     */
    private long id;
    
    /**
     * 
     */
    private int width;
    
    /**
     * 
     */
    private int height;

    /**
     * Key callback for the window
     */
    private final GLFWKeyCallback keyCallback;

    /**
     * Value showing if v-sync is on or off.
     */
    private boolean vsync;

    /**
     *
     */
    private final ScreenOptions screenOptions;
    
    /**
     * 
     */
    private final Matrix4f projectionMatrix;

    /**
     * Creates a new GLFW window and its OpenGL context with
     * specified width, height and title.
     *
     * @param width - Width of the screen.
     * @param height - Height of the screen.
     * @param title - Title of the window.
     * @param vsync - Set to true to put v-sync on.
     * @param options - The Screen options for this Screen.
     */
    public Screen (int width, int height, String title, boolean vsync, ScreenOptions options) {
        this.width = width;
        this.height = height;
    	this.vsync = vsync;
    	this.screenOptions = options;

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

        // Make the window visible
        glfwShowWindow(id);

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

        // Setting the clear color.
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        
        glEnable(GL_DEPTH_TEST);
        glEnable(GL_STENCIL_TEST);

        // Enable OpenGL blending that gives support for transparencies.
        glEnable(GL_BLEND);
        //glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        
        this.projectionMatrix = new Matrix4f();
    }
    
    /**
     * 
     */
    public void init() {
    	
    }

    /**
     * Checks if specified key was pressed, returns true if the
     * key was pressed.
     *
     * @param keyCode - Keycode of the key to check.
     *
     * @return - True if the specified key was pressed, false otherwise.
     */
    public boolean isKeyPressed(int keyCode) {
        return glfwGetKey(id, keyCode) == GLFW_PRESS;
    }

    /**
     * Update the screen.
     */
    public void update() {
        glfwSwapBuffers(id);
        glfwPollEvents();
    }

    /**
     * Returns if the window should close or not.
     *
     * @return true if the window should close, else false.
     */
    public boolean isClosing() {
        return glfwWindowShouldClose(id);
    }
    
    /**
     * Returns the handle of this screen.
     * 
     * @return - The handle for this screen.
     */
    public long getScreenHandle() {
    	return this.id;
    }
    
    /**
     * Returns the width of this screen.
     * 
     * @return - The width value of this screen.
     */
    public int getWidth() {
    	return this.width;
    }
    
    /**
     * Returns the height of this screen.
     * 
     * @return - The height value of this screen.
     */
    public int getHeight() {
    	return this.height;
    }
    
    /**
     * Returns the vsync value for this screen.
     * 
     * @return - The vsync value set for this screen.
     */
    public boolean getVsync() {
    	return this.vsync;
    }

    /**
     * Returns the Screen options for this Screen.
     *
     * @return - The ScreenOptions value for this Screen.
     */
    public ScreenOptions getScreenOptions() {
        return this.screenOptions;
    }

    /**
     * 
     * @return
     */
    public Matrix4f getProjectionMatrix() {
    	return this.projectionMatrix;
    }
    
    /**
     * 
     * @return
     */
    public Matrix4f updateProjectionMatrix() {
    	float aspectRatio = (float) width / (float) height;
        return projectionMatrix.setPerspective(FOV, aspectRatio, Z_NEAR, Z_FAR);
    }
    
    /**
     * 
     * @param matrix
     * @param width
     * @param height
     * @return
     */
    public static Matrix4f updateProjectionMatrix(Matrix4f matrix, int width, int height) {
    	float aspectRatio = (float) width / (float) height;
        return matrix.setPerspective(FOV, aspectRatio, Z_NEAR, Z_FAR);
    }
    
    /**
     * Destroys the window and releases the callbacks.
     */
    public void destroy() {
        glfwDestroyWindow(id);
        keyCallback.free();
    }
}

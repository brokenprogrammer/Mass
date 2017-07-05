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
     * Title for this Screen.
     */
    private final String title;
    
    /**
     * Width for this Screen.
     */
    private int width;
    
    /**
     * Height for this Screen.
     */
    private int height;

    /**
     * Value showing if v-sync is on or off.
     */
    private boolean vsync;

    /**
     * ScreenOptions object that contains options for this Screen.
     */
    private final ScreenOptions screenOptions;
    
    /**
     * Projection matrix for the Screen.
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
        this.title = title;
        this.width = width;
        this.height = height;
    	this.vsync = vsync;
    	this.screenOptions = options;
        this.projectionMatrix = new Matrix4f();
    }
    
    /**
     * Initializes the Screen by settings its callbacks and applying options specified in the
     * ScreenOptions object for this Screen.
     */
    public void init() {
        // Set resizeable to false.
        glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);

        if (this.screenOptions.getCompatibleProfile()) {
            glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_COMPAT_PROFILE);
        } else {
            glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
            glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);
        }

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

        // Enable v-sync
        if (this.getVsync()) {
            glfwSwapInterval(1);
        }

        // Make the window visible
        glfwShowWindow(id);

        GL.createCapabilities();

        // Set key callback
        GLFWKeyCallback keyCallback = new GLFWKeyCallback() {
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

        if (screenOptions.getShowTriangles()) {
            glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
        }

        // Enable OpenGL blending that gives support for transparencies.
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        if (screenOptions.getCullFace()) {
            glEnable(GL_CULL_FACE);
            glCullFace(GL_BACK);
        }

        // Antialiasing
        if (screenOptions.getAntialiasing()) {
            glfwWindowHint(GLFW_SAMPLES, 4);
        }
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
     * Getter for the projectionMatrix of this Screen.
     *
     * @return - The projectionMatrix of this Screen.
     */
    public Matrix4f getProjectionMatrix() {
    	return this.projectionMatrix;
    }
    
    /**
     * Updates the projectionMatrix based of the aspectRatio and returns
     * the updated projectionMatrix.
     *
     * @return - Updated projectionMatrix.
     */
    public Matrix4f updateProjectionMatrix() {
    	float aspectRatio = (float) width / (float) height;
        return projectionMatrix.setPerspective(FOV, aspectRatio, Z_NEAR, Z_FAR);
    }
    
    /**
     * Updates the specified projectionMatrix based on the specified with and
     * height.
     *
     * @param matrix - ProjectionMatrix to update.
     * @param width - Target width.
     * @param height - Target height.
     *
     * @return - Updated projectionMatrix.
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
        //keyCallback.free();
    }
}

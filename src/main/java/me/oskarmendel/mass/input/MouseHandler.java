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

package me.oskarmendel.mass.input;

import org.joml.Vector2d;
import org.joml.Vector2f;

import me.oskarmendel.mass.core.Screen;

import static org.lwjgl.glfw.GLFW.*;

/**
 * This class is used to handle mouse input.
 *
 * @author Oskar Mendel
 * @version 0.00.00
 * @name MouseHandler.java
 */
public class MouseHandler {
	
	/**
	 * Previous mouse coordinates.
	 */
	private Vector2d previousPos;
	
	/**
	 * Current mouse coordinates.
	 */
	private Vector2d currentPos;
	
	/**
	 * Dispel vector for the mouse handler.
	 */
	private Vector2f dispelVec;
	
	/**
	 * Boolean to check if the mouse is within the screen region.
	 */
	private boolean inScreen = false;
	
	/**
	 * Boolean to check if the left mouse button was pressed.
	 */
	private boolean leftButtonPressed = false;
	
	/**
	 * Boolean to check if the right mouse button was pressed.
	 */
	private boolean rightButtonPressed = false;
	
	/**
	 * Default constructor for the mouse handler initializing all the
	 * fields.
	 */
	public MouseHandler() {
		previousPos = new Vector2d(-1, -1);
		currentPos = new Vector2d(0, 0);
		dispelVec = new Vector2f();
	}
	
	/**
	 * Initializes the mouse handler and binds the mouse callbacks to
	 * the window handle of the specified screen.
	 * 
	 * @param screen - Screen to bind callbacks too.
	 */
	public void init(Screen screen) {
		// Set callback for the cursor position.
		glfwSetCursorPosCallback(screen.getScreenHandle(), (windowHandle, xpos, ypos) -> {
            currentPos.x = xpos;
            currentPos.y = ypos;
        });
		
		// Set callback for the mouse inside the screen callback.
		glfwSetCursorEnterCallback(screen.getScreenHandle(), (windowHandle, entered) -> {
			inScreen = entered;
		});
		
		// Set callback for mouse buttons.
		glfwSetMouseButtonCallback(screen.getScreenHandle(), (windowHandle, button, action, mods) -> {
			leftButtonPressed = button == GLFW_MOUSE_BUTTON_1 && action == GLFW_PRESS;
            rightButtonPressed = button == GLFW_MOUSE_BUTTON_2 && action == GLFW_PRESS;
		});
	}
	
	/**
	 * Updates the mouse input.
	 */
	public void input() {
		dispelVec.x = 0;
		dispelVec.y = 0;
		
		if (previousPos.x > 0 && previousPos.y > 0 && inScreen) {
			double deltaX = currentPos.x - previousPos.x;
			double deltaY = currentPos.y - previousPos.y;
			
			boolean rotateX = deltaX != 0;
			boolean rotateY = deltaY != 0;
			
			if (rotateX) {
				dispelVec.y = (float) deltaX;
			}
			
			if (rotateY) {
				dispelVec.x = (float) deltaY;
			}
		}
		
		previousPos.x = currentPos.x;
		previousPos.y = currentPos.y;
	}
	
	/**
	 * Getter for the dispel vector.
	 * 
	 * @return Dispel vector.
	 */
	public Vector2f getDispelVec() {
		return this.dispelVec;
	}
	
	/**
	 * Getter for the left mouse button boolean.
	 * 
	 * @return True of the left mouse button was pressed, false otherwise.
	 */
	public boolean isLeftButtonPressed() {
		return this.leftButtonPressed;
	}
	
	/**
	 * Getter for the right mouse button boolean.
	 * 
	 * @return True of the right mouse button was pressed, false otherwise.
	 */
	public boolean isRightButtonPressed() {
		return this.rightButtonPressed;
	}
}

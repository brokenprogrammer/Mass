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

package me.oskarmendel.mass.gfx.weather;

import me.oskarmendel.mass.gfx.Color;

/**
 * This class represents the weather modifier of Fog.
 *
 * @author Oskar Mendel
 * @version 0.00.00
 * @name Fog.java
 */
public class Fog {
	
	/**
	 * Value to check if the fog should be active or not.
	 */
	private boolean active;
	
	/**
	 * Color of the Fog.
	 */
	private Color color;
	
	/**
	 * Density of the Fog.
	 */
	private float density;
	
	/**
	 * Default Fog which is inactive.
	 */
	public static Fog NO_FOG = new Fog();
	
	/**
	 * Default constructor creates a non active Fog with
	 * the color black and no density.
	 */
	public Fog() {
		this.active = false;
		this.color = Color.BLACK;
		this.density = 0;
	}
	
	/**
	 * Creates a new Fog with the specified color and density
	 * and active value.
	 * 
	 * @param active - Active value for this Fog.
	 * @param color - Color value for this Fog.
	 * @param density - Density of this Fog.
	 */
	public Fog(boolean active, Color color, float density) {
		this.active = active;
		this.color = color;
		this.density = density;
	}

	/**
	 * Getter for the active value of this Fog.
	 * 
	 * @return - The active value of this Fog.
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * Setter for the active value of this Fog.
	 * 
	 * @param active - The active value to set.
	 */
	public void setActive(boolean active) {
		this.active = active;
	}

	/**
	 * Getter for the color value of this Fog.
	 * 
	 * @return - The color value of this Fog.
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * Setter for the color value of this Fog.
	 * 
	 * @param color - The color value to set.
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	/**
	 * Getter for the density value of this Fog.
	 * 
	 * @return - The density value of this Fog.
	 */
	public float getDensity() {
		return density;
	}

	/**
	 * Setter for the density of this Fog.
	 * 
	 * @param density - The density value to set.
	 */
	public void setDensity(float density) {
		this.density = density;
	}
}

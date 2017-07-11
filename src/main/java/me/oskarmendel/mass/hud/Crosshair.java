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

package me.oskarmendel.mass.hud;

import static org.lwjgl.system.MemoryUtil.NULL;
import org.lwjgl.nanovg.NVGColor;

import me.oskarmendel.mass.gfx.Color;

/**
 * This class represents a Crosshair.
 *
 * @author Oskar Mendel
 * @version 0.00.00
 * @name Crosshair.java
 */
public class Crosshair {
	
	/**
	 * NanoVG handle used to specify which NanoVG instance to draw to.
	 */
	private long vg;
	
	/**
	 * NanoVG color instance.
	 */
	private NVGColor hudColor;
	
	/**
	 * The Alpha value of this Crosshair.
	 */
	private int alpha;
	
	/**
	 * The Thickness value of this Crosshair.
	 */
	private float thickness;
	
	/**
	 * The Size value of this Crosshair.
	 */
	private int size;
	
	/**
	 * The Gap value of this Crosshair.
	 */
	private int gap;
	
	/**
	 * The Outline value of this Crosshair.
	 */
	private int outline;
	
	/**
	 * The Color value of this Crosshair.
	 */
	private Color color;
	
	/**
	 * The Dot value of this Crosshair.
	 */
	private boolean dot;
	
	/**
	 * Default constructor for the Crosshair creates a 
	 * default looking crosshair with set standard values.
	 */
	public Crosshair() {
		this.vg = NULL;
		
		this.alpha = 255;
		this.thickness = 0.5f;
		this.size = 5;
		this.gap = 0;
		this.outline = 0;
		this.color = Color.WHITE;
		this.dot = false;
	}
	
	/**
	 * Creates a new Crosshair with the specified configuration.
	 * 
	 * @param alpha - Alpha value of the Crosshair.
	 * @param thickness - Thickness of the Crosshair.
	 * @param size - Size of the Crosshair.
	 * @param gap - Gap of the Crosshair.
	 * @param outline - Outline of the Crosshair.
	 * @param color - Color value of the Crosshair.
	 * @param dot - Dot value of the Crosshair.
	 */
	public Crosshair(int alpha, float thickness, int size, int gap, 
			int outline, Color color, boolean dot) {
		
		this.vg = NULL;
		
		this.alpha = alpha;
		this.thickness = thickness;
		this.size = size;
		this.gap = gap;
		this.outline = outline;
		this.color = color;
		this.dot = dot;
	}
	
	/**
	 * Draws this Crosshair to the Screen.
	 */
	public void draw() {
		//TODO: Implement this. - Oskar Mendel 2017-07-11.
	}

	/**
	 * Getter for the NanoVG handle this Crosshair is using.
	 * 
	 * @return - The vg value of this Crosshair.
	 */
	public long getVg() {
		return vg;
	}

	/**
	 * Setter for the NanoVG handle of this Crosshair.
	 * 
	 * @param vg - The vg to set for this Crosshair.
	 */
	public void setVg(long vg) {
		this.vg = vg;
	}

	/**
	 * Getter for the NanoVG color instance this Crosshair is using.
	 * 
	 * @return - The NanoVG hudColor of this Crosshair.
	 */
	public NVGColor getHudColor() {
		return hudColor;
	}

	/**
	 * Setter for the NanoVG hudColor instance of this Crosshair.
	 * 
	 * @param hudColor - The hudColor instance to set for this Crosshair.
	 */
	public void setHudColor(NVGColor hudColor) {
		this.hudColor = hudColor;
	}

	/**
	 * Getter for the alpha value of this Crosshair.
	 * 
	 * @return - The alpha value of this Crosshair.
	 */
	public int getAlpha() {
		return alpha;
	}

	/**
	 * Setter for the alpha value of this Crosshair.
	 * 
	 * @param alpha - The alpha value to set for this Crosshair.
	 */
	public void setAlpha(int alpha) {
		this.alpha = alpha;
	}

	/**
	 * Getter for the thickness value of this Crosshair.
	 * 
	 * @return - The thickness value of this Crosshair.
	 */
	public float getThickness() {
		return thickness;
	}

	/**
	 * Setter for the thickness value of this Crosshair.
	 * 
	 * @param thickness - The thickness value to set for this Crosshair.
	 */
	public void setThickness(float thickness) {
		this.thickness = thickness;
	}

	/**
	 * Getter for the size value of this Crosshair.
	 * 
	 * @return - The size value of this Crosshair.
	 */
	public int getSize() {
		return size;
	}

	/**
	 * Setter for the size value of this Crosshair.
	 * 
	 * @param size - The size value to set for this Crosshair.
	 */
	public void setSize(int size) {
		this.size = size;
	}

	/**
	 * Getter for the gap value of this Crosshair.
	 * 
	 * @return - The gap value of this Crosshair.
	 */
	public int getGap() {
		return gap;
	}

	/**
	 * Setter for the gap value of this Crosshair.
	 * 
	 * @param gap - The gap value to set for this Crosshair.
	 */
	public void setGap(int gap) {
		this.gap = gap;
	}

	/**
	 * Getter for the outline value of this Crosshair.
	 * 
	 * @return - The outline value of this Crosshair.
	 */
	public int getOutline() {
		return outline;
	}

	/**
	 * Setter for the outline value of this Crosshair.
	 * 
	 * @param outline - The outline value to set for this Crosshair.
	 */
	public void setOutline(int outline) {
		this.outline = outline;
	}

	/**
	 * Getter for the color value of this Crosshair.
	 * 
	 * @return - The color value of this Crosshair.
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * Setter for the color value of this Crosshair.
	 * 
	 * @param color - The color value to set for this Crosshair.
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	/**
	 * Getter for the dot value of this Crosshair.
	 * 
	 * @return - The dot value of this Crosshair.
	 */
	public boolean isDot() {
		return dot;
	}

	/**
	 * Setter for the dot value of this Crosshair.
	 * 
	 * @param dot - The dot value to set for this Crosshair.
	 */
	public void setDot(boolean dot) {
		this.dot = dot;
	}
}

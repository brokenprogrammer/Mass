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

import static org.lwjgl.nanovg.NanoVG.NVG_ALIGN_LEFT;
import static org.lwjgl.nanovg.NanoVG.NVG_ALIGN_TOP;
import static org.lwjgl.nanovg.NanoVG.nvgFillColor;
import static org.lwjgl.nanovg.NanoVG.nvgFontFace;
import static org.lwjgl.nanovg.NanoVG.nvgFontSize;
import static org.lwjgl.nanovg.NanoVG.nvgText;
import static org.lwjgl.nanovg.NanoVG.nvgTextAlign;

/**
 * This class represents a Hud component to represent text.
 *
 * @author Oskar Mendel
 * @version 0.00.00
 * @name HudComponent.java
 */
public class HudTextComponent implements HudComponent{
	
	//TODO: TEMP.
	private static final String FONT_NAME = "";
	
	/**
	 * NanoVG handle used to specify which NanoVG instance to draw to.
	 */
	private long vg;
	
	/**
	 * NanoVG color instance.
	 */
	private NVGColor color;
	
	/**
	 * Text to display when drawing this HudTextComponent.
	 */
	private String text;
	
	/**
	 * The x coordinate to display the text at.
	 */
	private int x;
	
	/**
	 * The y coordinate to display the text at.
	 */
	private int y;
	
	/**
	 * Creates a new HudTextComponent with the specified text value
	 * at the specified x and y coordinates.
	 * 
	 * @param text - Text value.
	 * @param x - X coordinate.
	 * @param y - Y coordinate.
	 */
	public HudTextComponent(String text, int x, int y) {
		this.vg = NULL;
		this.text = text;
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Renders this HudTextComponent.
	 */
	@Override
	public void draw() {
        nvgFontSize(vg, 40.0f);
        nvgFontFace(vg, FONT_NAME);
        nvgTextAlign(vg, NVG_ALIGN_LEFT | NVG_ALIGN_TOP);
        nvgFillColor(vg, rgba(0xe6, 0xea, 0xed, 255, color));
        nvgText(vg, this.x, this.y, this.text);
	}

	/**
	 * Converts an rgba value into the NanoVG color format and places it
	 * into the specified NVGColor instance.
	 * 
	 * @param r - Red value.
	 * @param g - Green value.
	 * @param b - Blue value.
	 * @param a - Alpha value.
	 * @param color - NVGColor instance.
	 * 
	 * @return - The NVGColor instance now with a new color value.
	 */
	private NVGColor rgba(int r, int g, int b, int a, NVGColor color) {
		color.r(r / 255.0f);
		color.g(g / 255.0f);
		color.b(b / 255.0f);
		color.a(a / 255.0f);

        return color;
    }
	
	/**
	 * Getter for the NanoVG handle this HudTextComponent is using.
	 * 
	 * @return - The vg value of this HudTextComponent.
	 */
	public long getVg() {
		return vg;
	}

	/**
	 * Setter for the NanoVG handle of this HudTextComponent.
	 * 
	 * @param vg - The vg to set for this HudTextComponent.
	 */
	public void setVg(long vg) {
		this.vg = vg;
	}

	/**
	 * Getter for the NanoVG color instance this HudTextComponent is using.
	 * 
	 * @return - The NanoVG hudColor of this HudTextComponent.
	 */
	public NVGColor getColor() {
		return color;
	}

	/**
	 * Setter for the NanoVG hudColor instance of this HudTextComponent.
	 * 
	 * @param hudColor - The hudColor instance to set for this HudTextComponent.
	 */
	public void setColor(NVGColor color) {
		this.color = color;
	}

	/**
	 * Getter for the text value of this HudTextComponent.
	 * 
	 * @return - The text value of this HudTextComponent.
	 */
	public String getText() {
		return text;
	}

	/**
	 * Setter for the text value of this HudTextComponent.
	 * 
	 * @param text - The text value to set for this HudTextComponent.
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * Getter for the x coordinate value of this HudTextComponent.
	 * 
	 * @return - The x coordinate value of this HudTextComponent.
	 */
	public int getX() {
		return x;
	}

	/**
	 * Setter for the x coordinate value of this HudTextComponent.
	 * 
	 * @param x - The x coordinate value to set for this HudTextComponent.
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * Getter for the y coordinate value of this HudTextComponent.
	 * 
	 * @return - The y coordinate value of this HudTextComponent.
	 */
	public int getY() {
		return y;
	}

	/**
	 * Setter for the y coordinate value of this HudTextComponent.
	 * 
	 * @param y - The y coordinate value to set for this HudTextComponent.
	 */
	public void setY(int y) {
		this.y = y;
	}
	
	/**
	 * Setter for the x and y coordinate value of this HudTextComponent.
	 * 
	 * @param x - X Coordinate to set for this HudTextComponent.
	 * @param y - Y Coordinate to set for this HudTextComponent.
	 */
	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}
}

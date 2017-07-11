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

import me.oskarmendel.mass.core.Screen;
import me.oskarmendel.mass.core.ScreenOptions;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.nanovg.NVGColor;
import static org.lwjgl.nanovg.NanoVG.*;
import static org.lwjgl.nanovg.NanoVGGL3.*;
import static org.lwjgl.system.MemoryUtil.NULL;

/**
 * This class handles the ingame Hud.
 *
 * @author Oskar Mendel
 * @version 0.00.00
 * @name Hud.java
 */
public class Hud {
	
	/**
	 * TODO: Read in NVG docs what this exactly is for. - Oskar Mendel 2017-07-11.
	 */
	private static final String FONT_NAME = "";
	
	/**
	 * NanoVG handle used to specify which NanoVG instnace to draw to.
	 */
	private long vg;
	
	/**
	 * Font to use in the NanoVG instance.
	 */
	private Font font;
	
	/**
	 * List of HudComponents to walk through and draw.
	 */
	List<HudComponent> hudComponents;
	
	/**
	 * The Width value of the NanoVG space.
	 */
	private int width;
	
	/**
	 * The Height value of the NanoVG space.
	 */
	private int height;
	
	/**
	 * NanoVG color instance.
	 */
	private NVGColor color;
	
	/**
	 * Creates a new Hud using the specified ScreenOptions, Font, width and height values.
	 * 
	 * @param options - Screen options.
	 * @param font - Font to use in the Hud.
	 * @param width - Width of the Hud space.
	 * @param height - Height of the Hud space.
	 * 
	 * @throws Exception
	 */
	public Hud(ScreenOptions options, Font font, int width, int height) throws Exception {
		this.hudComponents = new ArrayList<>();
		this.font = font;
		this.width = width;
		this.height = height;
		
		this.vg = options.getAntialiasing() ? nvgCreate(NVG_ANTIALIAS | NVG_STENCIL_STROKES) : nvgCreate(NVG_STENCIL_STROKES);
		if (this.vg == NULL) {
			throw new Exception("Failed to initialize NanoVG");
		}
		
		int fontId = nvgCreateFontMem(vg, FONT_NAME, font.getFontBuffer(), 0);
		if (fontId == -1) {
			throw new Exception("Failed to add font to NanoVG");
		}
		
		this.color = NVGColor.create();
	}
	
	/**
	 * Renders the Hud and all its components then restores the 
	 * Screen.
	 * 
	 * @param screen - Screen to restore.
	 */
	public void render(Screen screen) {
		nvgBeginFrame(vg, this.width, this.height, 1);
		
		// Render each individual Hud component.
		for (HudComponent component : this.hudComponents) {
			component.draw();
		}
        
		nvgEndFrame(vg);
		
		// Restores the Screen state that NanoVG might have manipulated.
		screen.restore();
	}
	
	/**
	 * Adds a new HudComponent to the list of components to render.
	 * Sets the NanoVG handle and NVGColor instance for the added components.
	 * 
	 * @param component - HudComponent to add to the render list.
	 */
	public void addHudComponent(HudComponent component) {
		if (component instanceof HudTextComponent) {
			((HudTextComponent) component).setVg(this.vg);
			((HudTextComponent) component).setColor(this.color);
			this.hudComponents.add(component);
		}
	}
	
	/**
	 * Getter for the font value this Hud is using.
	 * 
	 * @return - The font that is being used in this Hud.
	 */
	public Font getFont() {
		return font;
	}

	/**
	 * Setter for the Font value of this Hud.
	 * 
	 * @param font - The font value to set for this Hud.
	 */
	public void setFont(Font font) {
		this.font = font;
	}

	/**
	 * Deletes this Hud freeing up used resources.
	 */
	public void delete() {
		nvgDelete(this.vg);
	}
}
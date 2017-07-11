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
	 * 
	 */
	private static final String FONT_NAME = "";
	
	/**
	 * 
	 */
	private long vg;
	
	/**
	 * 
	 */
	private Font font;
	
	/**
	 * 
	 */
	List<HudComponent> hudComponents;
	
	/**
	 * 
	 */
	private int width;
	
	/**
	 * 
	 */
	private int height;
	
	/**
	 * 
	 */
	private NVGColor color;
	
	/**
	 * 
	 * @param options
	 * @param font
	 * @param width
	 * @param height
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
	 * TODO: HudComponent list and draw all hud components.
	 * @param screen
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
	 * 
	 * @param component
	 */
	public void addHudComponent(HudComponent component) {
		if (component instanceof HudTextComponent) {
			((HudTextComponent) component).setVg(this.vg);
			((HudTextComponent) component).setColor(this.color);
			this.hudComponents.add(component);
		}
	}
	
	/**
	 * @return the font
	 */
	public Font getFont() {
		return font;
	}

	/**
	 * @param font the font to set
	 */
	public void setFont(Font font) {
		this.font = font;
	}

	/**
	 * 
	 */
	public void delete() {
		nvgDelete(this.vg);
	}
}
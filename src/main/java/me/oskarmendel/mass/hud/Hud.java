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

import java.nio.DoubleBuffer;

import org.lwjgl.nanovg.NVGColor;
import static org.lwjgl.nanovg.NanoVG.*;
import static org.lwjgl.nanovg.NanoVGGL3.*;
import static org.lwjgl.system.MemoryUtil.NULL;
import org.lwjgl.system.MemoryUtil;

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
	private static final String FONT_NAME = "BOLD";
	
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
	 */
	private DoubleBuffer posx;
	private DoubleBuffer posy;
	
	/**
	 * 
	 * @param options
	 * @param font
	 * @param width
	 * @param height
	 * @throws Exception
	 */
	public Hud(ScreenOptions options, Font font, int width, int height) throws Exception {
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
		
		posx = MemoryUtil.memAllocDouble(1);
		posy = MemoryUtil.memAllocDouble(1);
	}
	
	/**
	 * TODO: HudComponent list and draw all hud components.
	 * @param screen
	 */
	public void render(Screen screen) {
		nvgBeginFrame(vg, this.width, this.height, 1);
		
		// Upper ribbon
        nvgBeginPath(vg);
        nvgRect(vg, 0, this.height - 100, this.width, 50);
        nvgFillColor(vg, rgba(0x23, 0xa1, 0xf1, 200, color));
        nvgFill(vg);
		
        // Render text
        nvgFontSize(vg, 40.0f);
        nvgFontFace(vg, FONT_NAME);
        nvgTextAlign(vg, NVG_ALIGN_LEFT | NVG_ALIGN_TOP);
        nvgFillColor(vg, rgba(0xe6, 0xea, 0xed, 255, color));
        nvgText(vg, this.width - 180, this.height - 95, "Test 1231241");
        
		nvgEndFrame(vg);
		
		// Restores the Screen state that NanoVG might have manipulated.
		screen.restore();
	}
	
	/**
	 * TODO: Move to color class.
	 * @param r
	 * @param g
	 * @param b
	 * @param a
	 * @param color
	 * @return
	 */
	private NVGColor rgba(int r, int g, int b, int a, NVGColor color) {
		color.r(r / 255.0f);
		color.g(g / 255.0f);
		color.b(b / 255.0f);
		color.a(a / 255.0f);

        return color;
    }
	
	/**
	 * 
	 */
	public void delete() {
		
	}
}
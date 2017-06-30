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

package me.oskarmendel.mass.gfx.shadow;

import me.oskarmendel.mass.gfx.ArrayTexture;

import static org.lwjgl.opengl.GL30.glGenFramebuffers;
import static org.lwjgl.opengl.GL11.GL_DEPTH_COMPONENT;

/**
 * 
 *
 * @author Oskar Mendel
 * @version 0.00.00
 * @name ShadowBuffer.java
 */
public class ShadowBuffer {
	
	public static final int SHADOW_MAP_WIDTH = (int)Math.pow(65, 2);
	
	public static final int SHADOW_MAP_HEIGHT = SHADOW_MAP_WIDTH;
	
	private final int depthMapFBO;
	
	private final ArrayTexture depthMap;
	
	public ShadowBuffer() throws Exception {
		// Create frame buffer to render depth map.
		depthMapFBO = glGenFramebuffers();
		
		// Create the depth map textures.
		depthMap = new ArrayTexture(ShadowRenderer.NUM_CASCADES, SHADOW_MAP_WIDTH, SHADOW_MAP_HEIGHT, GL_DEPTH_COMPONENT);
		
		// Attatch depth map textures to frame buffer.
		
		// Set depth only.
		
		// Unbind frame buffer.
	}
	
	/**
	 * 
	 * @param start
	 */
	public void bindTextures(int start) {
		
	}
	
	public ArrayTexture getDepthMapTexture() {
        return depthMap;
    }
	
	/**
	 * 
	 * @return
	 */
	public int getDepthMapFBO() {
		return this.depthMapFBO;
	}
	
	/**
	 * 
	 */
	public void delete() {
		
	}
}

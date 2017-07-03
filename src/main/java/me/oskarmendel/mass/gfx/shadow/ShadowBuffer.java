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

import static org.lwjgl.opengl.GL30.glBindFramebuffer;
import static org.lwjgl.opengl.GL30.glGenFramebuffers;
import static org.lwjgl.opengl.GL30.glFramebufferTexture2D;
import static org.lwjgl.opengl.GL11.glDrawBuffer;
import static org.lwjgl.opengl.GL11.glReadBuffer;
import static org.lwjgl.opengl.GL30.glCheckFramebufferStatus;
import static org.lwjgl.opengl.GL30.glDeleteFramebuffers;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.GL_DEPTH_COMPONENT;
import static org.lwjgl.opengl.GL30.GL_FRAMEBUFFER;
import static org.lwjgl.opengl.GL30.GL_DEPTH_ATTACHMENT;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_NONE;
import static org.lwjgl.opengl.GL30.GL_FRAMEBUFFER_COMPLETE;

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
		glBindFramebuffer(GL_FRAMEBUFFER, depthMapFBO);
		glFramebufferTexture2D(GL_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, GL_TEXTURE_2D, depthMap.getIds()[0], 0);
		
		// Set depth only.
		glDrawBuffer(GL_NONE);
        glReadBuffer(GL_NONE);
		
        if (glCheckFramebufferStatus(GL_FRAMEBUFFER) != GL_FRAMEBUFFER_COMPLETE) {
            throw new Exception("Failed to create FrameBuffer");
        }
        
		// Unbind frame buffer.
        glBindFramebuffer(GL_FRAMEBUFFER, 0);
	}
	
	/**
	 * 
	 * @param start
	 */
	public void bindTextures(int start) {
		for (int i = 0; i < ShadowRenderer.NUM_CASCADES; i++) {
            glActiveTexture(start + i);
            glBindTexture(GL_TEXTURE_2D, depthMap.getIds()[i]);
        }
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
		glDeleteFramebuffers(depthMapFBO);
		depthMap.delete();
	}
}

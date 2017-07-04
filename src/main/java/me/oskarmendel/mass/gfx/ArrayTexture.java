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

package me.oskarmendel.mass.gfx;

import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glDeleteTextures;
import static org.lwjgl.opengl.GL11.glTexParameteri;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_DEPTH_COMPONENT;
import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_LINEAR;
import static org.lwjgl.opengl.GL11.GL_NONE;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_S;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_T;
import static org.lwjgl.opengl.GL12.GL_CLAMP_TO_EDGE;
import static org.lwjgl.opengl.GL14.GL_TEXTURE_COMPARE_MODE;

/**
 * This class represents an array of textures.
 * 
 * @author Oskar Mendel
 * @version 0.00.00
 * @name ArrayTexture.java
 */
public class ArrayTexture {
	
	/**
	 * Array of ids for all the textures created and stored in this texture array.
	 */
	private final int[] ids;
	
	/**
	 * Width of the textures within this texture array.
	 */
	private final int width;
	
	/**
	 * Height of the textures within this texture array.
	 */
	private final int height;
	
	/**
	 * Creates a new Array of Textures with the specified width and height.
	 * 
	 * @param size - Amount of textures to create.
	 * @param width - Width value of the textures.
	 * @param height - Height value of the textures.
	 * @param pixelFormat - Pixel format to use for the textures.
	 * 
	 * @throws Exception
	 */
	public ArrayTexture(int size, int width, int height, int pixelFormat) throws Exception {
		this.ids = new int[size];
		glGenTextures(ids);
		
		this.width = width;
		this.height = height;
		
		for (int i = 0; i < size; i++) {
			glBindTexture(GL_TEXTURE_2D, ids[i]);
            glTexImage2D(GL_TEXTURE_2D, 0, GL_DEPTH_COMPONENT, this.width, this.height, 0, pixelFormat, GL_FLOAT, (ByteBuffer) null);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_COMPARE_MODE, GL_NONE);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
		}
	}
	
	/**
	 * Getter for the array of texture ids.
	 * 
	 * @return - Array of texture ids stored in this texture array.
	 */
	public int[] getIds() {
		return this.ids;
	}
	
	/**
	 * Getter for the width value of the textures stored in 
	 * this texture array.
	 * 
	 * @return - Width value of the textures stored in this texture array.
	 */
	public int getWidth() {
		return this.width;
	}
	
	/**
	 * Getter for the height value of the textures stored in 
	 * this texture array.
	 * 
	 * @return - Height value of the textures stored in this texture array.
	 */
	public int getHeight() {
		return this.height;
	}
	
	/**
	 * Deletes all the textures created in this texture array.
	 */
	public void delete() {
		for (int id : this.ids) {
			glDeleteTextures(id);
		}
	}
}

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

import static org.lwjgl.opengl.GL11.glGenTextures;

/**
 * 
 * 
 * @author Oskar Mendel
 * @version 0.00.00
 * @name ArrayTexture.java
 */
public class ArrayTexture {
	
	private final int[] ids;
	
	private final int width;
	
	private final int height;
	
	public ArrayTexture(int size, int width, int height, int pixelFormat) throws Exception {
		this.ids = new int[size];
		glGenTextures(ids);
		
		this.width = width;
		this.height = height;
		
		for (int i = 0; i < size; i++) {
			//TODO: Build textures. - Oskar Mendel 2017-06-30
		}
	}
	
	public int[] getIds() {
		return this.ids;
	}
	
	public int getWidth() {
		return this.width;
	}
	
	public int getHeight() {
		return this.height;
	}
	
	public void delete() {
		
	}
}

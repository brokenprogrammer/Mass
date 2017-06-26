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

package me.oskarmendel.mass.util.assimp;

import java.util.HashMap;
import java.util.Map;

import me.oskarmendel.mass.gfx.Texture;

/**
 *
 * @author Oskar Mendel
 * @version 0.00.00
 * @name TextureCache.java
 */
public class TextureCache {
	
	private static TextureCache INSTANCE;
	
	private Map<String, Texture> textureMap;
	
	private TextureCache() {
		textureMap = new HashMap<>();
	}
	
	public static TextureCache getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new TextureCache();
		}
		
		return INSTANCE;
	}
	
	public Texture getTexture(String path) throws Exception{
		Texture texture = textureMap.get(path);
		
		if (texture == null) {
			texture = Texture.loadTexture(path);
			textureMap.put(path, texture);
		}
		
		return texture;
	}
}

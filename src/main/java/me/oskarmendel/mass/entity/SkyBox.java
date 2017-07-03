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

package me.oskarmendel.mass.entity;

import me.oskarmendel.mass.gfx.Color;
import me.oskarmendel.mass.gfx.Material;
import me.oskarmendel.mass.gfx.Mesh;
import me.oskarmendel.mass.gfx.Texture;
import me.oskarmendel.mass.util.assimp.StaticMeshLoader;

/**
 * This class represents a SkyBox for a Scene.
 *
 * @author Oskar Mendel
 * @version 0.00.00
 * @name SkyBox.java
 */
public class SkyBox extends Entity {
	
	/**
	 * Creates a new SkyBox by loading the model and 
	 * texture at the specified paths.
	 * 
	 * @param modelPath - Path for the SkyBox model to load.
	 * @param texturePath - Path for the textures of the SkyBox model.
	 * 
	 * @throws Exception - Throws exception when load is unsuccessful to 
	 * find specified resources.
	 */
	public SkyBox(String modelPath, String texturePath) throws Exception {
		super();
		
		Mesh mesh = StaticMeshLoader.load(modelPath, texturePath)[0];
		Texture texture = Texture.loadTexture(texturePath);
		
		mesh.setMaterial(new Material(texture));
		
		this.setMesh(mesh);
		this.setPosition(0, 0, 0);
	}
	
	/**
	 * Creates a new SkyBox by loading the specified model and
	 * setting the texture to the specified color.
	 * 
	 * @param modelPath - Path for the SkyBox model to load.
	 * @param color - Color to set for the Material of the SkyBox.
	 * 
	 * @throws Exception - Throws exception when load is unsuccessful to 
	 * find specified resources.
	 */
	public SkyBox(String modelPath, Color color) throws Exception {
		super();
		
		Mesh mesh = StaticMeshLoader.load(modelPath, "")[0];
		
		mesh.setMaterial(new Material(color, 0));
		
		this.setMesh(mesh);
		this.setPosition(0, 0, 0);
	}
}

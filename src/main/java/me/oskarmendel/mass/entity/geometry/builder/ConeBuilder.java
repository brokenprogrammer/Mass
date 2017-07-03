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

package me.oskarmendel.mass.entity.geometry.builder;

import org.joml.Quaternionf;
import org.joml.Vector3f;

import me.oskarmendel.mass.entity.geometry.Cone;
import me.oskarmendel.mass.gfx.Color;
import me.oskarmendel.mass.gfx.Material;

public class ConeBuilder implements GeometryBuilder{

	/**
	 * Cone object to build.
	 */
	private Cone cone;
	
	private boolean usingTexture; // If the sphere should be textured.
	private boolean usingColor;	  // If the sphere should be colored.
	
	/**
	 * Default constructor creating a new Cone object.
	 */
	public ConeBuilder() {
		cone = new Cone();
		usingColor = false;
		usingTexture = false;
	}
	
	/**
	 * Sets the position of the cone object to build.
	 * 
	 * @param position - Position of the cone object.
	 * 
	 * @return This ConeBuilder object.
	 */
	@Override
	public ConeBuilder setPosition(Vector3f position) {
		cone.setPosition(position.x, position.y, position.z);
		return this;
	}

	/**
	 * Sets the rotation of the cone object to build.
	 * 
	 * @param rotation - Rotation of the cone object.
	 * 
	 * @return This ConeBuilder object.
	 */
	@Override
	public ConeBuilder setRotation(Quaternionf rotation) {
		cone.setRotation(rotation);
		return this;
	}

	/**
	 * Sets the scale of the cone object to build.
	 * 
	 * @param scale - Scale of the cone object.
	 * 
	 * @return This ConeBuilder object.
	 */
	@Override
	public ConeBuilder setScale(float scale) {
		cone.setScale(scale);
		return this;
	}

	/**
	 * Sets the height value for the cone object to build.
	 * 
	 * @param height - Height value of this cone.
	 * 
	 * @return This ConeBuilder object.
	 */
	public ConeBuilder setHeight(float height) {
		cone.setHeight(height);
		
		return this;
	}
	
	/**
	 * Sets the radius value for the cone object to build.
	 * 
	 * @param radius - Radius value of this cone.
	 * 
	 * @return This ConeBuilder object.
	 */
	public ConeBuilder setRadius(float radius) {
		cone.setRadius(radius);
		
		return this;
	}
	
	/**s
	 * Sets the sides value for the cone object to build.
	 * 
	 * @param sides - Sides value of this cone.
	 * 
	 * @return This ConeBuilder object.
	 */
	public ConeBuilder setSides(int sides) {
		cone.setSides(sides);
		
		return this;
	}
	
	/**
	 * Set the color of the cone object to build.
	 * Setting a texture as well will override the color with the texture.
	 * 
	 * @param color - Color of the cone object
	 * 
	 * @return This ConeBuilder object.
	 */
	public ConeBuilder setColor(Color color) {
		cone.setColor(color);
		
		usingColor = true;
		
		return this;
	}
	
	/**
	 * Builds the cone and returns the built cone object.
	 */
	@Override
	public Cone build() {
		cone.generateVertices();
		cone.generateNormals();
		cone.generateTextureCoordinates();
		
		cone.generateMesh();
		
		Material mat;
		if (usingTexture) {
			mat = new Material(cone.getTexture());
		} else if (usingColor)  {
			mat = new Material();
			mat.setAmbientColor(cone.getColor().toVector4f());
	        mat.setDiffuseColor(cone.getColor().toVector4f());
	        mat.setSpecularColor(cone.getColor().toVector4f());
		} else {
			mat = new Material();
		}
		
		cone.getMesh().setMaterial(mat);
		
		return cone;
	}

}

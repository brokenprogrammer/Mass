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

import org.joml.Vector3f;

import me.oskarmendel.mass.entity.geometry.Sphere;
import me.oskarmendel.mass.gfx.Color;
import me.oskarmendel.mass.gfx.Material;
import me.oskarmendel.mass.gfx.Texture;

/**
 * Class to aid in building new Spheres through the
 * builder pattern.
 *
 * @author Oskar Mendel
 * @version 0.00.00
 * @name SphereBuilder.java
 */
public class SphereBuilder implements GeometryBuilder{

	/**
	 * Sphere object to build.
	 */
	private Sphere sphere;
	
	private boolean usingTexture; // If the sphere should be textured.
	private boolean usingColor;	  // If the sphere should be colored.
	
	/**
	 * Default constructor creating a new Sphere object.
	 */
	public SphereBuilder() {
		sphere = new Sphere();
		usingColor = false;
		usingTexture = false;
	}
	
	/**
	 * Sets the position of the sphere object to build.
	 * 
	 * @param position - Position of the sphere object.
	 * 
	 * @return This SphereBuilder object.
	 */
	@Override
	public SphereBuilder setPosition(Vector3f position) {
		sphere.setPosition(position.x, position.y, position.z);
		return this;
	}

	/**
	 * Sets the rotation of the sphere object to build.
	 * 
	 * @param rotation - Rotation of the sphere object.
	 * 
	 * @return This SphereBuilder object.
	 */
	@Override
	public SphereBuilder setRotation(Vector3f rotation) {
		sphere.setRotation(rotation.x, rotation.y, rotation.z);
		return this;
	}

	/**
	 * Sets the scale of the sphere object to build.
	 * 
	 * @param scale - Scale of the sphere object.
	 * 
	 * @return This SphereBuilder object.
	 */
	@Override
	public SphereBuilder setScale(float scale) {
		sphere.setScale(scale);
		return this;
	}
	
	/**
	 * Sets the amount of subdivisions for the sphere object to build.
	 * 
	 * @param subdivisions - Number of subdivisions to perform.
	 * 
	 * @return This SphereBuilder object.
	 */
	public SphereBuilder setSubdivisions(int subdivisions) {
		sphere.setSubdivisions(subdivisions);
		return this;
	}
	
	/**
	 * Set the color of the sphere object to build.
	 * Setting a texture as well will override the color with the texture.
	 * 
	 * @param color - Color of the sphere object
	 * 
	 * @return This SphereBuilder object.
	 */
	public SphereBuilder setColor(Color color) {
		sphere.setColor(color);
		
		usingColor = true;
		
		return this;
	}
	
	/**
	 * Set the texture of the sphere object to build.
	 * Setting a texture if there already is a color will override the
	 * color with the texture.
	 * 
	 * @param texture - Texture of the sphere object.
	 * 
	 * @return This SphereBuilder object.
	 */
	public SphereBuilder setTexture(Texture texture) {
		sphere.setTexture(texture);
		
		usingTexture = true;
		
		return this;
	}

	/**
	 * Builds the sphere and returns the built sphere object.
	 */
	@Override
	public Sphere build() {
		sphere.generateVertices();
		sphere.subdivide(sphere.getSubdivisions());
		sphere.generateNormals();
		sphere.generateTextureCoordinates();
		
		sphere.generateMesh();
		
		Material mat;
		if (usingTexture) {
			mat = new Material(sphere.getTexture());
		} else if (usingColor)  {
			mat = new Material();
			mat.setAmbientColor(sphere.getColor().toVector4f());
	        mat.setDiffuseColor(sphere.getColor().toVector4f());
	        mat.setSpecularColor(sphere.getColor().toVector4f());
		} else {
			mat = new Material();
		}
		
		sphere.getMesh().setMaterial(mat);
		
		return sphere;
	}
}

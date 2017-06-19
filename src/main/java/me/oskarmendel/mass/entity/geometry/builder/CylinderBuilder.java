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

import me.oskarmendel.mass.entity.geometry.Cylinder;
import me.oskarmendel.mass.gfx.Color;
import me.oskarmendel.mass.gfx.Material;
import me.oskarmendel.mass.gfx.Texture;

public class CylinderBuilder implements GeometryBuilder {

	/**
	 * Cylinder object to build.
	 */
	private Cylinder cylinder;
	
	private boolean usingTexture; // If the sphere should be textured.
	private boolean usingColor;	  // If the sphere should be colored.
	
	/**
	 * Default constructor creating a new Cylinder object.
	 */
	public CylinderBuilder() {
		cylinder = new Cylinder();
		usingColor = false;
		usingTexture = false;
	}
	
	/**
	 * Sets the position of the cylinder object to build.
	 * 
	 * @param position - Position of the cylinder object.
	 * 
	 * @return This CylinderBuilder object.
	 */
	@Override
	public CylinderBuilder setPosition(Vector3f position) {
		cylinder.setPosition(position.x, position.y, position.z);
		return this;
	}

	/**
	 * Sets the rotation of the cylinder object to build.
	 * 
	 * @param rotation - Rotation of the cylinder object.
	 * 
	 * @return This CylinderBuilder object.
	 */
	@Override
	public CylinderBuilder setRotation(Vector3f rotation) {
		cylinder.setRotation(rotation.x, rotation.y, rotation.z);
		return this;
	}

	/**
	 * Sets the scale of the cylinder object to build.
	 * 
	 * @param scale - Scale of the cylinder object.
	 * 
	 * @return This CylinderBuilder object.
	 */
	@Override
	public CylinderBuilder setScale(float scale) {
		cylinder.setScale(scale);
		return this;
	}
	
	/**
	 * Sets the height value for the cylinder object to build.
	 * 
	 * @param height - Height value of this cylinder.
	 * 
	 * @return This CylinderBuilder object.
	 */
	public CylinderBuilder setHeight(float height) {
		cylinder.setHeight(height);
		
		return this;
	}
	
	/**
	 * Sets the radius value for the cylinder object to build.
	 * 
	 * @param radius - Radius value of this cylinder.
	 * 
	 * @return This CylinderBuilder object.
	 */
	public CylinderBuilder setRadius(float radius) {
		cylinder.setRadius(radius);
		
		return this;
	}
	
	/**
	 * Sets the sides value for the cylinder object to build.
	 * 
	 * @param sides - Sides value of this cylinder.
	 * 
	 * @return This CylinderBuilder object.
	 */
	public CylinderBuilder setSides(int sides) {
		cylinder.setSides(sides);
		
		return this;
	}
	
	/**
	 * Set the color of the cylinder object to build.
	 * Setting a texture as well will override the color with the texture.
	 * 
	 * @param color - Color of the cylinder object
	 * 
	 * @return This CylinderBuilder object.
	 */
	public CylinderBuilder setColor(Color color) {
		cylinder.setColor(color);
		
		usingColor = true;
		
		return this;
	}
	
	/**
	 * Set the texture of the cylinder object to build.
	 * Setting a texture if there already is a color will override the
	 * color with the texture.
	 * 
	 * @param texture - Texture of the cylinder object.
	 * 
	 * @return This CylinderBuilder object.
	 */
	public CylinderBuilder setTexture(Texture texture) {
		cylinder.setTexture(texture);
		
		usingTexture = true;
		
		return this;
	}

	/**
	 * Builds the cylinder and returns the built cylinder object.
	 */
	@Override
	public Cylinder build() {
		cylinder.generateVertices();
		cylinder.generateNormals();
		cylinder.generateTextureCoordinates();
		
		cylinder.generateMesh();
		
		Material mat;
		if (usingTexture) {
			mat = new Material(cylinder.getTexture());
		} else if (usingColor)  {
			mat = new Material();
			mat.setAmbientColor(cylinder.getColor().toVector4f());
	        mat.setDiffuseColor(cylinder.getColor().toVector4f());
	        mat.setSpecularColor(cylinder.getColor().toVector4f());
		} else {
			mat = new Material();
		}
		
		cylinder.getMesh().setMaterial(mat);
		
		return cylinder;
	}

}

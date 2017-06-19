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

package me.oskarmendel.mass.entity.geometry;

import java.util.ArrayList;

import org.joml.Vector3f;
import org.joml.Vector3i;

import me.oskarmendel.mass.entity.Entity;
import me.oskarmendel.mass.gfx.Color;
import me.oskarmendel.mass.gfx.Material;
import me.oskarmendel.mass.gfx.Mesh;
import me.oskarmendel.mass.gfx.Texture;

/**
 * This class represents a cylinder.
 *
 * @author Oskar Mendel
 * @version 0.00.00
 * @name Cylinder.java
 */
public class Cylinder extends Entity implements Geometry {
	
	/**
	 * Vertex positions for the cylinder.
	 */
	private float[] cylinder_positions;
	
	// ArrayList used during the generation of the Cylinder.
	ArrayList<Vector3f> positions;
	
	/**
	 * Texture coordinates of the cylinder.
	 */
	private float[] cylinder_texture_coordinates;
	
	/**
	 * Normals for the cylinder.
	 */
	private float[] cylinder_normals;
	
	/**
	 * Indices for the cylinder.
	 */
	private int[] cylinder_indices;
	
	private float height; // Cylinder height.
	private float radius; // Cylinder radius.
	private int sides; // Amount of sides for the cylinder.
	
	/**
	 * Color of this Cylinder.
	 */
	private Color color;
	
	/**
	 * Texture of this Cylinder.
	 */
	private Texture texture;
	
	/**
	 * Default constructor for the Cylinder. 
	 */
	public Cylinder() {
		super();
		
		this.height = 1;
		this.radius = 1;
		this.sides = 10;
		
		this.color = null;
		this.texture = null;
	}
	
	/**
	 * Creates a new cylinder at the specified position
	 * using the specified rotation and scale.
	 * 
	 * @param position - Position of the cylinder.
	 * @param rotation - Rotation of the cylinder.
	 * @param scale - Scale of the cylinder.
	 */
	public Cylinder(Vector3f position, Vector3f rotation, float scale) {
		super();
		
		height = 1;
		radius = 1;
		sides = 10;
		
		generateVertices();
		generateNormals();
		generateTextureCoordinates();
		
		Mesh mesh = new Mesh(cylinder_positions, cylinder_texture_coordinates, cylinder_normals, cylinder_indices);
		Material mat = new Material();
		
		mesh.setMaterial(mat);
		setMesh(mesh);
		
		setPosition(position.x, position.y, position.z);
		setRotation(rotation.x, rotation.y, rotation.z);
		setScale(scale);
	}
	
	/**
	 * Creates a new cylinder at the specified position
	 * using the specified rotation, scale and color. 
	 * 
	 * @param position - Position of the cylinder.
	 * @param rotation - Rotation of the cylinder.
	 * @param scale - Scale of the cylinder.
	 * @param color - Color of the cylinder.
	 */
	public Cylinder(Vector3f position, Vector3f rotation, float scale, Color color) {
		super();
		
		height = 1;
		radius = 1;
		sides = 10;
		
		generateVertices();
		generateNormals();
		generateTextureCoordinates();
		
		Mesh mesh = new Mesh(cylinder_positions, cylinder_texture_coordinates, cylinder_normals, cylinder_indices);
		Material mat = new Material();
		
		mat.setAmbientColor(color.toVector4f());
        mat.setDiffuseColor(color.toVector4f());
        mat.setSpecularColor(color.toVector4f());
		
		mesh.setMaterial(mat);
		setMesh(mesh);
		
		setPosition(position.x, position.y, position.z);
		setRotation(rotation.x, rotation.y, rotation.z);
		setScale(scale);
	}
	
	/**
	 * Creates a new cylinder at the specified position
	 * using the specified rotation, scale and texture. 
	 * 
	 * @param position - Position of the cylinder.
	 * @param rotation - Rotation of the cylinder.
	 * @param scale - Scale of the cylinder.
	 * @param texture - Texture of the cylinder.
	 */
	public Cylinder(Vector3f position, Vector3f rotation, float scale, Texture texture) {
		super();
		
		height = 1;
		radius = 1;
		sides = 10;
		
		generateVertices();
		generateNormals();
		generateTextureCoordinates();
		
		Mesh mesh = new Mesh(cylinder_positions, cylinder_texture_coordinates, cylinder_normals, cylinder_indices);
		Material mat = new Material(texture);
		
		mesh.setMaterial(mat);
		setMesh(mesh);
		
		setPosition(position.x, position.y, position.z);
		setRotation(rotation.x, rotation.y, rotation.z);
		setScale(scale);
	}
	
	/**
	 * Generates the vertices and indices for the Cylinder.
	 */
	@Override
	public void generateVertices() {
		positions = new ArrayList<Vector3f>();
		ArrayList<Vector3i> indices = new ArrayList<Vector3i>();
		
		Vector3f v0 = new Vector3f(0, 0, 0);
		Vector3f v1 = new Vector3f(0, height, 0);
		positions.add(v0);
		positions.add(v1);
		
		float stepAngle = (float) ((2*Math.PI) / sides);
		for (int i = 0; i <= sides; i++) {
			float r = stepAngle * i;
			float x = (float) Math.cos(r) * radius;
			float z = (float) Math.sin(r) * radius;
			positions.add(new Vector3f(x, 0, z));
			positions.add(new Vector3f(x, height, z));
		}
		
		// Generate bottom indices
		for (int i = 0; i < (sides)*2; i+=2) {
			indices.add(new Vector3i(0, i+2, i+4));
		}
		
		//Generate top indices
		for (int i = 1; i < (sides)*2; i+=2) {
			indices.add(new Vector3i(1, i+2, i+4));
		}
		
		// Generating sides
		for (int i = 2; i < (sides+1)*2; i++) {
			indices.add(new Vector3i(i, i+1, i+2));
		}
		
		cylinder_positions = new float[positions.size()*3];
		for (int i = 0, k = 0; k < positions.size(); i+=3, k++) {
			cylinder_positions[i] = positions.get(k).x;
			cylinder_positions[i + 1] = positions.get(k).y;
			cylinder_positions[i + 2] = positions.get(k).z;
		}
		
		cylinder_indices = new int[indices.size()*3];
		for (int i = 0, k = 0; k < indices.size(); i+=3, k++) {
			cylinder_indices[i] = indices.get(k).x;
			cylinder_indices[i + 1] = indices.get(k).y;
			cylinder_indices[i + 2] = indices.get(k).z;
		}
	}

	/**
	 * Generates the normals for the Cylinder.
	 */
	@Override
	public void generateNormals() {
		float[] normals = new float[positions.size()*3];
		
		// TODO: These are not calculated properly, needs refactoring later on.
		// Oskar Mendel - 2017-06-15
		normals[0] = 0;
		normals[1] = -1;
		normals[2] = 0;
		
		for (int i = 3, k = 1; k < positions.size(); i+=3, k++) {
			Vector3f vec = positions.get(k);
			
			float x = vec.x;
			float y = vec.y;
			float z = vec.z;
			float dt = (float) Math.sqrt((x*x)+(y*y)+(z*z));
			
			x = x * (1.0f / dt);
			y = y * (1.0f / dt);
			z = z * (1.0f / dt);
			
			normals[i] = x;
			normals[i + 1] = y;
			normals[i + 2] = z;
		}
		
		cylinder_normals = normals;
	}

	/**
	 * Generates the uv mapping for the Cylinder.
	 */
	@Override
	public void generateTextureCoordinates() {
		// TODO: Switch to cube mapping?
		// Oskar Mendel - 2017-06-15
		cylinder_texture_coordinates = new float[positions.size()*2];
		
		for (int i = 2, k = 1; k < positions.size(); i+=2, k++) {
			Vector3f vertex = positions.get(k);
			
			cylinder_texture_coordinates[i] = (float)((Math.atan2(vertex.x, vertex.z) + Math.PI) / Math.PI / 2);
			cylinder_texture_coordinates[i + 1] = (float)((Math.acos(vertex.y) + Math.PI) / Math.PI - 1);
		}
	}
	
	/**
	 * Generates the mesh based upon the previously generated 
	 * vertices, indices, normals and texture coordinates.
	 */
	@Override
	public void generateMesh() {
		Mesh mesh = new Mesh(cylinder_positions, cylinder_texture_coordinates, cylinder_normals, cylinder_indices);
		setMesh(mesh);
	}
	
	/**
	 * Getter for the height of this cylinder.
	 * 
	 * @return - The height of this cylinder.
	 */
	public float getHeight() {
		return height;
	}

	/**
	 * Setter for the height of this cylinder.
	 * 
	 * @param height - Height value to set. 
	 */
	public void setHeight(float height) {
		this.height = height;
	}

	/**
	 * Getter for the radius of this cylinder.
	 * 
	 * @return - The radius of this cylinder.
	 */
	public float getRadius() {
		return radius;
	}

	/**
	 * Setter for the radius of this cylinder.
	 * 
	 * @param radius - Sides value to set. 
	 */
	public void setRadius(float radius) {
		this.radius = radius;
	}

	/**
	 * Getter for the sides of this cylinder.
	 * 
	 * @return - The amount sides of this cylinder.
	 */
	public int getSides() {
		return sides;
	}

	/**
	 * Setter for the sides of this cylinder.
	 * 
	 * @param sides - Sides value to set.
	 */
	public void setSides(int sides) {
		this.sides = sides;
	}

	/**
	 * Setter color of this cylinder.
	 * 
	 * @param color - Color value to set.
	 */
	public void setColor(Color color) {
		this.color = color;
	}
	
	/**
	 * Getter of the color of this cylinder.
	 * 
	 * @return - The color of this cylinder.
	 */
	public Color getColor() {
		return this.color;
	}
	
	/**
	 * Setter of the texture of this cylinder.
	 * 
	 * @param texture - Texture value to set.
	 */
	public void setTexture(Texture texture) {
		this.texture = texture;
	}
	
	/**
	 * Getter of the texture of this cylinder.
	 * 
	 * @return - The texture of this cylinder.
	 */
	public Texture getTexture() {
		return this.texture;
	}
}

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
 * This class represents a cone.
 *
 * @author Oskar Mendel
 * @version 0.00.00
 * @name Cone.java
 */
public class Cone extends Entity implements Geometry {

	/**
	 * Vertex positions for the cone.
	 */
	private float[] cone_positions;
	
	// ArrayList used during the generation of the Cone.
	ArrayList<Vector3f> positions;
	
	/**
	 * Texture coordinates for the cone.
	 */
	private float[] cone_texture_coordinates;
	
	/**
	 * Normals for the cone.
	 */
	private float[] cone_normals;
	
	/**
	 * Indices for the cone.
	 */
	private int[] cone_indices;
	
	
	private float height; // Cone height.
	private float radius; // Cone radius.
	private int sides; // Amount of sides for the cone.
	
	/**
	 * Color of this cone.
	 */
	private Color color;
	
	/**
	 * Texture of this cone.
	 */
	private Texture texture;
	
	/**
	 * Default constructor for the Cone. 
	 */
	public Cone() {
		super();
		
		this.height = 1;
		this.radius = 1;
		this.sides = 10;
		
		this.color = null;
		this.texture = null;
	}
	
	/**
	 * Creates a new cone at the specified position
	 * using the specified rotation and scale.
	 * 
	 * @param position - Position of the cone.
	 * @param rotation - Rotation of the cone.
	 * @param scale - Scale of the cone.
	 */
	public Cone(Vector3f position, Vector3f rotation, float scale) {
		super();
	
		height = 1;
		radius = 1;
		sides = 10;
		
		generateVertices();
		generateNormals();
		generateTextureCoordinates();
		
		Mesh mesh = new Mesh(cone_positions, cone_texture_coordinates, cone_normals, cone_indices);
		Material mat = new Material();
		
		mesh.setMaterial(mat);
		setMesh(mesh);
		
		setPosition(position.x, position.y, position.z);
		setRotation(rotation.x, rotation.y, rotation.z);
		setScale(scale);
	}
	
	/**
	 * Creates a new cone at the specified position
	 * using the specified rotation, scale and color. 
	 * 
	 * @param position - Position of the cone.
	 * @param rotation - Rotation of the cone.
	 * @param scale - Scale of the cone.
	 * @param color - Color of the cone.
	 */
	public Cone(Vector3f position, Vector3f rotation, float scale, Color color) {
		super();
		
		height = 1;
		radius = 1;
		sides = 10;
		
		generateVertices();
		generateNormals();
		generateTextureCoordinates();
		
		Mesh mesh = new Mesh(cone_positions, cone_texture_coordinates, cone_normals, cone_indices);
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
	 * Creates a new cone at the specified position
	 * using the specified rotation, scale and texture. 
	 * 
	 * @param position - Position of the cone.
	 * @param rotation - Rotation of the cone.
	 * @param scale - Scale of the cone.
	 * @param texture - Texture of the cone.
	 */
	public Cone(Vector3f position, Vector3f rotation, float scale, Texture texture) {
		super();
		
		height = 1;
		radius = 1;
		sides = 10;
		
		generateVertices();
		generateNormals();
		generateTextureCoordinates();
		
		Mesh mesh = new Mesh(cone_positions, cone_texture_coordinates, cone_normals, cone_indices);
		Material mat = new Material(texture);
		
		mesh.setMaterial(mat);
		setMesh(mesh);
		
		setPosition(position.x, position.y, position.z);
		setRotation(rotation.x, rotation.y, rotation.z);
		setScale(scale);
	}
	
	/**
	 * Generates the vertices and indices for the Cone.
	 */
	@Override
	public void generateVertices() {
		positions = new ArrayList<Vector3f>();
		ArrayList<Vector3i> indices = new ArrayList<Vector3i>();
		
		// Generate bottom
		Vector3f v0 = new Vector3f(0, 0, 0);
		positions.add(v0);
		
		float stepAngle = (float) ((2*Math.PI) / sides);
		for (int i = 0; i <= sides; i++) {
			float r = stepAngle * i;
			float x = (float) Math.cos(r) * radius;
			float z = (float) Math.sin(r) * radius;
			positions.add(new Vector3f(x, 0, z));
		}
		
		for (int i = 0; i < sides+1; i++) {
			indices.add(new Vector3i(0, i, i+1));
		}
		
		// Generate top and sides
		Vector3f v1 = new Vector3f(0, height, 0);
		positions.add(v1);
		
		int topInd = positions.indexOf(v1);
		
		for (int i = 0; i < sides+1; i++) {
			indices.add(new Vector3i(topInd, i, i+1));
		}
		
		cone_positions = new float[positions.size()*3];
		for (int i = 0, k = 0; k < positions.size(); i+=3, k++) {
			cone_positions[i] = positions.get(k).x;
			cone_positions[i + 1] = positions.get(k).y;
			cone_positions[i + 2] = positions.get(k).z;
		}
		
		cone_indices = new int[indices.size()*3];
		for (int i = 0, k = 0; k < indices.size(); i+=3, k++) {
			cone_indices[i] = indices.get(k).x;
			cone_indices[i + 1] = indices.get(k).y;
			cone_indices[i + 2] = indices.get(k).z;
		}
	}

	/**
	 * Generates the normals for the Cone.
	 */
	@Override
	public void generateNormals() {
		float[] normals = new float[positions.size()*3];
		
		// TODO: These are not calculated properly, needs refactoring later on.
		// Oskar Mendel - 2017-06-14
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
		
		cone_normals = normals;
	}

	/**
	 * Generates the uv mapping for the Cone.
	 */
	@Override
	public void generateTextureCoordinates() {
		// TODO: Switch to cube mapping?
		cone_texture_coordinates = new float[positions.size()*2];
		
		for (int i = 2, k = 1; k < positions.size(); i+=2, k++) {
			Vector3f vertex = positions.get(k);
			
			cone_texture_coordinates[i] = (float)((Math.atan2(vertex.x, vertex.z) + Math.PI) / Math.PI / 2);
			cone_texture_coordinates[i + 1] = (float)((Math.acos(vertex.y) + Math.PI) / Math.PI - 1);
		}
	}
	
	/**
	 * Generates the mesh based upon the previously generated 
	 * vertices, indices, normals and texture coordinates.
	 */
	@Override
	public void generateMesh() {
		Mesh mesh = new Mesh(cone_positions, cone_texture_coordinates, cone_normals, cone_indices);
		setMesh(mesh);
	}
	
	/**
	 * Getter for the height of this cone.
	 * 
	 * @return - The height of this cone.
	 */
	public float getHeight() {
		return height;
	}

	/**
	 * Setter for the height of this cone.
	 * 
	 * @param height - Height value to set. 
	 */
	public void setHeight(float height) {
		this.height = height;
	}

	/**
	 * Getter for the radius of this cone.
	 * 
	 * @return - The radius of this cone.
	 */
	public float getRadius() {
		return radius;
	}

	/**
	 * Setter for the radius of this cone.
	 * 
	 * @param radius - Sides value to set. 
	 */
	public void setRadius(float radius) {
		this.radius = radius;
	}

	/**
	 * Getter for the sides of this cone.
	 * 
	 * @return - The amount sides of this cone.
	 */
	public int getSides() {
		return sides;
	}

	/**
	 * Setter for the sides of this cone.
	 * 
	 * @param sides - Sides value to set.
	 */
	public void setSides(int sides) {
		this.sides = sides;
	}

	/**
	 * Setter color of this cone.
	 * 
	 * @param color - Color value to set.
	 */
	public void setColor(Color color) {
		this.color = color;
	}
	
	/**
	 * Getter of the color of this cone.
	 * 
	 * @return - The color of this cone.
	 */
	public Color getColor() {
		return this.color;
	}
	
	/**
	 * Setter of the texture of this cone.
	 * 
	 * @param texture - Texture value to set.
	 */
	public void setTexture(Texture texture) {
		this.texture = texture;
	}
	
	/**
	 * Getter of the texture of this cone.
	 * 
	 * @return - The texture of this cone.
	 */
	public Texture getTexture() {
		return this.texture;
	}
}

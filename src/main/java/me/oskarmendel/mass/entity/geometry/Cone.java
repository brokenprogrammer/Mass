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
public class Cone extends Entity {

	/**
	 * Vertex positions for the cone.
	 */
	private float[] cone_positions;
	
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
		
		generateCone();
		
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
		
		generateCone();
		
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
		
		generateCone();
		
		Mesh mesh = new Mesh(cone_positions, cone_texture_coordinates, cone_normals, cone_indices);
		Material mat = new Material(texture);
		
		mesh.setMaterial(mat);
		setMesh(mesh);
		
		setPosition(position.x, position.y, position.z);
		setRotation(rotation.x, rotation.y, rotation.z);
		setScale(scale);
	}
	
	/**
	 * Generates the vertices, indices, normals and uv mapping 
	 * for the cone.
	 */
	private void generateCone() {
		ArrayList<Vector3f> positions = new ArrayList<Vector3f>();
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
		
		// Generate normals
		float[] normals = new float[positions.size()*3];
		
		// Normals for bottom vertex
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
		
		// Generate texture coordinates through spherical uv mapping
		// TODO: Switch to cube mapping?
		cone_texture_coordinates = new float[positions.size()*2];
		
		for (int i = 2, k = 1; k < positions.size(); i+=2, k++) {
			Vector3f vertex = positions.get(k);
			
			cone_texture_coordinates[i] = (float)((Math.atan2(vertex.x, vertex.z) + Math.PI) / Math.PI / 2);
			cone_texture_coordinates[i + 1] = (float)((Math.acos(vertex.y) + Math.PI) / Math.PI - 1);
		}
	}
}

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
public class Cylinder extends Entity {
	
	/**
	 * Vertex positions for the cylinder.
	 */
	private float[] cylinder_positions;
	
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
		
		generateCylinder();
		
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
		
		generateCylinder();
		
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
		
		generateCylinder();
		
		Mesh mesh = new Mesh(cylinder_positions, cylinder_texture_coordinates, cylinder_normals, cylinder_indices);
		Material mat = new Material(texture);
		
		mesh.setMaterial(mat);
		setMesh(mesh);
		
		setPosition(position.x, position.y, position.z);
		setRotation(rotation.x, rotation.y, rotation.z);
		setScale(scale);
	}
	
	/**
	 * Generates the vertices, indices, normals and uv mapping 
	 * for the cylinder.
	 */
	private void generateCylinder() {
		ArrayList<Vector3f> positions = new ArrayList<Vector3f>();
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
		
		// Generate normals
		float[] normals = new float[positions.size()*3];
		// Normals for bottom and top vertex
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
		
		// Generate texture coordinates through spherical uv mapping
		// TODO: Switch to cube mapping?
		cylinder_texture_coordinates = new float[positions.size()*2];
		
		for (int i = 2, k = 1; k < positions.size(); i+=2, k++) {
			Vector3f vertex = positions.get(k);
			
			cylinder_texture_coordinates[i] = (float)((Math.atan2(vertex.x, vertex.z) + Math.PI) / Math.PI / 2);
			cylinder_texture_coordinates[i + 1] = (float)((Math.acos(vertex.y) + Math.PI) / Math.PI - 1);
		}
	}
}

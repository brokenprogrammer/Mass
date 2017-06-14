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
	private ArrayList<Vector3f> cone_positions;
	
	private float[] cone_texture_coordinates;
	
	private float[] cone_normals;
	
	/**
	 * Indices for the cone.
	 */
	private ArrayList<Vector3i> cone_indices;
	
	
	private float height;
	private float radius;
	private int sides;
	
	public Cone(Vector3f position, Vector3f rotation, float scale) {
		cone_positions = new ArrayList<Vector3f>();
		cone_indices = new ArrayList<Vector3i>();
		height = 1;
		radius = 1;
		sides = 10;
		
		generateCone();
		
		float[] pos = new float[cone_positions.size()*3];
		for (int i = 0, k = 0; k < cone_positions.size(); i+=3, k++) {
			pos[i] = cone_positions.get(k).x;
			pos[i + 1] = cone_positions.get(k).y;
			pos[i + 2] = cone_positions.get(k).z;
		}
		
		int[] ind = new int[cone_indices.size()*3];
		for (int i = 0, k = 0; k < cone_indices.size(); i+=3, k++) {
			ind[i] = cone_indices.get(k).x;
			ind[i + 1] = cone_indices.get(k).y;
			ind[i + 2] = cone_indices.get(k).z;
		}
		
		Mesh mesh = new Mesh(pos, cone_texture_coordinates, cone_normals, ind);
		Material mat = new Material();
		
		mesh.setMaterial(mat);
		setMesh(mesh);
		
		setPosition(position.x, position.y, position.z);
		setRotation(rotation.x, rotation.y, rotation.z);
		setScale(scale);
	}
	
	public Cone(Vector3f position, Vector3f rotation, float scale, float height) {
		
	}
	
	public Cone(Vector3f position, Vector3f rotation, float scale, Texture texture) {
		cone_positions = new ArrayList<Vector3f>();
		cone_indices = new ArrayList<Vector3i>();
		height = 1;
		radius = 1;
		sides = 10;
		
		generateCone();
		
		float[] pos = new float[cone_positions.size()*3];
		for (int i = 0, k = 0; k < cone_positions.size(); i+=3, k++) {
			pos[i] = cone_positions.get(k).x;
			pos[i + 1] = cone_positions.get(k).y;
			pos[i + 2] = cone_positions.get(k).z;
		}
		
		int[] ind = new int[cone_indices.size()*3];
		for (int i = 0, k = 0; k < cone_indices.size(); i+=3, k++) {
			ind[i] = cone_indices.get(k).x;
			ind[i + 1] = cone_indices.get(k).y;
			ind[i + 2] = cone_indices.get(k).z;
		}
		
		Mesh mesh = new Mesh(pos, cone_texture_coordinates, cone_normals, ind);
		Material mat = new Material(texture);
		
		mesh.setMaterial(mat);
		setMesh(mesh);
		
		setPosition(position.x, position.y, position.z);
		setRotation(rotation.x, rotation.y, rotation.z);
		setScale(scale);
	}
	
	/**
	 * 
	 */
	private void generateCone() {
		// Generate bottom
		Vector3f v0 = new Vector3f(0, 0, 0);
		cone_positions.add(v0);
		
		float stepAngle = (float) ((2*Math.PI) / sides);
		for (int i = 0; i <= sides; i++) {
			float r = stepAngle * i;
			float x = (float) Math.cos(r) * radius;
			float z = (float) Math.sin(r) * radius;
			cone_positions.add(new Vector3f(x, 0, z));
		}
		
		for (int i = 0; i < sides+1; i++) {
			cone_indices.add(new Vector3i(0, i, i+1));
		}
		
		// Generate top and sides
		Vector3f v1 = new Vector3f(0, height, 0);
		cone_positions.add(v1);
		
		int topInd = cone_positions.indexOf(v1);
		
		for (int i = 0; i < sides+1; i++) {
			cone_indices.add(new Vector3i(topInd, i, i+1));
		}
		
		// Generate normals
		float[] normals = new float[cone_positions.size()*3];
		
		// Normals for bottom vertex
		// TODO: These are not calculated properly, needs refactoring later on.
		// Oskar Mendel - 2017-06-14
		normals[0] = 0;
		normals[1] = -1;
		normals[2] = 0;
		
		for (int i = 3, k = 1; k < cone_positions.size(); i+=3, k++) {
			Vector3f vec = cone_positions.get(k);
			
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
		
		// Generate texture coordinates
		// TODO: Switch to cube mapping?
		cone_texture_coordinates = new float[cone_positions.size()*2];
		
		for (int i = 2, k = 1; k < cone_positions.size(); i+=2, k++) {
			Vector3f vertex = cone_positions.get(k);
			
			cone_texture_coordinates[i] = (float)((Math.atan2(vertex.x, vertex.z) + Math.PI) / Math.PI / 2);
			cone_texture_coordinates[i + 1] = (float)((Math.acos(vertex.y) + Math.PI) / Math.PI - 1);
		}
	}
}

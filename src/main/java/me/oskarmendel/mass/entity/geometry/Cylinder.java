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

/**
 * This class represents a cylinder.
 *
 * @author Oskar Mendel
 * @version 0.00.00
 * @name Cylinder.java
 */
public class Cylinder extends Entity {
	
	private ArrayList<Vector3f> cylinder_positions;
	
	private ArrayList<Vector3i> cylinder_indices;
	
	private float height;
	private float radius;
	private int sides;
	
	public Cylinder(Vector3f position, Vector3f rotation, float scale) {
		cylinder_positions = new ArrayList<Vector3f>();
		cylinder_indices = new ArrayList<Vector3i>();
		height = 50;
		radius = 1;
		sides = 10;
		
		generateCylinder();
		
		float[] pos = new float[cylinder_positions.size()*3];
		for (int i = 0, k = 0; k < cylinder_positions.size(); i+=3, k++) {
			pos[i] = cylinder_positions.get(k).x;
			pos[i + 1] = cylinder_positions.get(k).y;
			pos[i + 2] = cylinder_positions.get(k).z;
		}
		
		int[] ind = new int[cylinder_indices.size()*3];
		for (int i = 0, k = 0; k < cylinder_indices.size(); i+=3, k++) {
			ind[i] = cylinder_indices.get(k).x;
			ind[i + 1] = cylinder_indices.get(k).y;
			ind[i + 2] = cylinder_indices.get(k).z;
		}
		
		float[] t = {1};
		Mesh mesh = new Mesh(pos, t, t, ind);
		Material mat = new Material();
		
		mesh.setMaterial(mat);
		setMesh(mesh);
		
		setPosition(position.x, position.y, position.z);
		setRotation(rotation.x, rotation.y, rotation.z);
		setScale(scale);
	}
	
	private void generateCylinder() {
		// Generate bottom
		Vector3f v0 = new Vector3f(0, 0, 0);
		Vector3f v1 = new Vector3f(0, height, 0);
		cylinder_positions.add(v0);
		cylinder_positions.add(v1);
		
		float stepAngle = (float) ((2*Math.PI) / sides);
		for (int i = 0; i <= sides; i++) {
			float r = stepAngle * i;
			float x = (float) Math.cos(r) * radius;
			float z = (float) Math.sin(r) * radius;
			cylinder_positions.add(new Vector3f(x, 0, z));
			cylinder_positions.add(new Vector3f(x, height, z));
		}
		
		
		for (int i = 0; i < (sides)*2; i+=2) {
			cylinder_indices.add(new Vector3i(0, i+2, i+4));
		}
		
		for (int i = 1; i < (sides)*2; i+=2) {
			cylinder_indices.add(new Vector3i(1, i+2, i+4));
		}
		
		// Generating sides
		for (int i = 2; i < (sides+1)*2; i++) {
			cylinder_indices.add(new Vector3i(i, i+1, i+2));
		}
	}
}

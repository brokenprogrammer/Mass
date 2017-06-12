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

import org.joml.Vector3f;

import me.oskarmendel.mass.entity.Entity;
import me.oskarmendel.mass.gfx.Material;
import me.oskarmendel.mass.gfx.Mesh;

/**
 * This class represents a sphere.
 * The sphere type used is icosphere.
 *
 * @author Oskar Mendel
 * @version 0.00.00
 * @name Sphere.java
 */
public class Sphere extends Entity{
	
	float t = (float) (1.0f + Math.sqrt(5))/2;
	private float[] sphere_positions;
	private float[] sphere_texture_coordinates = {1};
	private float[] sphere_normals = {1};
	private int[] sphere_indices;
	
	public Sphere(Vector3f position, Vector3f rotation, float radius) {
		sphere_positions = new float[12*3];
		generateIcoSphere();
		
		Mesh mesh = new Mesh(sphere_positions, sphere_texture_coordinates, sphere_normals, sphere_indices);
		Material mat = new Material();
		
		mesh.setMaterial(mat);
		setMesh(mesh);
		
		setPosition(position.x, position.y, position.z);
        setRotation(rotation.x, rotation.y, rotation.z);
        setScale(1);
	}
	
	private void generateIcoSphere() {
		// The golden ratio.
		float t = (float) (1.0f + Math.sqrt(5))/2;
		
		for (int i = 0; i < 12; i+=3) {
			sphere_positions[i] = 0;
			sphere_positions[i + 1] = (i&2) == 2?-1:1;
			sphere_positions[i + 2] = (i&1) == 1?-t:t;
		}
		
		for (int i = 12; i < 24; i+=3) {
			sphere_positions[i] = (i&2) == 2?-1:1;
			sphere_positions[i + 1] = (i&1) == 1?-t:t;
			sphere_positions[i + 2] = 0;
		}
		
		for (int i = 24; i < 36; i+=3) {
			sphere_positions[i] = (i&1) == 1?-t:t;
			sphere_positions[i + 1] = 0;
			sphere_positions[i + 2] = (i&2) == 2?-1:1;
		}
		
		int[] indices = {
				6, 11, 0,
				6, 0, 4,
				6, 4, 3,
				6, 3, 9,
				6, 9, 11,
				
				4, 0, 8,
				0, 11, 2,
				11, 9, 5,
				9, 3, 1,
				3, 4, 10,
				
				7, 8, 2,
				7, 2, 5,
				7, 5, 1,
				7, 1, 10,
				7, 10, 8,
				
				2, 8, 0,
				5, 2, 11,
				1, 5, 9,
				10, 1, 3, 
				8, 10, 4
		};
		
		sphere_indices = indices;
		
		//TODO: Texture coords, normals.
	}
	
	private void refine(int iterations) {
		
	}
}

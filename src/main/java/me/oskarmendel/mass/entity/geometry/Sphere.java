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

/**
 * This class represents a sphere.
 * The sphere type used is icosphere.
 *
 * @author Oskar Mendel
 * @version 0.00.00
 * @name Sphere.java
 */
public class Sphere {
	
	
	private float[] sphere_positions;
	private float[] sphere_texture_coordinates;
	private float[] sphere_normals;
	private int[] sphere_indices;
	
	public Sphere(Vector3f position, Vector3f rotation, float radius) {
		// How many vertices etc.. Then generate
		sphere_positions = new float[12];
	}
	
	private void generateIcoSphere() {
		// The golden ratio.
		double t = (1.0f + Math.sqrt(5))/2;
	}
	
	private void refine(int iterations) {
		
	}
}

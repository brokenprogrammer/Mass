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
import java.util.HashMap;
import java.util.Map;

import org.joml.Vector3f;
import org.joml.Vector3i;

import me.oskarmendel.mass.entity.Entity;
import me.oskarmendel.mass.gfx.Color;
import me.oskarmendel.mass.gfx.Material;
import me.oskarmendel.mass.gfx.Mesh;
import me.oskarmendel.mass.gfx.Texture;

/**
 * This class represents a sphere.
 * The sphere type used is icosphere.
 *
 * @author Oskar Mendel
 * @version 0.00.00
 * @name Sphere.java
 */
public class Sphere extends Entity implements Geometry {
	
	private static final float t = (float) (1.0f + Math.sqrt(5))/2;
	
	/**
	 * Vertex positions for the sphere.
	 */
	private float[] sphere_positions;
	
	/**
	 * Texture coordinates of the sphere.
	 */
	private float[] sphere_texture_coordinates;
	
	/**
	 * Normals for the sphere.
	 */
	private float[] sphere_normals;
	
	/**
	 * Indices for the sphere.
	 */
	private int[] sphere_indices;
	
	/**
	 * Creates a new sphere at the specified position
	 * using the specified rotation and scale. Then 
	 * subdivides the sphere with the specified amount of iterations.
	 * 
	 * @param position - Position of the sphere.
	 * @param rotation - Rotation of the sphere.
	 * @param scale - Scale of the sphere.
	 * @param iterations - Number of subdivisions.
	 */
	public Sphere(Vector3f position, Vector3f rotation, float scale, int iterations) {
		super();
		
		generateVertices();
		subdivide(iterations);
		generateNormals();
		generateTextureCoordinates();
		
		Mesh mesh = new Mesh(sphere_positions, sphere_texture_coordinates, sphere_normals, sphere_indices);
		Material mat = new Material();
		
		mesh.setMaterial(mat);
		setMesh(mesh);
		
		setPosition(position.x, position.y, position.z);
        setRotation(rotation.x, rotation.y, rotation.z);
        setScale(scale);
	}
	
	/**
	 * Creates a new sphere at the specified position
	 * using the specified rotation, scale and color. 
	 * Then subdivides the sphere with the specified amount 
	 * of iterations.
	 * 
	 * @param position - Position of the sphere.
	 * @param rotation - Rotation of the sphere.
	 * @param scale - Scale of the sphere.
	 * @param iterations - Number of subdivisions.
	 * @param color - Color of the sphere.
	 */
	public Sphere(Vector3f position, Vector3f rotation, float scale, int iterations, Color color) {
		super();
		
		generateVertices();
		subdivide(iterations);
		generateNormals();
		generateTextureCoordinates();
		
		Mesh mesh = new Mesh(sphere_positions, sphere_texture_coordinates, sphere_normals, sphere_indices);
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
	 * Creates a new sphere at the specified position
	 * using the specified rotation, scale and texture. 
	 * Then subdivides the sphere with the specified amount 
	 * of iterations.
	 * 
	 * @param position - Position of the sphere.
	 * @param rotation - Rotation of the sphere.
	 * @param scale - Scale of the sphere.
	 * @param iterations - Number of subdivisions.
	 * @param texture - Texture of the sphere.
	 */
	public Sphere(Vector3f position, Vector3f rotation, float scale, int iterations, Texture texture) {
		super();
		
		generateVertices();
		subdivide(iterations);
		generateNormals();
		generateTextureCoordinates();
		
		Mesh mesh = new Mesh(sphere_positions, sphere_texture_coordinates, sphere_normals, sphere_indices);
		Material mat = new Material(texture);
		
		mesh.setMaterial(mat);
		setMesh(mesh);
		
		setPosition(position.x, position.y, position.z);
        setRotation(rotation.x, rotation.y, rotation.z);
        setScale(scale);
	}
	
	/**
	 * Generates an initial icosphere vertex positions and
	 * indices.
	 */
	@Override
	public void generateVertices() {
		sphere_positions = new float[12*3];
		
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
	}
	
	/**
	 * Generates the normals for the sphere.
	 */
	@Override
	public void generateNormals() {
		float[] normals = new float[sphere_positions.length];
		for(int i = 0; i < normals.length; i+=3) {
			float x = sphere_positions[i];
			float y = sphere_positions[i+1];
			float z = sphere_positions[i+2];
			float dt = (float) Math.sqrt((x*x)+(y*y)+(z*z));
			
			x = x * (1.0f / dt);
			y = y * (1.0f / dt);
			z = z * (1.0f / dt);
			
			normals[i] = x;
			normals[i+1] = y;
			normals[i+2] = z;
		}
		
		sphere_normals = normals;
	}
	
	/**
	 * Generates the texture coordinates for the sphere.
	 * TODO: Switch to using cube mapping:
	 * https://www.shaneenishry.com/blog/2014/08/01/planet-generation-part-i/
	 */
	@Override
	public void generateTextureCoordinates() {
		sphere_texture_coordinates = new float[(sphere_positions.length/3)*2];
		
		ArrayList<Vector3f> vertices = new ArrayList<>();
		for (int i = 0; i < sphere_positions.length; i+=3) {
			vertices.add(new Vector3f(sphere_positions[i], sphere_positions[i + 1], sphere_positions[i + 2]));
		}
		
		for (int i = 0, k = 0; k < vertices.size(); i+=2, k++) {
			Vector3f vertex = vertices.get(k);
			
			sphere_texture_coordinates[i] = (float)((Math.atan2(vertex.x, vertex.z) + Math.PI) / Math.PI / 2);
			sphere_texture_coordinates[i + 1] = (float)((Math.acos(vertex.y) + Math.PI) / Math.PI - 1);
		}
	}
	
	/**
	 * Subdivides each of the icospheres triangles into 4 
	 * smaller triangles.
	 * 
	 * @param iterations - Number of subdivisions.
	 */
	private void subdivide(int iterations) {
		ArrayList<Vector3f> vertices = new ArrayList<>();
		Map<Long, Integer> middlePointIndexCache = new HashMap<Long, Integer>();
		
		for (int i = 0; i < sphere_positions.length; i+=3) {
			vertices.add(new Vector3f(sphere_positions[i], sphere_positions[i + 1], sphere_positions[i + 2]));
		}
		for (Vector3f vec : vertices) {
			vec.normalize();
		}
		
		for (int i = 0; i < iterations; i++) {
			ArrayList<Vector3i> indices = new ArrayList<>();
			for (int j = 0; j < sphere_indices.length; j+=3) {
				indices.add(new Vector3i(sphere_indices[j], sphere_indices[j + 1], sphere_indices[j + 2]));
			}
			
			ArrayList<Vector3i> indices2 = new ArrayList<>();
			for(Vector3i tri : indices) {
				int a = midPoint(tri.x, tri.y, vertices, middlePointIndexCache);
				int b = midPoint(tri.y, tri.z, vertices, middlePointIndexCache);
				int c = midPoint(tri.z, tri.x, vertices, middlePointIndexCache);
				
				Vector3i x0 = new Vector3i(tri.x, a, c);
				Vector3i x1 = new Vector3i(tri.y, b, a);
				Vector3i x2 = new Vector3i(tri.z, c, b);
				Vector3i x3 = new Vector3i(a, b, c);
				
				indices2.add(x0);
				indices2.add(x1);
				indices2.add(x2);
				indices2.add(x3);
			}
			
			sphere_indices = new int[indices2.size()*3];
			for (int j = 0, k = 0; j < sphere_indices.length; j+=3, k++) {
				sphere_indices[j] = indices2.get(k).x;
				sphere_indices[j + 1] = indices2.get(k).y;
				sphere_indices[j + 2] = indices2.get(k).z;
			}
			
			sphere_positions = new float[vertices.size()*3];
			for (int j = 0, k = 0; j < sphere_positions.length; j+=3, k++) {
				sphere_positions[j] = vertices.get(k).x;
				sphere_positions[j + 1] = vertices.get(k).y;
				sphere_positions[j + 2] = vertices.get(k).z;
			}
		}
	}
	
	/**
	 * Finds the midpoint between two vertices and returns 
	 * the index of the midpoint.
	 * 
	 * @param p1 - First vertex index.
	 * @param p2 - Second vertex index.
	 * @param vertices - List of vertices.
	 * @param cache - Cache of previously added midpoints.
	 * 
	 * @return The index of the vertex in the middle of p1 and p2.
	 */
	private int midPoint(int p1, int p2, ArrayList<Vector3f> vertices, Map<Long, Integer> cache) {
		// first check if we have it already
		boolean firstIsSmaller = p1 < p2;
		long smallerIndex = firstIsSmaller ? p1 : p2;
		long greaterIndex = firstIsSmaller ? p2 : p1;
		long key = (smallerIndex << 32) + greaterIndex;
 
		if (cache.containsKey(key)) {
			return cache.get(key);
		}
 
		// not in cache, calculate it
		Vector3f point1 = vertices.get(p1);
		Vector3f point2 = vertices.get(p2);
		Vector3f middle = new Vector3f
		(
			(point1.x + point2.x) / 2f,
			(point1.y + point2.y) / 2f,
			(point1.z + point2.z) / 2f
		);
 
		// add vertex makes sure point is on unit sphere
		int i = vertices.size();
		vertices.add(middle.normalize());
 
		// store it, return index
		cache.put(key, i);
 
		return i;
	}
}

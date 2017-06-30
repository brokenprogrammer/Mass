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

package me.oskarmendel.mass.gfx.filter;

import java.util.List;
import java.util.Map;

import org.joml.FrustumIntersection;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import me.oskarmendel.mass.entity.Entity;
import me.oskarmendel.mass.gfx.Mesh;

/**
 * This class helps improve performance through filtering
 * entities that shouldn't be rendered so that the game wont attempt
 * to render entities which shouldn't.
 *
 * @author Oskar Mendel
 * @version 0.00.00
 * @name FrustrumCullingFilter.java
 */
public class FrustumCullingFilter {
	
	private final Matrix4f projectionViewMatrix;
	
	private final FrustumIntersection frustumInt;
	
	/**
	 * Default constructor for a new FrustumCullingFilter which
	 * initializes the fields of this class.
	 */
	public FrustumCullingFilter() {
		projectionViewMatrix = new Matrix4f();
		frustumInt = new FrustumIntersection();
	}
	
	/**
	 * Updates the frustum filter before rendering.
	 * 
	 * @param projMatrix - Projection matrix.
	 * @param viewMatrix - View matrix.
	 */
	public void updateFrustum(Matrix4f projMatrix, Matrix4f viewMatrix) {
		this.projectionViewMatrix.set(projMatrix);
		this.projectionViewMatrix.mul(viewMatrix);
		
		this.frustumInt.set(this.projectionViewMatrix);
	}
	
	/**
	 * Filters the entities and their meshes to check if they are within
	 * the view frustum or not.
	 * 
	 * @param mapMesh - Map of meshes and connected entities to check.
	 */
	public void filter(Map<? extends Mesh, List<Entity>> mapMesh) {
		for (Map.Entry<? extends Mesh, List<Entity>> e : mapMesh.entrySet()) {
			List<Entity> entities = e.getValue();
			filter(entities, e.getKey().getBoundingRadius());
		}
	}
	
	/**
	 * Filters the entities which is outside the view frustum.
	 * 
	 * @param entities - List of entities to check.
	 * @param meshBoundingRadius - Bounding radius of the shared mesh.
	 */
	public void filter(List<Entity> entities, float meshBoundingRadius) {
		float boundingRadius;
		Vector3f position;
		
		for (Entity e : entities) {
			boundingRadius = e.getScale() * meshBoundingRadius;
			position = e.getPosition();
			e.setInsideFrtustrum(insideFrustum(position.x, position.y, position.z, boundingRadius));
		}
	}
	
	/**
	 * Checks if a sphere is inside the frustum or not.
	 * 
	 * @param x - X coordinate.
	 * @param y - Y coordinate.
	 * @param z - Z coordinate.
	 * @param boundingRadius - Bounding Radius.
	 * 
	 * @return True if the sphere is inside the frustum; False otherwise.
	 */
	public boolean insideFrustum(float x, float y, float z, float boundingRadius) {
		return frustumInt.testSphere(x, y, z, boundingRadius);
	}
}

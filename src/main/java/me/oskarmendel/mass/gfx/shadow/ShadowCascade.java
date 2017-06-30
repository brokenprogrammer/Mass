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

package me.oskarmendel.mass.gfx.shadow;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import me.oskarmendel.mass.gfx.Screen;
import me.oskarmendel.mass.gfx.light.DirectionalLight;

/**
 * 
 * 
 * @author Oskar Mendel
 * @version 0.00.00
 * @name ShadowCascade.java
 */
public class ShadowCascade {

	private static final int FRUSTUM_CORNERS = 8;
	
	private final Matrix4f projectionViewMatrix;
	
	private final Matrix4f orthoProjectionMatrix;
	
	private final Matrix4f lightViewMatrix;
	
	private final Vector3f centr;
	
	private final Vector3f[] frustumCorners;
	
	private final float zNear;
	
	private final float zFar;
	
	private final Vector4f tmpVec;
	
	/**
	 * 
	 * @param zNear
	 * @param zFar
	 */
	public ShadowCascade(float zNear, float zFar) {
		this.zNear = zNear;
		this.zFar = zFar;
		this.projectionViewMatrix = new Matrix4f();
		this.orthoProjectionMatrix = new Matrix4f();
		this.lightViewMatrix = new Matrix4f();
		this.centr = new Vector3f();
		this.frustumCorners = new Vector3f[FRUSTUM_CORNERS];
		
		for (int i = 0; i < FRUSTUM_CORNERS; i++) {
			frustumCorners[i] = new Vector3f();
		}
		
		this.tmpVec = new Vector4f();
	}
	
	/**
	 * 
	 * @param screen
	 * @param viewMatrix
	 * @param light
	 */
	public void update(Screen screen, Matrix4f viewMatrix, DirectionalLight light) {
		
	}
	
	/**
	 * 
	 * @param lightDirection
	 * @param lightPosition
	 */
	private void updateLightViewMatrix(Vector3f lightDirection, Vector3f lightPosition) {
		
	}
	
	/**
	 * 
	 */
	private void updateLightProjectionMatrix() {
		
	}
	
	/**
	 * 
	 * @return
	 */
	public Matrix4f getOrthoProjectionMatrix() {
		return this.orthoProjectionMatrix;
	}
	
	/**
	 * 
	 * @return
	 */
	public Matrix4f getLightViewMatrix() {
		return this.lightViewMatrix;
	}
}

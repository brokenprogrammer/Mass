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
import me.oskarmendel.mass.gfx.Transformation;
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
		float aspectRatio = (float) screen.getWidth() / (float) screen.getHeight();
		// Build view matrix for this shadow cascade.
		this.projectionViewMatrix.setPerspective(Screen.FOV, aspectRatio, zNear, zFar);
		this.projectionViewMatrix.mul(viewMatrix);
		
		// Calculate frustum corners in space
		float maxZ = Float.MIN_VALUE;
		float minZ = Float.MAX_VALUE;
		
		for (int i = 0; i < FRUSTUM_CORNERS; i++) {
			Vector3f corner = this.frustumCorners[i];
			corner.set(0, 0, 0);
			
			this.projectionViewMatrix.frustumCorner(i, corner);
			this.centr.add(corner);
			this.centr.div(8.0f);
			
			minZ = Math.min(minZ, corner.z);
			maxZ = Math.max(maxZ, corner.z);
		}
		
		Vector3f lightDir = light.getDirection();
		Vector3f lightPosInc = new Vector3f().set(lightDir);
		float distance = maxZ - minZ;
		
		lightPosInc.mul(distance);
		Vector3f lightPos = new Vector3f();
        lightPos.set(this.centr);
        lightPos.add(lightPosInc);
        
        updateLightViewMatrix(lightDir, lightPos);
        updateLightProjectionMatrix();
	}
	
	/**
	 * 
	 * @param lightDirection
	 * @param lightPosition
	 */
	private void updateLightViewMatrix(Vector3f lightDirection, Vector3f lightPosition) {
		float lightAngleX = (float) Math.toDegrees(Math.acos(lightDirection.z));
        float lightAngleY = (float) Math.toDegrees(Math.asin(lightDirection.x));
        float lightAngleZ = 0;
        Transformation.updateGenericViewMatrix(lightPosition, new Vector3f(lightAngleX, lightAngleY, lightAngleZ), lightViewMatrix);
	}
	
	/**
	 * 
	 */
	private void updateLightProjectionMatrix() {
		float minX =  Float.MAX_VALUE;
        float maxX = -Float.MIN_VALUE;
        float minY =  Float.MAX_VALUE;
        float maxY = -Float.MIN_VALUE;
        float minZ =  Float.MAX_VALUE;
        float maxZ = -Float.MIN_VALUE;
        
        for (int i = 0; i < FRUSTUM_CORNERS; i++) {
            Vector3f corner = frustumCorners[i];
            tmpVec.set(corner, 1);
            tmpVec.mul(lightViewMatrix);
            minX = Math.min(tmpVec.x, minX);
            maxX = Math.max(tmpVec.x, maxX);
            minY = Math.min(tmpVec.y, minY);
            maxY = Math.max(tmpVec.y, maxY);
            minZ = Math.min(tmpVec.z, minZ);
            maxZ = Math.max(tmpVec.z, maxZ);
        }
        
        float distz = maxZ - minZ;
        this.orthoProjectionMatrix.setOrtho(minX, maxX, minY, maxY, 0, distz);
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

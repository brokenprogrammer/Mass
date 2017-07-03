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

package me.oskarmendel.mass.gfx;

import me.oskarmendel.mass.entity.Entity;

import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

/**
 * Class to perform transformations like position
 * translation, rotation and scaling.
 *
 * @author Oskar Mendel
 * @version 0.00.00
 * @name Transformation.java
 */
public class Transformation {

	private final Matrix4f modelMatrix;

    private final Matrix4f projectionMatrix;

    private final Matrix4f modelViewMatrix;
    

    /**
     *
     */
    public Transformation() {
    	this.modelMatrix = new Matrix4f();
        this.projectionMatrix = new Matrix4f();
        this.modelViewMatrix = new Matrix4f();
    }

    public static Matrix4f updateGenericViewMatrix(Vector3f position, Vector3f rotation, Matrix4f matrix) {
        return matrix.rotationX((float)Math.toRadians(rotation.x))
                .rotateY((float)Math.toRadians(rotation.y))
                .translate(-position.x, -position.y, -position.z);
    }

    public final Matrix4f getProjectionMatrix(float fov, float width, float height, float zNear, float zFar) {
        float aspectRatio = width / height;

        projectionMatrix.identity();
        projectionMatrix.perspective(fov, aspectRatio, zNear, zFar);

        return projectionMatrix;
    }
    
    public Matrix4f buildModelViewMatrix(Entity entity, Matrix4f viewMatrix) {
    	return buildModelViewMatrix(buildModelMatrix(entity), viewMatrix);
    }
    
    public Matrix4f buildModelViewMatrix(Matrix4f modelMatrix, Matrix4f viewMatrix) {
    	return viewMatrix.mulAffine(modelMatrix, this.modelViewMatrix);
    }
    
    public Matrix4f buildModelMatrix(Entity entity) {
    	Quaternionf rotation = entity.getRotation();
    	Vector3f position = entity.getPosition();
    	
    	return modelMatrix.translationRotateScale(position.x, position.y, position.z, 
    			rotation.x, rotation.y, rotation.z, rotation.w, 
    			entity.getScale(), entity.getScale(), entity.getScale());
    }
}

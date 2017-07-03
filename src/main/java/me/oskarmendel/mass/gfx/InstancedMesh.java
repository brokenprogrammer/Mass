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

import java.nio.FloatBuffer;
import java.util.List;

import org.joml.Matrix4f;
import org.lwjgl.system.MemoryUtil;

import me.oskarmendel.mass.entity.Entity;
import me.oskarmendel.mass.util.ArrayHelper;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_DYNAMIC_READ;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL33.glVertexAttribDivisor;
import static org.lwjgl.opengl.GL31.glDrawElementsInstanced;

/**
 * This class represents a Instanced Mesh.
 *
 * @author Oskar Mendel
 * @version 0.00.00
 * @name InstancedMesh.java
 */
public class InstancedMesh extends Mesh {

	private static int FLOAT_SIZE_BYTES = 4;
	
	private static int VECTOR4F_SIZE_BYTES = 4 * FLOAT_SIZE_BYTES;
	
	private static final int MATRIX_SIZE_FLOATS = 4 * 4;
	
	private static final int MATRIX_SIZE_BYTES = MATRIX_SIZE_FLOATS * FLOAT_SIZE_BYTES;
	
	private static final int INSTANCE_SIZE_FLOATS = MATRIX_SIZE_FLOATS + 3;
	
	private static final int INSTANCE_SIZE_BYTES = MATRIX_SIZE_BYTES + FLOAT_SIZE_BYTES * 2 + FLOAT_SIZE_BYTES;
	
	private final int instances;
	
	private final int instancedDataVbo;
	
	private FloatBuffer instancedDataBuffer;
	
	/**
	 * 
	 * @param positions
	 * @param textCoords
	 * @param normals
	 * @param indices
	 * @param instances
	 */
	public InstancedMesh(float[] positions, float[] textCoords, float[] normals, int[] indices, int instances) {
		super(positions, textCoords, normals, indices,  ArrayHelper.createEmptyArrayInt(MAX_WEIGHTS * positions.length / 3, 0), ArrayHelper.createEmptyArrayFloat(MAX_WEIGHTS * positions.length / 3, 0) );
		
		
		this.instances = instances;
		
		instancedDataVbo = glGenBuffers();
		vboIdList.add(instancedDataVbo);
		
		instancedDataBuffer = MemoryUtil.memAllocFloat(instances * INSTANCE_SIZE_FLOATS);
		glBindBuffer(GL_ARRAY_BUFFER, instancedDataVbo);
		
		int start = 5;
		int strideStart = 0;
		
		// Model matrix
		for (int i = 0; i < 4; i++) {
			glVertexAttribPointer(start, 4, GL_FLOAT, false, INSTANCE_SIZE_BYTES, strideStart);
			glVertexAttribDivisor(start, 1);
			
			start++;
			strideStart += VECTOR4F_SIZE_BYTES;
		}
		
		// Texture offsets
		glVertexAttribPointer(start, 2, GL_FLOAT, false, INSTANCE_SIZE_BYTES, strideStart);
		glVertexAttribDivisor(start, 1);
		
		start++;
		strideStart += FLOAT_SIZE_BYTES * 2;
		
		// Selected
		glVertexAttribPointer(start, 1, GL_FLOAT, false, INSTANCE_SIZE_BYTES, strideStart);
		glVertexAttribDivisor(start, 1);
		
		start++;
		
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		glBindVertexArray(0);
	}
	
	/**
	 * 
	 */
	@Override
	protected void initRenderer() {
		super.initRenderer();
		
		int start = 5;
		int elems = 4 * 2 + 2;
		
		for (int i = 0; i < elems; i++) {
			glEnableVertexAttribArray(start + i);
		}
	}
	
	/**
	 * 
	 */
	@Override
	protected void endRenderer() {
		int start = 5;
		int elems = 4 * 2 + 2;
		
		for (int i = 0; i < elems; i++) {
			glDisableVertexAttribArray(start + i);
		}
		
		super.endRenderer();
	}
	
	/**
	 * 
	 * @param entities
	 * @param transformation
	 * @param viewMatrix
	 */
	public void renderListInstanced(List<Entity> entities, Transformation transformation, Matrix4f viewMatrix) {
		renderListInstanced(entities, false, transformation, viewMatrix);
	}
	
	/**
	 * 
	 * @param entities
	 * @param bbrd
	 * @param transformation
	 * @param viewMatrix
	 */
	public void renderListInstanced(List<Entity> entities, boolean bbrd, Transformation transformation, 
			Matrix4f viewMatrix) {
		initRenderer();
		
		int chunkSize = this.instances;
		int len = entities.size();
		
		for (int i = 0; i < len; i+= chunkSize) {
			int end = Math.min(len,  i+chunkSize);
			List<Entity> sub = entities.subList(i,  end);
			
			renderChunkInstanced(sub, bbrd, transformation, viewMatrix);
		}
	}
	
	/**
	 * 
	 * @param entities
	 * @param depthMap
	 * @param transformation
	 * @param viewMatrix
	 * @param lightViewMatrix
	 */
	private void renderChunkInstanced(List<Entity> entities, boolean bbrd, Transformation transformation, 
			Matrix4f viewMatrix) {
		this.instancedDataBuffer.clear();
		
		int i = 0;
		
		Texture texture = getMaterial().getTexture();
		
		for (Entity entity : entities) {
			Matrix4f modelMatrix = transformation.buildModelMatrix(entity);
			
			if (viewMatrix != null && bbrd) {
				viewMatrix.transpose3x3(modelMatrix);
			}
			
			modelMatrix.get(INSTANCE_SIZE_FLOATS * i, this.instancedDataBuffer);
			
			if (texture != null) {
				int col = entity.getTexturePos() % texture.getNumCols();
				int row = entity.getTexturePos() / texture.getNumRows();
				float textureXOffset = (float) col / texture.getNumCols();
				float textureYOffset = (float) row / texture.getNumRows();
				
				int bufferPosition = INSTANCE_SIZE_FLOATS * i + MATRIX_SIZE_FLOATS;
				
				this.instancedDataBuffer.put(bufferPosition, textureXOffset);
				this.instancedDataBuffer.put(bufferPosition + 1, textureYOffset);
			}
			
			int bufferPosition = INSTANCE_SIZE_FLOATS * i + MATRIX_SIZE_FLOATS + 2;
			this.instancedDataBuffer.put(bufferPosition, entity.isSelected() ? 1 : 0);
		}
		
		glBindBuffer(GL_ARRAY_BUFFER, this.instancedDataVbo);
		glBufferData(GL_ARRAY_BUFFER, this.instancedDataBuffer, GL_DYNAMIC_READ);
		
		glDrawElementsInstanced(GL_TRIANGLES, getVertexCount(), GL_UNSIGNED_INT, 0, entities.size());
		
		glBindBuffer(GL_ARRAY_BUFFER, 0);
	}
	
	/**
	 * 
	 */
	@Override
	public void delete() {
		super.delete();
		
		if (this.instancedDataBuffer != null) {
			MemoryUtil.memFree(this.instancedDataBuffer);
			this.instancedDataBuffer = null;
		}
	}
}

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

import org.lwjgl.system.MemoryUtil;

import me.oskarmendel.mass.entity.Entity;
import me.oskarmendel.mass.util.ArrayHelper;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.GL_TEXTURE1;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.*;

/**
 * This class represents a Mesh.
 * A mesh is a representation of a single drawable entity.
 *
 * @author Oskar Mendel
 * @version 0.00.00
 * @name Mesh.java
 */
public class Mesh {

	/**
	 * 
	 */
    private static final Color DEFAULT_COLOR = Color.WHITE;
    
    /**
     * 
     */
    public static final int MAX_WEIGHTS = 4;
    
    /**
     * Vertice positions of this mesh.
     */
    private float[] positions;
    
    /**
     * Indices of this mesh.
     */
    private int[] indices;
    
    /**
     * The vertex array object for this mesh.
     */
    protected final int vaoId;

    /**
     * 
     */
    protected final List<Integer> vboIdList;
    
    /**
     * Amount of vertices in this Mesh.
     */
    private final int vertexCount;

    /**
     * Material value for this Mesh.
     */
    private Material material;

    /**
     * Color value for this Mesh.
     */
    private Color color;
    
    /**
     * Bounding radius of this Mesh.
     */
    private float boundingRadius;

    /**
     * Creates a new Mesh object by using the specified vertex positions, texture coordinates, normal coordinates 
     * and indices. Sets the jointIndices and weights to empty arrays.
     * 
     * @param positions - Vertex positions.
     * @param textCoords - Texture coordinates.
     * @param normals - Normal coordinates.
     * @param indices - Indices.
     */
    public Mesh(float[] positions, float[] textCoords, float[] normals, int[] indices) {
        this(positions, textCoords, normals, indices, ArrayHelper.createEmptyArrayInt(MAX_WEIGHTS * positions.length / 3, 0), ArrayHelper.createEmptyArrayFloat(MAX_WEIGHTS * positions.length / 3, 0));
    }
    
    /**
     * Creates a new Mesh object by using the specified vertex positions, texture coordinates, normal coordinates 
     * indices, jointIndices and weights.
     * 
     * @param positions - Vertex positions.
     * @param textCoords - Texture coordinates.
     * @param normals - Normal coordinates.
     * @param indices - Indices.
     * @param jointIndices - Joint indices.
     * @param weights - Weights.
     */
    public Mesh(float[] positions, float[] textCoords, float[] normals, int[] indices, int[] jointIndices, float[] weights) {
        this.positions = positions;
    	this.indices = indices;
    	
    	// Store array of floats in the buffer to interface correctly with C Library.
        FloatBuffer positionBuffer = null;
        FloatBuffer textCoordsBuffer = null;
        FloatBuffer normalsBuffer = null;
        FloatBuffer weightsBuffer = null;
        IntBuffer jointIndicesBuffer = null;
        IntBuffer indicesBuffer = null;
        try {
        	calculateBoundingRadius(positions);
        	
            color = DEFAULT_COLOR;
            vertexCount = indices.length;
            vboIdList = new ArrayList<>();

            // Create the VAO and bind it.
            vaoId = glGenVertexArrays();
            glBindVertexArray(vaoId);

            // Position VBO
            int vboId = glGenBuffers();
            vboIdList.add(vboId);
            positionBuffer = MemoryUtil.memAllocFloat(positions.length);
            positionBuffer.put(positions).flip();
            glBindBuffer(GL_ARRAY_BUFFER, vboId);
            glBufferData(GL_ARRAY_BUFFER, positionBuffer, GL_STATIC_DRAW);
            // Define structure of data and store it in VAO attribute list.
            glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);

            // Texture coordinates VBO
            vboId = glGenBuffers();
            vboIdList.add(vboId);
            textCoordsBuffer = MemoryUtil.memAllocFloat(textCoords.length);
            textCoordsBuffer.put(textCoords).flip();
            glBindBuffer(GL_ARRAY_BUFFER, vboId);
            glBufferData(GL_ARRAY_BUFFER, textCoordsBuffer, GL_STATIC_DRAW);
            glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);

            // Vertex normals VBO
            vboId = glGenBuffers();
            vboIdList.add(vboId);
            normalsBuffer = MemoryUtil.memAllocFloat(normals.length);
            normalsBuffer.put(normals).flip();
            glBindBuffer(GL_ARRAY_BUFFER, vboId);
            glBufferData(GL_ARRAY_BUFFER, normalsBuffer, GL_STATIC_DRAW);
            glVertexAttribPointer(2, 3, GL_FLOAT, false, 0, 0);

            // Weights VBO
            vboId = glGenBuffers();
            vboIdList.add(vboId);
            weightsBuffer = MemoryUtil.memAllocFloat(weights.length);
            weightsBuffer.put(weights).flip();
            glBindBuffer(GL_ARRAY_BUFFER, vboId);
            glBufferData(GL_ARRAY_BUFFER, weightsBuffer, GL_STATIC_DRAW);
            glVertexAttribPointer(3, 4, GL_FLOAT, false, 0, 0);
            
            // Joint indices VBO
            vboId = glGenBuffers();
            vboIdList.add(vboId);
            jointIndicesBuffer = MemoryUtil.memAllocInt(jointIndices.length);
            jointIndicesBuffer.put(jointIndices).flip();
            glBindBuffer(GL_ARRAY_BUFFER, vboId);
            glBufferData(GL_ARRAY_BUFFER, jointIndicesBuffer, GL_STATIC_DRAW);
            glVertexAttribPointer(4, 4, GL_FLOAT, false, 0, 0);
            
            // Index VBO
            vboId = glGenBuffers();
            vboIdList.add(vboId);
            indicesBuffer = MemoryUtil.memAllocInt(indices.length);
            indicesBuffer.put(indices).flip();
            glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, vboId);
            glBufferData(GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL_STATIC_DRAW);

            // Unbinds the VBO and VAO.
            glBindBuffer(GL_ARRAY_BUFFER, 0);
            glBindVertexArray(0);
        } finally {
            if (positionBuffer != null) {
                MemoryUtil.memFree(positionBuffer);
            }

            if (textCoordsBuffer != null) {
                MemoryUtil.memFree(textCoordsBuffer);
            }
            
            if (normalsBuffer != null) {
            	MemoryUtil.memFree(normalsBuffer);
            }
            
            if (weightsBuffer != null) {
            	MemoryUtil.memFree(weightsBuffer);
            }
            
            if (jointIndicesBuffer != null) {
            	MemoryUtil.memFree(jointIndicesBuffer);
            }

            if (indicesBuffer != null) {
                MemoryUtil.memFree(indicesBuffer);
            }
        }
    }
    
    /**
     * Calculates the bounding radius based upon the vertices of this Mesh.
     * 
     * @param positions - Array of vertices.
     */
    private void calculateBoundingRadius(float positions[]) {
    	int len = positions.length;
    	
    	this.boundingRadius = 0;
    	for (int i = 0; i < len; i++) {
    		float pos = positions[i];
    		boundingRadius = Math.max(Math.abs(pos), this.boundingRadius);
    	}
    }
    
    /**
     * Initialize rendering by binding the textures and VAOs used
     * for this mesh.
     */
    protected void initRenderer() {
    	Texture texture = material.getTexture();
        if (texture != null) {
            // Activate first texture bank.
            glActiveTexture(GL_TEXTURE0);

            // Bind target texture.
            glBindTexture(GL_TEXTURE_2D, texture.getId());
        }
        
        Texture normalMap = material.getNormalMap();
        if (normalMap != null) {
        	// Activate second texture bank
            glActiveTexture(GL_TEXTURE1);
            
            // Bind the texture
            glBindTexture(GL_TEXTURE_2D, normalMap.getId());
        }
        
	     // Draw the mesh.
	    glBindVertexArray(getVaoId());
	    glEnableVertexAttribArray(0);
	    glEnableVertexAttribArray(1);
	    glEnableVertexAttribArray(2);
	    glEnableVertexAttribArray(3);
	    glEnableVertexAttribArray(4);
    }
    
    /**
     * End the rendering cleans up after rendering by resetting the used
     * vertex attribute arrays and unbinds the VAO.
     */
    protected void endRenderer() {
    	// Restore state.
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glDisableVertexAttribArray(2);
        glDisableVertexAttribArray(3);
        glDisableVertexAttribArray(4);
        
        glBindVertexArray(0);
        
        glBindTexture(GL_TEXTURE_2D, 0);
    }

    /**
     * Render method that draws the mesh then restores the
     * state when finished.
     */
    public void render() {
    	initRenderer();
    	
        glDrawElements(GL_TRIANGLES, getVertexCount(), GL_UNSIGNED_INT, 0);
        
        endRenderer();
    }
    
    /**
     * Render method that renders a list of entities then restores the 
     * state when finished.
     * 
     * @param entities - List of entities to render.
     * @param consumer - Consumer for the entities.
     */
    public void renderList(List<Entity> entities, Consumer<Entity> consumer) {
    	initRenderer();
    	
    	for (Entity e : entities) {
    		if (e.insideFrustrum()) {
	    		// Set up data required from the entity.
	    		consumer.accept(e);
	    		
	    		// Render the entity.
	    		glDrawElements(GL_TRIANGLES, getVertexCount(), GL_UNSIGNED_INT, 0);
    		}
    	}
    	
    	endRenderer();
    }

    /**
     * Getter for the vertex array object id in this mesh.
     *
     * @return Handle of the vertex array object.
     */
    public int getVaoId() {
        return this.vaoId;
    }

    /**
     * Getter for the vertex count in this mesh.
     *
     * @return vertex count of this mesh.
     */
    public int getVertexCount() {
        return this.vertexCount;
    }

    /**
     * Getter for the material of this Mesh.
     *
     * @return - The material of this mesh.
     */
    public Material getMaterial() {
        return this.material;
    }

    /**
     * Setter for the material of this Mesh.
     *
     * @param material - The material value to set.
     */
    public void setMaterial(Material material) {
        this.material = material;
    }

    /**
     * Getter for the color value of this Mesh.
     *
     * @return - The color value of this Mesh.
     */
    public Color getColor() {
        return this.color;
    }

    /**
     * Setter for the color value of this Mesh.
     *
     * @param color - The color value to set.
     */
    public void setColor(Color color) {
        this.color = color;
    }
    
    /**
     * Getter for the bounding radius value for this Mesh.
     * 
     * @return - The bounding radius value for this Mesh.
     */
    public float getBoundingRadius() {
    	return this.boundingRadius;
    }
    
    /**
     * Setter for the bounding radius for this Mesh.
     * 
     * @param boundingRadius - The bounding radius value to set.
     */
    public void setBoundingRadius(float boundingRadius) {
    	this.boundingRadius = boundingRadius;
    }

    /**
     * Deletes this mesh and deletes the vertex array object and the
     * vertex buffer object.
     */
    public void delete() {
        glDisableVertexAttribArray(0);

        // Delete the vertex buffer objects.
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        for (int vboId : vboIdList) {
        	glDeleteBuffers(vboId);
        }

        Texture texture = material.getTexture();
        // Delete the texture used by this mesh.
        if (texture != null) {
            texture.delete();
        }

        // Delete the vertex array object.
        glBindVertexArray(0);
        glDeleteVertexArrays(vaoId);
    }

	/**
	 * Getter for the array of vertex positions for this Mesh.
	 * 
	 * @return - The vertex positions of this Mesh.
	 */
	public float[] getPositions() {
		return positions;
	}

	/**
	 * Setter for the vertex positions for this Mesh.
	 * 
	 * @param positions - The positions array to set.
	 */
	public void setPositions(float[] positions) {
		this.positions = positions;
	}

	/**
	 * Getter for the array of indices for this Mesh.
	 * 
	 * @return - The indices of this mesh.
	 */
	public int[] getIndices() {
		return indices;
	}

	/**
	 * Setter for the indices for this Mesh.
	 * 
	 * @param indices - The indices array to set
	 */
	public void setIndices(int[] indices) {
		this.indices = indices;
	}
}

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

import org.joml.Vector3f;
import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
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

    private static final Color DEFAULT_COLOR = Color.WHITE;

    /**
     * The vertex array object for this mesh.
     */
    private final int vaoId;

    /**
     *
     */
    private final int positionVboId;

    /**
     *
     */
    private final int textureVboId;

    /**
     *
     */
    private final int normalsVboId;

    /**
     *
     */
    private final int indexVboId;

    /**
     *
     */
    private final int vertexCount;

    /**
     *
     */
    private Texture texture;

    /**
     *
     */
    private Color color;

    //public Mesh(float[] positions, float[] colors, int[] indices) { // Create shape with colors
    //public Mesh(float[] positions, float[] textCoords, int[] indices, Texture texture) { // Create shape with texture
    public Mesh(float[] positions, float[] textCoords, float[] normals, int[] indices) {
        // Store array of floats in the buffer to interface correctly with C Library.
        FloatBuffer positionBuffer = null;
        FloatBuffer textCoordsBuffer = null;
        FloatBuffer normalsBuffer = null;
        IntBuffer indicesBuffer = null;
        try {
            color = DEFAULT_COLOR;
            vertexCount = indices.length;

            // Create the VAO and bind it.
            vaoId = glGenVertexArrays();
            glBindVertexArray(vaoId);

            // Position VBO
            positionVboId = glGenBuffers();
            positionBuffer = MemoryUtil.memAllocFloat(positions.length);
            positionBuffer.put(positions).flip();
            glBindBuffer(GL_ARRAY_BUFFER, positionVboId);
            glBufferData(GL_ARRAY_BUFFER, positionBuffer, GL_STATIC_DRAW);
            // Define structure of data and store it in VAO attribute list.
            glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);

            // Texture coordinates VBO
            textureVboId = glGenBuffers();
            textCoordsBuffer = MemoryUtil.memAllocFloat(textCoords.length);
            textCoordsBuffer.put(textCoords).flip();
            glBindBuffer(GL_ARRAY_BUFFER, textureVboId);
            glBufferData(GL_ARRAY_BUFFER, textCoordsBuffer, GL_STATIC_DRAW);
            glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);

            // Vertex normals VBO
            normalsVboId = glGenBuffers();
            normalsBuffer = MemoryUtil.memAllocFloat(normals.length);
            normalsBuffer.put(normals).flip();
            glBindBuffer(GL_ARRAY_BUFFER, normalsVboId);
            glBufferData(GL_ARRAY_BUFFER, normalsBuffer, GL_STATIC_DRAW);
            glVertexAttribPointer(2, 3, GL_FLOAT, false, 0, 0);


            // Index VBO
            indexVboId = glGenBuffers();
            indicesBuffer = MemoryUtil.memAllocInt(indices.length);
            indicesBuffer.put(indices).flip();
            glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indexVboId);
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

            if (indicesBuffer != null) {
                MemoryUtil.memFree(indicesBuffer);
            }
        }
    }

    /**
     * Render method that draws the mesh then restores the
     * state when finished.
     */
    public void render() {
        if (this.texture != null) {
            // Activate first texture bank.
            glActiveTexture(GL_TEXTURE0);

            // Bind target texture.
            glBindTexture(GL_TEXTURE_2D, texture.getId());
        }

        // Draw the mesh.
        glBindVertexArray(getVaoId());
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);
        glEnableVertexAttribArray(2);

        glDrawElements(GL_TRIANGLES, getVertexCount(), GL_UNSIGNED_INT, 0);

        // Restore state.
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glDisableVertexAttribArray(2);
        glBindVertexArray(0);
        glBindTexture(GL_TEXTURE_2D, 0);
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
     *
     *
     * @return
     */
    public boolean isTextured() {
        return this.texture != null;
    }

    /**
     *
     * @return
     */
    public Texture getTexture() {
        return this.texture;
    }

    /**
     *
     * @param texture
     */
    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    /**
     *
     * @return
     */
    public Color getColor() {
        return this.color;
    }

    /**
     *
     * @param color
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Deletes this mesh and deletes the vertex array object and the
     * vertex buffer object.
     */
    public void delete() {
        glDisableVertexAttribArray(0);

        // Delete the vertex buffer objects.
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glDeleteBuffers(positionVboId);
        glDeleteBuffers(textureVboId);
        glDeleteBuffers(normalsVboId);
        glDeleteBuffers(indexVboId);

        // Delete the texture used by this mesh.
        if (this.texture != null) {
            texture.delete();
        }

        // Delete the vertex array object.
        glBindVertexArray(0);
        glDeleteVertexArrays(vaoId);
    }
}

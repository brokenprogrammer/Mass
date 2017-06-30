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

import org.lwjgl.system.MemoryStack;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.GL_CLAMP_TO_EDGE;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;
import static org.lwjgl.stb.STBImage.stbi_failure_reason;
import static org.lwjgl.stb.STBImage.stbi_load;
import static org.lwjgl.stb.STBImage.stbi_set_flip_vertically_on_load;

/**
 * This class is a representation of a Texture.
 *
 * @author Oskar Mendel
 * @version 0.00.00
 * @name Texture.java
 */
public class Texture {

    /**
     * Handle of the texture.
     */
    private final int id;
    
    /**
     * Width of the texture.
     */
    private int width;

    /**
     * Height of the texture.
     */
    private int height;
    
    /**
     * Number of rows on the texture.
     */
    private int numRows = 1;
    
    /**
     * Number of columns on the texture.
     */
    private int numCols = 1;


    /**
     * Default constructor for the Texture.
     * Creates an empty texture.
     */
    public Texture() {
        this.id = glGenTextures();
    }
    
    /**
     * Creates a new empty texture with the specified width, 
     * height and pixel format.
     * 
     * @param width - Width of the Texture to create.
     * @param height - Height of the Texture to create.
     * @param pixelFormat - Pixel format of the Texture to create.
     * 
     * @throws Exception
     */
    public Texture(int width, int height, int pixelFormat) throws Exception {
    	this.id = glGenTextures();
    	this.width = width;
    	this.height = height;
    	
    	glBindTexture(GL_TEXTURE_2D, this.id);
    	glTexImage2D(GL_TEXTURE_2D, 0, GL_DEPTH_COMPONENT, this.width, this.height, 
    			0, pixelFormat, GL_FLOAT, (ByteBuffer) null);
    	
    	this.setParameter(GL_TEXTURE_MIN_FILTER, GL_NEAREST);
    	this.setParameter(GL_TEXTURE_MAG_FILTER, GL_NEAREST);
    	this.setParameter(GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
    	this.setParameter(GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
    }

    /**
     * Binds this texture.
     */
    public void bind() {
        glBindTexture(GL_TEXTURE_2D, id);
    }

    /**
     * Set a parameter for the texture with the specified value.
     *
     * @param name - Name of the parameter.
     * @param value - Value to set.
     */
    public void setParameter(int name, int value) {
        glTexParameteri(GL_TEXTURE_2D, name, value);
    }

    /**
     * Uploads image data with specified parameters such as internal format, width
     * height, format and data.
     *
     * @param internalFormat - Internal format of the image data.
     *                       Specifies the number of color components in the image data.
     * @param width - Width of the image.
     * @param height - Height of the image.
     * @param format - Specifies the format of the pixel data.
     * @param data - Pixel data of the image.
     */
    public void uploadData(int internalFormat, int width, int height, int format, ByteBuffer data) {
        glTexImage2D(GL_TEXTURE_2D, 0, internalFormat, width, height, 0, format, GL_UNSIGNED_BYTE, data);
    }

    /**
     * //TODO: Make javadoc
     * @return
     */
    public int getId() {
        return this.id;
    }

    /**
     * Getter for the width of this texture.
     *
     * @return - Texture width.
     */
    public int getWidth() {
        return this.width;
    }

    /**
     * Setter for the width of this texture.
     *
     * @param width - Texture width value to set.
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * Getter for the height of this texture.
     *
     * @return - Texture height.
     */
    public int getHeight() {
        return this.height;
    }

    /**
     * Setter for the height of this texture.
     *
     * @param height - Texture height value to set.
     */
    public void setHeight(int height) {
        this.height = height;
    }
    
    /**
     * Getter for the number of rows of this texture.
     * 
     * @return - The number of rows of this texture.
     */
    public int getNumRows() {
    	return this.numRows;
    }
    
    /**
     * Setter for the number of Rows of this texture.
     * 
     * @param rows - The number of rows value to set.
     */
    public void setNumRows(int rows) {
    	this.numRows = rows;
    }
    
    /**
     * Getter for the number of columns of this texture.
     * 
     * @return - The number of columns of this texture.
     */
    public int getNumCols() {
    	return this.numCols;
    }
    
    /**
     * Setter for the number of columns of this texture.
     * 
     * @param cols - The number of columns value to set.
     */
    public void setNumCols(int cols) {
    	this.numCols= cols;
    }

    /**
     * Deletes the texture.
     */
    public void delete() {
        glDeleteTextures(id);
    }

    /**
     * Creates a texture with the specified width, height and image data.
     *
     * @param width - Width of the texture.
     * @param height - Height of the texture.
     * @param data - Image data in RGBA format.
     *
     * @return - Texture generated from specified data.
     */
    public static Texture createTexture(int width, int height, ByteBuffer data) {
        Texture texture = new Texture();
        texture.setWidth(width);
        texture.setHeight(height);

        texture.bind();

        // Tell OpenGL how to unpack the RGBA bytes. Each component is 1 byte size
        glPixelStorei(GL_UNPACK_ALIGNMENT, 1);

        texture.setParameter(GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        texture.setParameter(GL_TEXTURE_MAG_FILTER, GL_NEAREST);

        // Upload the texture data.
        texture.uploadData(GL_RGBA, width, height, GL_RGBA, data);

        // Generate Mip Map
        glGenerateMipmap(GL_TEXTURE_2D);

        return texture;
    }

    /**
     * Loads a texture from a file at the specified path.
     *
     * @param path - File path of the texture file.
     *
     * @return A Texture built from the specified file.
     */
    public static Texture loadTexture(String path) {
        ByteBuffer image;
        int width;
        int height;

        try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer w = stack.mallocInt(1);
            IntBuffer h = stack.mallocInt(1);
            IntBuffer comp = stack.mallocInt(1);

            // Load image using the STB library
            stbi_set_flip_vertically_on_load(false);
            image = stbi_load(path, w, h, comp, 4);

            if (image == null) {
                throw new RuntimeException("Failed to load a texture file! " +
                        stbi_failure_reason());
            }

            width = w.get();
            height = h.get();
        }

        return createTexture(width, height, image);
    }
    
    /**
     * Loads a texture from a file at the specified path and sets the
     * number of rows and columns to the specified values. 
     * 
     * @param path - File path of the texture file.
     * @param numCols - Number of columns for this texture.
     * @param numRows - Number of rows of this texture.
     * 
     * @return A Texture built from the specified file with specified 
     * number of rows and columns.
     */
    public static Texture loadTexture(String path, int numCols, int numRows) {
    	Texture t = loadTexture(path);
    	t.setNumCols(numCols);
    	t.setNumRows(numRows);
    	
    	return t;
    }
}

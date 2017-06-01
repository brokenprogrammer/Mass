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

package me.oskarmendel.mass.gfx.shader;

import java.io.*;

import static org.lwjgl.opengl.GL11.GL_TRUE;
import static org.lwjgl.opengl.GL20.*;

/**
 * This class represents a shader.
 *
 * @author Oskar Mendel
 * @version 0.00.00
 * @name Shader.java
 */
public class Shader {

    /**
     * Stores the handle of the shader.
     */
    private final int id;

    /**
     * Creates a shader with the specified type.
     * Can be either <code>GL_VERTEX_SHADER</code> or <code>GL_FRAGMENT_SHADER</code>.
     *
     * @param type - Type of shader.
     */
    public Shader(int type) {
        id = glCreateShader(type);
    }

    /**
     * Sets the source code of this shader.
     *
     * @param source - GLSL source code for this shader.
     */
    public void setSource(CharSequence source) {
        glShaderSource(id, source);
    }

    /**
     * Compile this shader and checks it's status afterwards.
     */
    public void compile() {
        glCompileShader(id);

        checkStatus();
    }

    /**
     * Checks if the shader was successfully compiled.
     */
    private void checkStatus() {
        int status = glGetShaderi(id, GL_COMPILE_STATUS);

        if (status != GL_TRUE) {
            throw new RuntimeException(glGetShaderInfoLog(id));
        }
    }

    /**
     * Deletes this shader.
     */
    public void delete() {
        glDeleteShader(id);
    }

    /**
     * Getter for the id of the shader.
     *
     * @return Handle of this shader.
     */
    public int getId() {
        return this.id;
    }

    /**
     * Creates a shader with the specified type and source then compiles it.
     * The type can be either <code>GL_VERTEX_SHADER</code> or
     * <code>GL_FRAGMENT_SHADER</code>.
     *
     * @param type - Type of shader.
     * @param source - GLSL source code for this shader.
     *
     * @return A compiler shader of the specified type and source.
     */
    public static Shader createShader(int type, CharSequence source) {
        Shader shader = new Shader(type);
        shader.setSource(source);
        shader.compile();

        return shader;
    }

    /**
     * Loads a shader from a file at the specified path.
     *
     * @param type - Type of shader.
     * @param path - File path for the shader file.
     *
     * @return A compiled shader from the specified file and type.
     */
    public static Shader loadShader(int type, String path) {
        StringBuilder stringBuilder = new StringBuilder();

        try (InputStream in = new FileInputStream(path);
             BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
            String line;

            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }

        } catch (IOException e) {
            throw new RuntimeException("Failed to load a shader file! " +
                e.getMessage());
        }

        CharSequence source = stringBuilder.toString();

        return createShader(type, source);
    }
}

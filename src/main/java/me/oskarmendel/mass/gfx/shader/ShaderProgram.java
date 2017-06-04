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

import me.oskarmendel.mass.gfx.Material;
import me.oskarmendel.mass.gfx.light.PointLight;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.system.MemoryStack;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL11.GL_TRUE;
import static org.lwjgl.opengl.GL20.*;

/**
 * This class represents a shader program.
 *
 * @author Oskar Mendel
 * @version 0.00.00
 * @name ShaderProgram.java
 */
public class ShaderProgram {

    /**
     * Stores the handle of the program.
     */
    private final int id;

    /**
     * Create a shader program.
     */
    public ShaderProgram() {
        id = glCreateProgram();
    }

    /**
     * Attatch a shader to this shader program.
     *
     */
    public void attachShader(Shader shader) {
        glAttachShader(id, shader.getId());
    }

    /**
     * Link this program and checks it's status afterwards.
     */
    public void link() {
        glLinkProgram(id);

        checkStatus();
    }

    /**
     * Gets the location of a uniform variable with specified name.
     *
     * @param name - Name of the uniform variable.
     *
     * @return Location of the uniform.
     */
    public int getUniformLocation(CharSequence name) {
        return glGetUniformLocation(id, name);
    }

    /**
     * Set the uniform variable at the specified location.
     *
     * @param location - Uniform location.
     * @param value - Value to set at the specified location.
     */
    public void setUniform(int location, int value) {
        glUniform1i(location, value);
    }

    /**
     * Set the uniform variable at the specified location.
     *
     * @param location - Uniform location.
     * @param value - Value to set at the specified location.
     */
    public void setUniform(int location, float value) {
        glUniform1f(location, value);
    }

    /**
     * Sets the uniform variable at the specified location.
     *
     * @param location - Uniform location.
     * @param value - Value to set at the specified location.
     */
    public void setUniform(int location, Vector3f value) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            FloatBuffer floatBuffer = stack.mallocFloat(3);
            value.get(floatBuffer);
            glUniform3fv(location, floatBuffer);
        }
    }

    /**
     * Sets the uniform variable at the specified location.
     *
     * @param location - Uniform location.
     * @param value - Value to set at the specified location.
     */
    public void setUniform(int location, Vector4f value) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            FloatBuffer floatBuffer = stack.mallocFloat(4);
            value.get(floatBuffer);
            glUniform4fv(location, floatBuffer);
        }
    }

    /**
     * Sets the uniform variable at the specified location.
     *
     * @param location - Uniform location.
     * @param value - Value to set at the specified location.
     */
    public void setUniform(int location, Matrix4f value) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            FloatBuffer floatBuffer = stack.mallocFloat(4*4);
            value.get(floatBuffer);
            glUniformMatrix4fv(location, false, floatBuffer);
        }
    }

    /**
     * Sets the PointLight uniform variable with the specified location name.
     *
     * @param location - Location name.
     * @param pointLight - PointLight object to get values from.
     */
    public void setUniform(String location, PointLight pointLight) {
        setUniform(getUniformLocation(location + ".color"), pointLight.getColor().toVector3f());
        setUniform(getUniformLocation(location + ".position"), pointLight.getPosition());
        setUniform(getUniformLocation(location + ".intensity"), pointLight.getIntensity());
        setUniform(getUniformLocation(location + ".att.constant"), pointLight.getAttenuation().getConstant());
        setUniform(getUniformLocation(location + ".att.linear"), pointLight.getAttenuation().getLinear());
        setUniform(getUniformLocation(location + ".att.exponent"), pointLight.getAttenuation().getExponent());
    }

    /**
     * Sets the Material uniform variable with the specified location name.
     *
     * @param location - Location name.
     * @param material - Material object to get values from.
     */
    public void setUniform(String location, Material material) {
        setUniform(getUniformLocation(location + ".ambient"), material.getAmbientColor());
        setUniform(getUniformLocation(location + ".diffuse"), material.getDiffuseColor());
        setUniform(getUniformLocation(location + ".specular"), material.getSpecularColor());
        setUniform(getUniformLocation(location + ".hasTexture"), material.isTextured() ? 1 : 0);
        setUniform(getUniformLocation(location + ".reflectance"), material.getReflectance());
    }

    /**
     * Use this shader program.
     */
    public void use() {
        glUseProgram(id);
    }

    /**
     * Stops using this shader program.
     */
    public void stopUse() {
        glUseProgram(0);
    }

    /**
     * Checks if a shader program was successfully linked.
     */
    public void checkStatus() {
        int status = glGetProgrami(id, GL_LINK_STATUS);

        if (status != GL_TRUE) {
            throw new RuntimeException(glGetProgramInfoLog(id));
        }
    }

    /**
     * Deletes the shader program.
     */
    public void delete() {
        glDeleteProgram(id);
    }
}

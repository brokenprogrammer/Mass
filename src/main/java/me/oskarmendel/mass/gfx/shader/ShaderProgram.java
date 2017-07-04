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
import me.oskarmendel.mass.gfx.light.DirectionalLight;
import me.oskarmendel.mass.gfx.light.PointLight;
import me.oskarmendel.mass.gfx.light.SpotLight;
import me.oskarmendel.mass.gfx.weather.Fog;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.system.MemoryStack;

import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.Map;

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
     * HashMap to store uniform locations within a ShaderProgram.
     */
    private final Map<String, Integer> uniformLocations;

    /**
     * Create a shader program.
     */
    public ShaderProgram() {
        id = glCreateProgram();
        
        uniformLocations = new HashMap<>();
    }
    
    /**
     * Creates a new uniform by storing the specified uniform together
     * with its uniform location in the uniformLocations HashMap.
     * 
     * @param uniform - Uniform identifier.
     */
    public void createUniform(String uniform) {
    	this.uniformLocations.put(uniform, this.getUniformLocation(uniform));
    }
    
    /**
     * Creates a new uniform array by storing the specified uniform together
     * with its uniform location in the uniformLocations HashMap.
     * 
     * @param uniform - Uniform identifier.
     * @param size - Amount of uniforms to create.
     */
    public void createUniform(String uniform, int size) {
    	for (int i = 0; i < size; i++) {    		
    		this.createUniform(uniform + "[" + i + "]");
    	}
    }
    
    /**
     * Creates a new PointLight uniform array by storing the specified uniform together
     * with its uniform location in the uniformLocations HashMap.
     * 
     * @param uniform - Uniform identifier.
     * @param size - Amount of uniforms to create.
     */
    public void createPointLightUniform(String uniform, int size) {
    	for (int i = 0; i < size; i++) {
    		createPointLightUniform(uniform + "[" + i + "]");
    	}
    }
    
    /**
     * Creates a new PointLight uniform by storing the specified uniform
     * together with its uniform location in the uniformsLocation HashMap.
     * 
     * @param uniform - Uniform identifier.
     */
    public void createPointLightUniform(String uniform) {
    	createUniform(uniform + ".color");
        createUniform(uniform + ".position");
        createUniform(uniform + ".intensity");
        createUniform(uniform + ".att.constant");
        createUniform(uniform + ".att.linear");
        createUniform(uniform + ".att.exponent");
    }
    
    /**
     * Creates a new SpotLight uniform array by storing the specified uniform together
     * with its uniform location in the uniformLocations HashMap.
     * 
     * @param uniform - Uniform identifier.
     * @param size - Amount of uniforms to create.
     */
    public void createSpotLightUniform(String uniform, int size) {
    	for (int i = 0; i < size; i++) {
    		createSpotLightUniform(uniform + "[" + i + "]");
    	}
    }
    
    /**
     * Creates a new SpotLight uniform by storing the specified uniform
     * together with its uniform location in the uniformsLocation HashMap.
     * 
     * @param uniform - Uniform identifier.
     */
    public void createSpotLightUniform(String uniform) {
    	createPointLightUniform(uniform + ".pl");
        createUniform(uniform + ".conedir");
        createUniform(uniform + ".cutoff");
    }
    
    /**
     * Creates a new DirectionalLight uniform by storing the specified uniform
     * together with its uniform location in the uniformsLocation HashMap.
     * 
     * @param uniform - Uniform identifier.
     */
    public void createDirectionalLightUniform(String uniform) {
    	createUniform(uniform + ".color");
    	createUniform(uniform + ".direction");
    	createUniform(uniform + ".intensity");
    }
    
    /**
     * Creates a new Material uniform by storing the specified uniform
     * together with its uniform location in the uniformsLocation HashMap.
     * 
     * @param uniform - Uniform identifier.
     */
    public void createMaterialUniform(String uniform) {
    	createUniform(uniform + ".ambient");
        createUniform(uniform + ".diffuse");
        createUniform(uniform + ".specular");
        createUniform(uniform + ".hasTexture");
        createUniform(uniform + ".hasNormalMap");
        createUniform(uniform + ".reflectance");
    }
    
    /**
     * Creates a new Fog uniform by storing the specified uniform
     * together with its uniform location in the uniformsLocation HashMap.
     * 
     * @param uniform - Uniform identifier.
     */
    public void createFogUniform(String uniform) {
    	createUniform(uniform + ".activeFog");
        createUniform(uniform + ".color");
        createUniform(uniform + ".density");
    }

    /**
     * Attach a shader to this shader program.
     *
     * @param shader - Shader to attach to this ShaderProgram.
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
    public void setUniform(String location, int value) {
        glUniform1i(uniformLocations.get(location), value);
    }

    /**
     * Set the uniform variable at the specified location.
     *
     * @param location - Uniform location.
     * @param value - Value to set at the specified location.
     */
    public void setUniform(String location, float value) {
        glUniform1f(uniformLocations.get(location), value);
    }
    
    /**
     * Set the uniform variable at the specified location.
     * 
     * @param location - Uniform location.
     * @param value - Value to set at the specified location.
     * @param pos - Array position for the value to set.
     */
    public void setUniform(String location, float value, int pos) {
    	setUniform((location + "[" + pos + "]"), value);
    }

    /**
     * Sets the uniform variable at the specified location.
     *
     * @param location - Uniform location.
     * @param value - Value to set at the specified location.
     */
    public void setUniform(String location, Vector3f value) {
        glUniform3f(uniformLocations.get(location), value.x, value.y, value.z);
    }

    /**
     * Sets the uniform variable at the specified location.
     *
     * @param location - Uniform location.
     * @param value - Value to set at the specified location.
     */
    public void setUniform(String location, Vector4f value) {
        glUniform4f(uniformLocations.get(location), value.x, value.y, value.z, value.w);
    }

    /**
     * Sets the uniform variable at the specified location.
     *
     * @param location - Uniform location.
     * @param value - Value to set at the specified location.
     */
    public void setUniform(String location, Matrix4f value) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            FloatBuffer floatBuffer = stack.mallocFloat(4*4);
            value.get(floatBuffer);
            glUniformMatrix4fv(uniformLocations.get(location), false, floatBuffer);
        }
    }
    
    /**
     * Set the uniform variable at the specified location.
     * 
     * @param location - Uniform location.
     * @param value - Value to set at the specified location.
     * @param pos - Array position for the value to set.
     */
    public void setUniform(String location, Matrix4f value, int pos) {
    	setUniform((location + "[" + pos + "]"), value);
    }
    
    /**
     * Sets the entire PointLight array uniform with the specified 
     * PointLights at the specified location.
     * 
     * @param location - Location name.
     * @param spotLights - Array of PointLights to set.
     */
    public void setUniform(String location, PointLight[] pointLights) {
    	int lights = pointLights != null ? pointLights.length : 0;
    	
    	for (int i = 0; i < lights; i++) {
    		setUniform(location, pointLights[i], i);
    	}
    }

    /**
     * Sets the PointLight uniform variable with the specified location name.
     *
     * @param location - Location name.
     * @param pointLight - PointLight object to get values from.
     */
    public void setUniform(String location, PointLight pointLight) {
        setUniform((location + ".color"), pointLight.getColor().toVector3f());
        setUniform((location + ".position"), pointLight.getPosition());
        setUniform((location + ".intensity"), pointLight.getIntensity());
        setUniform((location + ".att.constant"), pointLight.getAttenuation().getConstant());
        setUniform((location + ".att.linear"), pointLight.getAttenuation().getLinear());
        setUniform((location + ".att.exponent"), pointLight.getAttenuation().getExponent());
    }
    
    /**
     * Sets the PointLight uniform variable with the specified 
     * location name at the specified position.
     * 
     * @param location - Location name.
     * @param pointLight - PointLight object to get values from.
     * @param pos - Position of the PointLight to set.
     */
    public void setUniform(String location, PointLight pointLight, int pos) {
    	setUniform(location + "[" + pos + "]", pointLight);
    }
    
    /**
     * Sets the entire SpotLight array uniform with the specified 
     * spotlights at the specified location.
     * 
     * @param location - Location name.
     * @param spotLights - Array of SpotLights to set.
     */
    public void setUniform(String location, SpotLight[] spotLights) {
    	int lights = spotLights != null ? spotLights.length : 0;
    	
    	for (int i = 0; i < lights; i++) {
    		setUniform(location, spotLights[i], i);
    	}
    }
    
    /**
     * Sets the SpotLight uniform variable with the specified location name.
     *
     * @param location - Location name.
     * @param spotLight - SpotLight object to get values from.
     */
    public void setUniform(String location, SpotLight spotLight) {
    	setUniform((location + ".pl"), spotLight.getPointLight());
    	setUniform((location + ".coneDirection"), spotLight.getConeDirection());
        setUniform((location + ".cutOff"), spotLight.getCutOff());
    }
    
    /**
     * Sets the SpotLight uniform variable with the specified 
     * location name at the specified position.
     * 
     * @param location - Location name.
     * @param pointLight - SpotLight object to get values from.
     * @param pos - Position of the SpotLight to set.
     */
    public void setUniform(String location, SpotLight spotLight, int pos) {
    	setUniform(location + "[" + pos + "]", spotLight);
    }

    /**
     * Sets the DirectionalLight uniform variable with the specified location name.
     *
     * @param location - Location name.
     * @param directionalLight - DirectionalLight object to get values from.
     */
    public void setUniform(String location, DirectionalLight directionalLight) {
        setUniform((location + ".color"), directionalLight.getColor().toVector3f());
        setUniform((location + ".direction"), directionalLight.getDirection());
        setUniform((location + ".intensity"), directionalLight.getIntensity());
    }

    /**
     * Sets the Material uniform variable with the specified location name.
     *
     * @param location - Location name.
     * @param material - Material object to get values from.
     */
    public void setUniform(String location, Material material) {
        setUniform((location + ".ambient"), material.getAmbientColor());
        setUniform((location + ".diffuse"), material.getDiffuseColor());
        setUniform((location + ".specular"), material.getSpecularColor());
        setUniform((location + ".hasTexture"), material.isTextured() ? 1 : 0);
        setUniform((location + ".reflectance"), material.getReflectance());
    }
    
    /**
     * Sets the Fog uniform variable with the specified location name.
     * 
     * @param location - Location name.
     * @param fog - Fog object to retrieve values from.
     */
    public void setUniform(String location, Fog fog) {
    	setUniform((location + ".activeFog"), fog.isActive() ? 1 : 0);
    	setUniform((location + ".color"), fog.getColor().toVector3f());
    	setUniform((location + ".density"), fog.getDensity());
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

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

import me.oskarmendel.mass.core.Camera;
import me.oskarmendel.mass.entity.Entity;
import me.oskarmendel.mass.gfx.light.DirectionalLight;
import me.oskarmendel.mass.gfx.light.PointLight;
import me.oskarmendel.mass.gfx.light.SpotLight;
import me.oskarmendel.mass.gfx.shader.Shader;
import me.oskarmendel.mass.gfx.shader.ShaderProgram;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

/**
 * Performs the rendering process.
 *
 * @author Oskar Mendel
 * @version 0.00.00
 * @name Renderer.java
 */
public class Renderer {

    Transformation transformation;

    /**
     * Field of View in Radians
     */
    private static final float FOV = (float) Math.toRadians(60.0f);
    
    private static final int MAX_POINT_LIGHTS = 5;
    private static final int MAX_SPOT_LIGHTS = 5;
    
    /**
     * Distance to near plane.
     */
    private static final float Z_NEAR = 0.01f;

    /**
     * Distance to far plane.
     */
    private static final float Z_FAR = 1000.f;

    private ShaderProgram shaderProgram;

    /**
     *
     */
    public Renderer() {
        transformation = new Transformation();
    }

    /**
     * Initializes the renderer.
     */
    public void init() {
        //TODO SHOULD THE INIT METHOD REALLY CREATE A SHADER PROGRAM?
        Shader vertexShader = Shader.loadShader(GL_VERTEX_SHADER, "src/main/resources/shaders/default.vert");
        Shader fragmentShader = Shader.loadShader(GL_FRAGMENT_SHADER, "src/main/resources/shaders/default.frag");
        shaderProgram = new ShaderProgram();
        shaderProgram.attachShader(vertexShader);
        shaderProgram.attachShader(fragmentShader);
        shaderProgram.link();
    }

    /**
     * 
     * @param camera
     * @param entities
     * @param ambientLight
     * @param pointLights
     * @param spotLights
     * @param directionalLight
     */
    public void render(Camera camera, Entity[] entities, Vector3f ambientLight,
                       PointLight[] pointLights, SpotLight[] spotLights, DirectionalLight directionalLight) {
        clear();

        shaderProgram.use();
        Matrix4f projectionMatrix = transformation.getProjectionMatrix(FOV, 800, 600, Z_NEAR, Z_FAR);
        shaderProgram.setUniform(shaderProgram.getUniformLocation("projectionMatrix"), projectionMatrix);

        Matrix4f viewMatrix = camera.getViewMatrix();
        
        // Update light uniforms.
        renderLights(viewMatrix, ambientLight, pointLights, spotLights, directionalLight);

        shaderProgram.setUniform(shaderProgram.getUniformLocation("texture_sampler"), 0);

        for (Entity entity : entities) {
            // Set the world matrix for this entity
        	for (Mesh mesh : entity.getMeshes()) {
	            Matrix4f modelViewMatrix =
	                    transformation.getModelViewMatrix(entity, viewMatrix);
	            shaderProgram.setUniform(shaderProgram.getUniformLocation("modelViewMatrix"), modelViewMatrix);
	
	            // Render the mesh for this entity
	            shaderProgram.setUniform("material", mesh.getMaterial());
	
	            mesh.render();
        	}
        }

        shaderProgram.stopUse();
    }
    
    /**
     * 
     * @param viewMatrix
     * @param ambientLight
     * @param pointLights
     * @param spotLights
     * @param directionalLight
     */
    private void renderLights(Matrix4f viewMatrix, Vector3f ambientLight, 
    		PointLight[] pointLights, SpotLight[] spotLights, DirectionalLight directionalLight) {
    	
    	// TODO: Throw error if too many lights? - Oskar Mendel 2017-06-28
    	
    	shaderProgram.setUniform(shaderProgram.getUniformLocation("ambientLight"), ambientLight);
        shaderProgram.setUniform(shaderProgram.getUniformLocation("specularPower"), 10f);
        
        // Process pointlights
        int lights = pointLights != null ? pointLights.length : 0;
        for (int i = 0; i < lights; i++) {
        	PointLight currPointLight = new PointLight(pointLights[i]);
			Vector3f lightPos = currPointLight.getPosition();
			
			Vector4f aux = new Vector4f(lightPos, 1);
			aux.mul(viewMatrix);
			lightPos.x = aux.x;
			lightPos.y = aux.y;
			lightPos.z = aux.z;
			
			shaderProgram.setUniform("pointLights", currPointLight, i);
        }
        
        // Process spotLights
        lights = spotLights != null ? spotLights.length : 0;
        for (int i = 0; i < lights; i++) {
        	SpotLight currSpotLight = new SpotLight(spotLights[i]);
			Vector4f dir = new Vector4f(currSpotLight.getConeDirection(), 0);
			dir.mul(viewMatrix);
			
			currSpotLight.setConeDirection(new Vector3f(dir.x, dir.y, dir.z));
			Vector3f spotLightPos = currSpotLight.getPointLight().getPosition();
			
			Vector4f auxSpot = new Vector4f(spotLightPos, 1);
			auxSpot.mul(viewMatrix);
			spotLightPos.x = auxSpot.x;
			spotLightPos.y = auxSpot.y;
			spotLightPos.z = auxSpot.z;
			
			shaderProgram.setUniform("spotLights", currSpotLight, i);
        }
        
        // Process directionalLight
        DirectionalLight currDirLight = new DirectionalLight(directionalLight);
		Vector4f dir = new Vector4f(currDirLight.getDirection(), 0);
		dir.mul(viewMatrix);
		currDirLight.setDirection(new Vector3f(dir.x, dir.y, dir.z));
		shaderProgram.setUniform("directionalLight", currDirLight);
    }

    /**
     * Clears the screen.
     */
    public void clear() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }

    /**
     * Destroy all resources used by the renderer.
     */
    public void dispose() {
        // Destroy the shader program.
        shaderProgram.delete();
    }
}

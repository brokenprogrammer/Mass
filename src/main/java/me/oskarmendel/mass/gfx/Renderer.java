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

    public void render(Camera camera, Entity[] entities, Vector3f ambientLight,
                       PointLight pointLight, SpotLight spotLight, DirectionalLight directionalLight) {
        clear();

        shaderProgram.use();
        Matrix4f projectionMatrix = transformation.getProjectionMatrix(FOV, 800, 600, Z_NEAR, Z_FAR);
        shaderProgram.setUniform(shaderProgram.getUniformLocation("projectionMatrix"), projectionMatrix);

        Matrix4f viewMatrix = camera.getViewMatrix();

        // Update light uniforms
        shaderProgram.setUniform(shaderProgram.getUniformLocation("ambientLight"), ambientLight);
        shaderProgram.setUniform(shaderProgram.getUniformLocation("specularPower"), 10f);

        // Update the point light relative to the viewMatrix of the camera.
        PointLight currPointLight = new PointLight(pointLight);
        Vector3f lightPos = currPointLight.getPosition();
        Vector4f aux = new Vector4f(lightPos, 1);
        aux.mul(viewMatrix);
        lightPos.x = aux.x;
        lightPos.y = aux.y;
        lightPos.z = aux.z;
        shaderProgram.setUniform("pointLight", currPointLight);
        
        // Update the spot light relative to the view matrix.
        SpotLight currSpotLight = new SpotLight(spotLight);
        Vector4f dir = new Vector4f(currSpotLight.getConeDirection(), 0);
        dir.mul(viewMatrix);
        currSpotLight.setConeDirection(new Vector3f(dir.x, dir.y, dir.z));
        
        Vector3f spotLightPos = currSpotLight.getPointLight().getPosition();
        Vector4f auxSpot = new Vector4f(spotLightPos, 1);
        auxSpot.mul(viewMatrix);
        spotLightPos.x = auxSpot.x;
        spotLightPos.y = auxSpot.y;
        spotLightPos.z = auxSpot.z;
        shaderProgram.setUniform("spotLight", currSpotLight);

        // Update the directional light relative to the viewMatrix of the camera.
        DirectionalLight currDirLight = new DirectionalLight(directionalLight);
        dir = new Vector4f(currDirLight.getDirection(), 0);
        dir.mul(viewMatrix);
        currDirLight.setDirection(new Vector3f(dir.x, dir.y, dir.z));
        shaderProgram.setUniform("directionalLight", currDirLight);

        shaderProgram.setUniform(shaderProgram.getUniformLocation("texture_sampler"), 0);

        for (Entity entity : entities) {
            // Set the world matrix for this entity
            Matrix4f modelViewMatrix =
                    transformation.getModelViewMatrix(entity, viewMatrix);
            shaderProgram.setUniform(shaderProgram.getUniformLocation("modelViewMatrix"), modelViewMatrix);

            // Render the mesh for this entity
            shaderProgram.setUniform("material", entity.getMesh().getMaterial());

            entity.getMesh().render();
        }

        shaderProgram.stopUse();
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

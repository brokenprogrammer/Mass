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
import me.oskarmendel.mass.core.Scene;
import me.oskarmendel.mass.entity.Entity;
import me.oskarmendel.mass.entity.SkyBox;
import me.oskarmendel.mass.entity.animated.AnimatedEntity;
import me.oskarmendel.mass.gfx.filter.FrustumCullingFilter;
import me.oskarmendel.mass.gfx.light.DirectionalLight;
import me.oskarmendel.mass.gfx.light.PointLight;
import me.oskarmendel.mass.gfx.light.SpotLight;
import me.oskarmendel.mass.gfx.shader.Shader;
import me.oskarmendel.mass.gfx.shader.ShaderProgram;
import me.oskarmendel.mass.gfx.shadow.ShadowCascade;
import me.oskarmendel.mass.gfx.shadow.ShadowRenderer;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL13.GL_TEXTURE2;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Performs the rendering process.
 *
 * @author Oskar Mendel
 * @version 0.00.00
 * @name Renderer.java
 */
public class Renderer {

    private static final int MAX_POINT_LIGHTS = 5;
    
    private static final int MAX_SPOT_LIGHTS = 5;
    
    /**
     * 
     */
    private final Transformation transformation;
    
    private final ShadowRenderer shadowRenderer;
    
    private ShaderProgram defaultShaderProgram;
    
    private ShaderProgram skyBoxShaderProgram;
    
    private final float specularPower;
    
    private final FrustumCullingFilter frustumFilter;
    
    private final List<Entity> filteredEntities;

    /**
     *
     */
    public Renderer() {
        this.transformation = new Transformation();
        this.specularPower = 10f;
        this.shadowRenderer = new ShadowRenderer();
        this.frustumFilter = new FrustumCullingFilter();
        this.filteredEntities = new ArrayList<>();
    }

    /**
     * Initializes the renderer.
     */
    public void init() throws Exception {
    	shadowRenderer.init();
    	
    	setupSkyBoxShader();
    	setupDefaultShader();
    }
    
    public void setupSkyBoxShader() {
    	this.skyBoxShaderProgram = new ShaderProgram();
    	
    	Shader vertexShader = Shader.loadShader(GL_VERTEX_SHADER, "src/main/resources/shaders/skybox.vert");
		Shader fragmentShader = Shader.loadShader(GL_FRAGMENT_SHADER, "src/main/resources/shaders/skybox.frag");
		
		this.skyBoxShaderProgram.attachShader(vertexShader);
		this.skyBoxShaderProgram.attachShader(fragmentShader);
		this.skyBoxShaderProgram.link();
    }
    
    public void setupDefaultShader() {
    	this.defaultShaderProgram = new ShaderProgram();
    	
    	Shader vertexShader = Shader.loadShader(GL_VERTEX_SHADER, "src/main/resources/shaders/default.vert");
		Shader fragmentShader = Shader.loadShader(GL_FRAGMENT_SHADER, "src/main/resources/shaders/default.frag");
		
		this.defaultShaderProgram.attachShader(vertexShader);
		this.defaultShaderProgram.attachShader(fragmentShader);
		this.defaultShaderProgram.link();
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
    public void render(Screen screen, Camera camera, Scene scene, boolean changed) {
        clear();

        //TODO: if (screen.getOptions().frustumCulling) { - Oskar Mendel 2017-07-01
        if (true) {
        	this.frustumFilter.updateFrustum(screen.getProjectionMatrix(), camera.getViewMatrix());
        	this.frustumFilter.filter(scene.getEntityMeshes());
        	this.frustumFilter.filter(scene.getEntityInstancedMeshes());
        }
        
        // Render depth map.
        if (scene.isRenderShadows() && changed) {
        	shadowRenderer.render(screen, scene, camera, transformation, this);
        }
        
        glViewport(0, 0, screen.getWidth(), screen.getHeight());
        
        // Update projection matrix
        screen.updateProjectionMatrix();
        
        renderScene(screen, camera, scene);
        renderSkyBox(screen, camera, scene);
        
//        shaderProgram.use();
//        Matrix4f projectionMatrix = transformation.getProjectionMatrix(FOV, 800, 600, Z_NEAR, Z_FAR);
//        shaderProgram.setUniform(shaderProgram.getUniformLocation("projectionMatrix"), projectionMatrix);
//
//        Matrix4f viewMatrix = camera.getViewMatrix();
//        
//        // Update light uniforms.
//        renderLights(viewMatrix, ambientLight, pointLights, spotLights, directionalLight);
//
//        shaderProgram.setUniform(shaderProgram.getUniformLocation("texture_sampler"), 0);
//
//        for (Entity entity : entities) {
//            // Set the world matrix for this entity
//        	for (Mesh mesh : entity.getMeshes()) {
//	            Matrix4f modelViewMatrix =
//	                    transformation.getModelViewMatrix(entity, viewMatrix);
//	            shaderProgram.setUniform(shaderProgram.getUniformLocation("modelViewMatrix"), modelViewMatrix);
//	
//	            // Render the mesh for this entity
//	            shaderProgram.setUniform("material", mesh.getMaterial());
//	
//	            mesh.render();
//        	}
//        }
//
//        shaderProgram.stopUse();
    }
    
    /**
     * 
     * @param screen
     * @param camera
     * @param scene
     */
    private void renderSkyBox(Screen screen, Camera camera, Scene scene) {
    	SkyBox skyBox = scene.getSkyBox();
    	if (skyBox != null) {
    		this.skyBoxShaderProgram.use();
    		
    		this.skyBoxShaderProgram.setUniform(skyBoxShaderProgram.getUniformLocation("texture_sampler"), 0);
    		
    		Matrix4f projectionMatrix = screen.getProjectionMatrix();
    		this.skyBoxShaderProgram.setUniform(skyBoxShaderProgram.getUniformLocation("projectionMatrix"), projectionMatrix);
    		
    		Matrix4f viewMatrix = camera.getViewMatrix();
    		float m30 = viewMatrix.m30();
    		viewMatrix.m30(0);
    		float m31 = viewMatrix.m31();
    		viewMatrix.m31(0);
    		float m32 = viewMatrix.m32();
    		viewMatrix.m32(0);
    		
    		Mesh mesh = skyBox.getMesh();
    		Matrix4f modelViewMatrix = this.transformation.buildModelViewMatrix(skyBox, viewMatrix);
    		
    		this.skyBoxShaderProgram.setUniform(this.skyBoxShaderProgram.getUniformLocation("modelViewMatrix"), modelViewMatrix);
    		this.skyBoxShaderProgram.setUniform(this.skyBoxShaderProgram.getUniformLocation("ambientLight"), scene.getSceneLight().getAmbientLight());
    		this.skyBoxShaderProgram.setUniform(this.skyBoxShaderProgram.getUniformLocation("color"), mesh.getMaterial().getAmbientColor());
    		this.skyBoxShaderProgram.setUniform(this.skyBoxShaderProgram.getUniformLocation("hasTexture"), mesh.getMaterial().isTextured() ? 1 : 0);
    		
    		mesh.render();
    		
    		viewMatrix.m30(m30);
    		viewMatrix.m31(m31);
    		viewMatrix.m32(m32);
    		
    		this.skyBoxShaderProgram.stopUse();
    	}
    }
    
    /**
     * 
     * @param screen
     * @param camera
     * @param scene
     */
    private void renderScene(Screen screen, Camera camera, Scene scene) {
    	this.defaultShaderProgram.use();
    	
    	Matrix4f viewMatrix = camera.getViewMatrix();
    	Matrix4f projectionMatrix = screen.getProjectionMatrix();
    	
    	this.defaultShaderProgram.setUniform(this.defaultShaderProgram.getUniformLocation("viewMatrix"), viewMatrix);
    	this.defaultShaderProgram.setUniform(this.defaultShaderProgram.getUniformLocation("projectionMatrix"), projectionMatrix);
    	
    	List<ShadowCascade> shadowCascades = this.shadowRenderer.getShadowCascades();
    	for (int i = 0; i < ShadowRenderer.NUM_CASCADES; i++) {
    		ShadowCascade shadowCascade = shadowCascades.get(i);
    		this.defaultShaderProgram.setUniform("orthoProjectionMatrix", shadowCascade.getOrthoProjectionMatrix(), i);
    		this.defaultShaderProgram.setUniform("cascadeFarPlanes", ShadowRenderer.CASCADE_SPLITS[i], i);
    		this.defaultShaderProgram.setUniform("lightViewMatrix", shadowCascade.getLightViewMatrix(), i);
    	}
    	
    	SceneLight sceneLight = scene.getSceneLight();
    	renderLights(viewMatrix, sceneLight);
    	
    	this.defaultShaderProgram.setUniform("fog", scene.getFog());
    	this.defaultShaderProgram.setUniform(this.defaultShaderProgram.getUniformLocation("texture_sampler"), 0);
    	this.defaultShaderProgram.setUniform(this.defaultShaderProgram.getUniformLocation("normalMap"), 1);
    	
    	int start = 2;
    	for (int i = 0; i < ShadowRenderer.NUM_CASCADES; i++) {
    		this.defaultShaderProgram.setUniform(this.defaultShaderProgram.getUniformLocation("shadowMap_" + i), start + i);
    	}
    	
    	this.defaultShaderProgram.setUniform(this.defaultShaderProgram.getUniformLocation("renderShadow"), scene.isRenderShadows() ? 1 : 0);
    	
    	renderNonInstancedMeshes(scene);
    	
    	renderInstancedMeshes(scene, viewMatrix);
    	
    	
    	this.defaultShaderProgram.stopUse();
    }
    
    /**
     * 
     * @param viewMatrix
     * @param ambientLight
     * @param pointLights
     * @param spotLights
     * @param directionalLight
     */
    private void renderLights(Matrix4f viewMatrix, SceneLight sceneLight) {
    	
    	// TODO: Throw error if too many lights? - Oskar Mendel 2017-06-28
    	
    	this.defaultShaderProgram.setUniform(this.defaultShaderProgram.getUniformLocation("ambientLight"), sceneLight.getAmbientLight());
    	this.defaultShaderProgram.setUniform(this.defaultShaderProgram.getUniformLocation("specularPower"), 10f);
        
        // Process pointlights
    	PointLight[] pointLights = sceneLight.getPointLights();
        int lights = pointLights != null ? pointLights.length : 0;
        for (int i = 0; i < lights; i++) {
        	PointLight currPointLight = new PointLight(pointLights[i]);
			Vector3f lightPos = currPointLight.getPosition();
			
			Vector4f aux = new Vector4f(lightPos, 1);
			aux.mul(viewMatrix);
			lightPos.x = aux.x;
			lightPos.y = aux.y;
			lightPos.z = aux.z;
			
			this.defaultShaderProgram.setUniform("pointLights", currPointLight, i);
        }
        
        // Process spotLights
        SpotLight[] spotLights = sceneLight.getSpotLights();
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
			
			this.defaultShaderProgram.setUniform("spotLights", currSpotLight, i);
        }
        
        // Process directionalLight
        DirectionalLight currDirLight = new DirectionalLight(sceneLight.getDirectionalLight());
		Vector4f dir = new Vector4f(currDirLight.getDirection(), 0);
		dir.mul(viewMatrix);
		currDirLight.setDirection(new Vector3f(dir.x, dir.y, dir.z));
		this.defaultShaderProgram.setUniform("directionalLight", currDirLight);
    }
    
    /**
     * 
     * @param scene
     */
    private void renderNonInstancedMeshes(Scene scene) {
    	this.defaultShaderProgram.setUniform(this.defaultShaderProgram.getUniformLocation("isInstanced"), 0);
    	
    	// Render each mesh
    	Map<Mesh, List<Entity>> mapMeshes = scene.getEntityMeshes();
    	for (Mesh mesh : mapMeshes.keySet()) {
    		 this.defaultShaderProgram.setUniform("material", mesh.getMaterial());
    		 
    		 Texture texture = mesh.getMaterial().getTexture();
    		 if (texture != null) {
    			 this.defaultShaderProgram.setUniform(this.defaultShaderProgram.getUniformLocation("numCols"), texture.getNumCols());
    			 this.defaultShaderProgram.setUniform(this.defaultShaderProgram.getUniformLocation("numRows"), texture.getNumRows());
    		 }
    		 
    		 this.shadowRenderer.bindTextures(GL_TEXTURE2);
    		 
    		 mesh.renderList(mapMeshes.get(mesh), (Entity entity) -> {
    			 this.defaultShaderProgram.setUniform(this.defaultShaderProgram.getUniformLocation("selectedNonInstanced"), entity.isSelected() ? 1.0f : 0.0f);
    			 Matrix4f modelMatrix = this.transformation.buildModelMatrix(entity);
    			 this.defaultShaderProgram.setUniform(this.defaultShaderProgram.getUniformLocation("modelNonInstancedMatrix"), modelMatrix);
    			 if (entity instanceof AnimatedEntity) {
    				// TODO: If its animated render the shadows differently.
 					// Oskar Mendel - 2017-07-03
    			 }
    		 });
    	}
    }
    
    /**
     * 
     * @param scene
     * @param viewMatrix
     */
    private void renderInstancedMeshes(Scene scene, Matrix4f viewMatrix) {
    	
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
        if (this.shadowRenderer != null) {
        	this.shadowRenderer.delete();
        }
        
        if (this.skyBoxShaderProgram != null) {
        	this.skyBoxShaderProgram.delete();
        }
        
        if (this.defaultShaderProgram != null) {
        	this.defaultShaderProgram.delete();
        }
    }
}

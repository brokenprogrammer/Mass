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

package me.oskarmendel.mass.gfx.shadow;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.joml.Matrix4f;

import me.oskarmendel.mass.core.Camera;
import me.oskarmendel.mass.core.Scene;
import me.oskarmendel.mass.entity.Entity;
import me.oskarmendel.mass.entity.animated.AnimatedEntity;
import me.oskarmendel.mass.gfx.InstancedMesh;
import me.oskarmendel.mass.gfx.Mesh;
import me.oskarmendel.mass.gfx.Renderer;
import me.oskarmendel.mass.gfx.SceneLight;
import me.oskarmendel.mass.gfx.Screen;
import me.oskarmendel.mass.gfx.Transformation;
import me.oskarmendel.mass.gfx.light.DirectionalLight;
import me.oskarmendel.mass.gfx.shader.Shader;
import me.oskarmendel.mass.gfx.shader.ShaderProgram;

import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL30.glFramebufferTexture2D;
import static org.lwjgl.opengl.GL30.glBindFramebuffer;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL30.GL_FRAMEBUFFER;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL30.GL_DEPTH_ATTACHMENT;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL13.GL_TEXTURE2;


/**
 *
 * @author Oskar Mendel
 * @version 0.00.00
 * @name ShadowRenderer.java
 */
public class ShadowRenderer {
	
	/**
	 * 
	 */
	public static final int NUM_CASCADES = 3;
	
	/**
	 * 
	 */
	public static final float[] CASCADE_SPLITS = new float[]{
			Screen.Z_FAR / 20.0f, 
			Screen.Z_FAR / 10.0f, 
			Screen.Z_FAR
	};
	
	/**
	 * 
	 */
	private ShaderProgram depthShaderProgram;
	
	/**
	 * 
	 */
	private List<ShadowCascade> shadowCascades;
	
	/**
	 * 
	 */
	private ShadowBuffer shadowBuffer;
	
	/**
	 * 
	 */
	private final List<Entity> filteredEntities;
	
	/**
	 * 
	 */
	public ShadowRenderer() {
		this.filteredEntities = new ArrayList<>();
	}
	
	/**
	 * 
	 */
	public void init() throws Exception {
		shadowBuffer = new ShadowBuffer();
		this.shadowCascades = new ArrayList<>();
		
		setupDepthShader();
		
		float zNear = Screen.Z_NEAR;
		for (int i = 0; i < NUM_CASCADES; i++) {
			ShadowCascade shadowCascade = new ShadowCascade(zNear, CASCADE_SPLITS[i]);
			this.shadowCascades.add(shadowCascade);
			zNear = CASCADE_SPLITS[i];
		}
	}
	
	/**
	 * Sets up the depth shader program for this ShadowRenderer by
	 * loading and linking the vertex and fragment shader.
	 */
	private void setupDepthShader() {
		depthShaderProgram = new ShaderProgram();
		
		Shader vertexShader = Shader.loadShader(GL_VERTEX_SHADER, "src/main/resources/shaders/depth.vert");
		Shader fragmentShader = Shader.loadShader(GL_FRAGMENT_SHADER, "src/main/resources/shaders/depth.frag");
		
		depthShaderProgram.attachShader(vertexShader);
		depthShaderProgram.attachShader(fragmentShader);
		depthShaderProgram.link();
		
		depthShaderProgram.createUniform("isInstanced");
		depthShaderProgram.createUniform("modelNonInstancedMatrix");
		depthShaderProgram.createUniform("lightViewMatrix");
		depthShaderProgram.createUniform("jointsMatrix");
		depthShaderProgram.createUniform("orthoProjectionMatrix");
	}
	
	/**
	 * 
	 * @param screen
	 * @param viewMatrix
	 * @param scene
	 */
	public void update(Screen screen, Matrix4f viewMatrix, Scene scene) {
		SceneLight sceneLight = scene.getSceneLight();
		DirectionalLight directionalLight = sceneLight != null ? sceneLight.getDirectionalLight() : null;
		
		for (int i = 0; i < NUM_CASCADES; i++) {
			ShadowCascade shadowCascade = shadowCascades.get(i);
			shadowCascade.update(screen, viewMatrix, directionalLight);
		}
	}
	
	/**
	 * 
	 * @param screen
	 * @param scene
	 * @param camera
	 * @param transformation
	 * @param renderer
	 */
	public void render(Screen screen, Scene scene, Camera camera, Transformation transformation, Renderer renderer) {
		update(screen, camera.getViewMatrix(), scene);
		
		glBindFramebuffer(GL_FRAMEBUFFER, shadowBuffer.getDepthMapFBO());
		glViewport(0, 0, ShadowBuffer.SHADOW_MAP_WIDTH, ShadowBuffer.SHADOW_MAP_HEIGHT);
		glClear(GL_DEPTH_BUFFER_BIT);
		
		depthShaderProgram.use();
		
		for (int i = 0; i < NUM_CASCADES; i++) {
			ShadowCascade shadowCascade = shadowCascades.get(i);
			
			depthShaderProgram.setUniform(("orthoProjectionMatrix"), shadowCascade.getOrthoProjectionMatrix());
			depthShaderProgram.setUniform(("lightViewMatrix"), shadowCascade.getLightViewMatrix());
			
			glFramebufferTexture2D(GL_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, GL_TEXTURE_2D, shadowBuffer.getDepthMapTexture().getIds()[i], 0);
			
			renderNonInstancedMeshes(scene, transformation);
			
			renderInstancedMeshes(scene, transformation);
		}
		
		depthShaderProgram.stopUse();
		glBindFramebuffer(GL_FRAMEBUFFER, 0);
	}
	
	/**
	 * 
	 * @param scene
	 * @param transformation
	 */
	public void renderNonInstancedMeshes(Scene scene, Transformation transformation) {
		depthShaderProgram.setUniform(("isInstanced"), 0);
		
		Map<Mesh, List<Entity>> mapMeshes = scene.getEntityMeshes();
		
		for (Mesh mesh : mapMeshes.keySet()) {
			mesh.renderList(mapMeshes.get(mesh), (Entity entity) -> {
				Matrix4f modelMatrix = transformation.buildModelMatrix(entity);
				depthShaderProgram.setUniform(("modelNonInstancedMatrix"), modelMatrix);
				if (entity instanceof AnimatedEntity) {
					// TODO: If its animated render the shadows differently.
					// Oskar Mendel - 2017-07-01
				}
			});
		}
	}
	
	/**
	 * 
	 * @param scene
	 * @param transformation
	 */
	public void renderInstancedMeshes(Scene scene, Transformation transformation) {
		depthShaderProgram.setUniform(("isInstanced"), 1);
		
		Map<InstancedMesh, List<Entity>> mapMeshes = scene.getEntityInstancedMeshes();
		for (InstancedMesh mesh : mapMeshes.keySet()) {
			this.filteredEntities.clear();
			
			for (Entity e : mapMeshes.get(mesh)) {
				if (e.insideFrustrum()) {
					this.filteredEntities.add(e);
				}
			}
			
			bindTextures(GL_TEXTURE2);
			
			mesh.renderListInstanced(this.filteredEntities, transformation, null);
		}
	}
	
	/**
	 * 
	 * @return
	 */
	public List<ShadowCascade> getShadowCascades() {
		return this.shadowCascades;
	}
	
	/**
	 * 
	 * @param start
	 */
	public void bindTextures(int start) {
		this.shadowBuffer.bindTextures(start);
	}
	
	/**
	 * 
	 */
	public void delete() {
		if (shadowBuffer != null) {
			shadowBuffer.delete();
		}
		
		if (depthShaderProgram != null) {
			depthShaderProgram.delete();
		}
	}
}

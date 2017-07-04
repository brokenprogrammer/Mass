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

package me.oskarmendel.mass.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.oskarmendel.mass.entity.Entity;
import me.oskarmendel.mass.entity.SkyBox;
import me.oskarmendel.mass.gfx.InstancedMesh;
import me.oskarmendel.mass.gfx.Mesh;
import me.oskarmendel.mass.gfx.particle.ParticleEmitter;
import me.oskarmendel.mass.gfx.weather.Fog;

/**
 * Scene class that gathers all content of a scene in the game.
 *
 * @author Oskar Mendel
 * @version 0.00.00
 * @name Scene.java
 */
public class Scene {
	
	/**
	 * Map to keep track of all the shared Meshes among the entities.
	 */
	private final Map<Mesh, List<Entity>> meshMap;
	
	/**
	 * Map to keep track of all the shared Instanced Meshes among the entities.
	 */
	private final Map<InstancedMesh, List<Entity>> instancedMeshMap;
	
	/**
	 * Container for all the entities in this Scene.
	 */
	private Entity[] entities;
	
	/**
	 * The SkyBox to be used in this Scene.
	 */
	private SkyBox skyBox;
	
	/**
	 * Container for all the lights in this Scene.
	 */
	private SceneLight sceneLight;
	
	/**
	 * The fog weather modifier for this Scene.
	 */
	private Fog fog;
	
	/**
	 * Value to check if the Scene should render shadows or not.
	 */
	private boolean renderShadows;
	
	/**
	 * Container for all the particle emitters of this Scene.
	 */
	private ParticleEmitter[] particleEmitters;
	
	/**
	 * Default constructor for a scene. Initializes
	 * all the fields of this scene.
	 */
	public Scene() {
		meshMap = new HashMap<>();
		instancedMeshMap = new HashMap<>();
		
		renderShadows = true;
	}
	
	/**
	 * Getter for all the shared Meshes among the Entities in
	 * this Scene.
	 * 
	 * @return - All the Meshes in this Scene.
	 */
	public Map<Mesh, List<Entity>> getEntityMeshes() {
		return meshMap;
	}
	
	/**
	 * Getter for all the shared Instanced Meshes among the 
	 * Entities in this Scene.
	 * 
	 * @return - All the Instanced Meshes in this Scene.
	 */
	public Map<InstancedMesh, List<Entity>> getEntityInstancedMeshes() {
		return instancedMeshMap;
	}
	
	/**
	 * Populates the maps of shared Meshes by walking through all the
	 * Meshes among the Entities.
	 * 
	 * @param entities - All Entities to walk through Meshes of.
	 */
	public void setEntityMeshes(Entity[] entities) {
		int len = entities.length;
		
		for (int i = 0; i < len; i++) {
			Entity e = entities[i];
			Mesh[] meshes = e.getMeshes();
			
			for (Mesh m : meshes) {
				boolean instanced = m instanceof InstancedMesh;
				
				List<Entity> list = instanced ? instancedMeshMap.get(m) : meshMap.get(m);
				if (list == null) {
					list = new ArrayList<>();
					
					if (instanced) {
						instancedMeshMap.put((InstancedMesh) m, list);
					} else {
						meshMap.put(m, list);
					}
				}
				list.add(e);
			}
		}
	}
	
	/**
	 * Getter for the container of entities stored in this Scene.
	 * 
	 * @return - The array of entities stored in this Scene.
	 */
	public Entity[] getEntities() {
		return this.entities;
	}
	
	/**
	 * Setter for the array of entities stored in this Scene.
	 * 
	 * @param entities - Array of entities to set for this Scene.
	 */
	public void setEntities(Entity[] entities) {
		this.entities = entities;
	}
	
	/**
	 * Getter for the SkyBox of this Scene.
	 * 
	 * @return - The SkyBox of this Scene.
	 */
	public SkyBox getSkyBox() {
		return this.skyBox;
	}
	
	/**
	 * Setter for the SkyBox of this Scene.
	 * 
	 * @param skyBox - SkyBox to set to this Scene.
	 */
	public void setSkyBox(SkyBox skyBox) {
		this.skyBox = skyBox;
	}
	
	/**
	 * Getter for the container of lights for this Scene.
	 * 
	 * @return - The SceneLight containing all the lights for this Scene.
	 */
	public SceneLight getSceneLight() {
		return sceneLight;
	}
	
	/**
	 * Setter for the SceneLight of this Scene.
	 * 
	 * @param sceneLight - The SceneLight container to set.
	 */
	public void setSceneLight(SceneLight sceneLight) {
		this.sceneLight = sceneLight;
	}
	
	/**
	 * Getter for the Fog value of this Scene.
	 * 
	 * @return - The Fog value of this Scene.
	 */
	public Fog getFog() {
		return fog;
	}
	
	/**
	 * Setter for the Fog value of this Scene.
	 * 
	 * @param fog - The Fog value to set.
	 */
	public void setFog(Fog fog) {
		this.fog = fog;
	}
	
	/**
	 * Getter for the value to check if the Scene is rendering shadows
	 * or not.
	 * 
	 * @return - True of Scene should render shadows; False otherwise.
	 */
	public boolean isRenderShadows() {
		return this.renderShadows;
	}
	
	/**
	 * Setter for the Render Shadows value of this Scene.
	 * 
	 * @param renderShadows - The renderShadows value to set.
	 */
	public void setRenderShadows(boolean renderShadows) {
		this.renderShadows = renderShadows;
	}
	
	/**
	 * Getter for all the ParticleEmitters in this Scene.
	 * 
	 * @return - The ParticleEmitters within this Scene.
	 */
	public ParticleEmitter[] getParticleEmitters() {
		return this.particleEmitters;
	}
	
	/**
	 * Setter for the ParticleEmitters of this Scene.
	 * 
	 * @param particleEmitters - The ParticleEmitters to set.
	 */
	public void setParticleEmitters(ParticleEmitter[] particleEmitters) {
		this.particleEmitters = particleEmitters;
	}
	
	/**
	 * Deletes this Scene by deleting all the stored Meshes and 
	 * ParticleEmitters within it.
	 */
	public void delete() {
		for (Mesh mesh : meshMap.keySet()) {
			mesh.delete();
		}
		
		for (InstancedMesh mesh : instancedMeshMap.keySet()) {
			mesh.delete();
		}
		
		if (this.particleEmitters != null) {
			for (ParticleEmitter p : particleEmitters) {
				p.delete();
			}
		}
	}
}

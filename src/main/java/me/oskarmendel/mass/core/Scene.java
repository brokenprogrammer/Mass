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

import me.oskarmendel.mass.entity.Entity;
import me.oskarmendel.mass.entity.SkyBox;
import me.oskarmendel.mass.gfx.SceneLight;

/**
 * Scene class that gathers all content of a scene in the game.
 *
 * @author Oskar Mendel
 * @version 0.00.00
 * @name Scene.java
 */
public class Scene {
	
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
}

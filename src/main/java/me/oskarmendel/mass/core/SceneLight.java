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

import org.joml.Vector3f;

import me.oskarmendel.mass.gfx.light.DirectionalLight;
import me.oskarmendel.mass.gfx.light.PointLight;
import me.oskarmendel.mass.gfx.light.SpotLight;

/**
 * Class that gathers all the light to be stored in a scene.
 *
 * @author Oskar Mendel
 * @version 0.00.00
 * @name SceneLight.java
 */
public class SceneLight {
	
	/**
	 * Ambient light of the scene light.
	 */
	private Vector3f ambientLight;
	
	/**
	 * Array of all the PointLights.
	 */
	private PointLight[] pointLights;
	
	/**
	 * Array of all the SpotLights.
	 */
	private SpotLight[] spotLights;
	
	/**
	 * Directional light for the scene light.
	 */
	private DirectionalLight directionalLight;
	
	/**
	 * Getter for the ambient light of this SceneLight.
	 * 
	 * @return - Ambient light of this SceneLight.
	 */
	public Vector3f getAmbientLight() {
		return this.ambientLight;
	}
	
	/**
	 * Setter for the ambient light of this SceneLight.
	 * 
	 * @param ambientLight - Ambient light value to set.
	 */
	public void setAmbientLight(Vector3f ambientLight) {
		this.ambientLight = ambientLight;
	}

	/**
	 * Getter for the array of PointLights in this SceneLight.
	 * 
	 * @return - The pointLights in this SceneLight.
	 */
	public PointLight[] getPointLights() {
		return pointLights;
	}

	/**
	 * Setter for the PointLights of this SceneLight.
	 * 
	 * @param pointLights - The pointLights to set to this SceneLight.
	 */
	public void setPointLights(PointLight[] pointLights) {
		this.pointLights = pointLights;
	}

	/**
	 * Getter for the array of SpotLights in this SceneLight.
	 * 
	 * @return - The spotLights in this SceneLight
	 */
	public SpotLight[] getSpotLights() {
		return spotLights;
	}

	/**
	 * Setter for the SpotLights of this SceneLight.
	 * 
	 * @param spotLights - the spotLights to set to this SceneLight.
	 */
	public void setSpotLights(SpotLight[] spotLights) {
		this.spotLights = spotLights;
	}

	/**
	 * Getter for the DirectionalLight of this SceneLight.
	 * 
	 * @return - The directionalLight in this SceneLight.
	 */
	public DirectionalLight getDirectionalLight() {
		return directionalLight;
	}

	/**
	 * Setter for the DirectionalLight of this SceneLight.
	 * 
	 * @param directionalLight - The directionalLight to set to this SceneLight.
	 */
	public void setDirectionalLight(DirectionalLight directionalLight) {
		this.directionalLight = directionalLight;
	}
}

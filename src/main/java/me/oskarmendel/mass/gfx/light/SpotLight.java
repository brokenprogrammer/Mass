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

package me.oskarmendel.mass.gfx.light;

import org.joml.Vector3f;

/**
 * This class represents a light model that sends out light
 * in a cone.
 *
 * @author Oskar Mendel
 * @version 0.00.00
 * @name SpotLight.java
 */
public class SpotLight {
	
	/**
	 * The spotlight is based upon a pointlight
	 * and cuts of the light the pointlight emits
	 * into a cone.
	 */
	private PointLight pointLight;
	
	/**
	 * Direction of the cone of this spotlight.
	 */
	private Vector3f coneDirection;
	
	/**
	 * Cutoff angle of this SpotLight.
	 */
	private float cutOff;
	
	/**
	 * Creates a new SpotLight with the specified PointLight, cone direction
	 * and cutOff angle.
	 * 
	 * @param pointLight - PointLight to use for this SpotLight.
	 * @param coneDirection - Cone direction.
	 * @param cutOff - Cutoff angle.
	 */
	public SpotLight(PointLight pointLight, Vector3f coneDirection, float cutOff) {
		this.pointLight = pointLight;
		this.coneDirection = coneDirection;
		this.cutOff = cutOff;
	}
	
	/**
	 * Creates a new SpotLight using the values from the
	 * specified SpotLight.
	 * 
	 * @param spotLight - SpotLight to use values from.
	 */
	public SpotLight(SpotLight spotLight) {
		this(new PointLight(spotLight.getPointLight()), 
				new Vector3f(spotLight.getConeDirection()), spotLight.getCutOff());
	}

	/**
	 * Getter for the pointlight of this SpotLight.
	 * 
	 * @return - The pointLight of this SpotLight.
	 */
	public PointLight getPointLight() {
		return pointLight;
	}

	/**
	 * Setter for the pointlight of this SpotLight.
	 * 
	 * @param pointLight - The pointLight value to set.
	 */
	public void setPointLight(PointLight pointLight) {
		this.pointLight = pointLight;
	}

	/**
	 * Getter for the cone direction of this SpotLight.
	 * 
	 * @return - The coneDirection of this SpotLight.
	 */
	public Vector3f getConeDirection() {
		return coneDirection;
	}

	/**
	 * Setter for the cone direction of this SpotLight.
	 * 
	 * @param coneDirection - The coneDirection value to set.
	 */
	public void setConeDirection(Vector3f coneDirection) {
		this.coneDirection = coneDirection;
	}

	/**
	 * Getter for the cutoff of this SpotLight.
	 * 
	 * @return - The cutOff of this SpotLight.
	 */
	public float getCutOff() {
		return cutOff;
	}

	/**
	 * Setter for the cutoff value of this SpotLight.
	 * 
	 * @param cutOff - The cutOff value to set.
	 */
	public void setCutOff(float cutOff) {
		this.cutOff = cutOff;
	}
}

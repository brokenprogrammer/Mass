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

import me.oskarmendel.mass.gfx.Color;
import org.joml.Vector3f;

/**
 * This class represents a light model that we recieve from the sun
 * this light model sends out parallel rays from a specific direction.
 *
 * @author Oskar Mendel
 * @version 0.00.00
 * @name DirectionalLight.java
 */
public class DirectionalLight {

    /**
     * The color of this light.
     */
    private Color color;

    /**
     * The direction of this light.
     */
    private Vector3f direction;

    /**
     * The intensity of this light. Range 0 - 1.
     */
    private float intensity;

    /**
     * Creates a new DirectionalLight with the specified color,
     * direction and intensity.
     *
     * @param color - Color of this light.
     * @param direction - Direction of this light.
     * @param intensity - Intensity of this light.
     */
    public DirectionalLight(Color color, Vector3f direction, float intensity) {
        this.color = color;
        this.direction = direction;
        this.intensity = intensity;
    }

    /**
     * Creates a new DirectionalLight using the values from the
     * specified DirectionalLight.
     *
     * @param directionalLight - DirectionalLight to use values from.
     */
    public DirectionalLight(DirectionalLight directionalLight) {
        this(directionalLight.getColor(), new Vector3f(directionalLight.getDirection()),
                directionalLight.getIntensity());
    }

    /**
     * Getter for the color of this DirectionalLight.
     *
     * @return - The color of this DirectionalLight.
     */
    public Color getColor() {
        return color;
    }

    /**
     * Setter for the color of this DirectionalLight.
     *
     * @param color - The color value to set for this DirectionalLight.
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Getter for the direction of this DirectionalLight.
     *
     * @return - The direction of this DirectionalLight.
     */
    public Vector3f getDirection() {
        return direction;
    }

    /**
     * Setter for the direction of this DirectionalLight.
     *
     * @param direction - Direction value to set for this DirectionalLight.
     */
    public void setDirection(Vector3f direction) {
        this.direction = direction;
    }

    /**
     * Getter for the intensity of this DirectionalLight.
     *
     * @return - The intensity of this DirectionalLight.
     */
    public float getIntensity() {
        return intensity;
    }

    /**
     * Setter for the intensity of this DirectionalLight.
     *
     * @param intensity - The intensity value to set for this DirectionalLight.
     */
    public void setIntensity(float intensity) {
        this.intensity = intensity;
    }
}


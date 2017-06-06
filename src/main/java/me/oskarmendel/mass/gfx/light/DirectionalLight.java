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
     * The position of tihs light.
     */
    private Vector3f position;

    /**
     * The intensity of this light. Range 0 - 1.
     */
    private float intensity;

    /**
     * Creates a new DirectionalLight with the specified color,
     * position and intensity.
     *
     * @param color - Color of this light.
     * @param position - Position of this light.
     * @param intensity - Intensity of this light.
     */
    public DirectionalLight(Color color, Vector3f position, float intensity) {
        this.color = color;
        this.position = position;
        this.intensity = intensity;
    }

    /**
     * Creates a new DirectionalLight using the values from the
     * specified DirectionalLight.
     *
     * @param directionalLight - DirectionalLight to use values from.
     */
    public DirectionalLight(DirectionalLight directionalLight) {
        this(directionalLight.getColor(), new Vector3f(directionalLight.getPosition()),
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
     * Getter for the position of this DirectionalLight.
     *
     * @return - The position of this DirectionalLight.
     */
    public Vector3f getPosition() {
        return position;
    }

    /**
     * Setter for the position of this DirectionalLight.
     *
     * @param position - Position value to set for this DirectionalLight.
     */
    public void setPosition(Vector3f position) {
        this.position = position;
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


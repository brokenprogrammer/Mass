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
 * This class represents a light model that sends out light
 * in all directions from a point in space.
 *
 * @author Oskar Mendel
 * @version 0.00.00
 * @name PointLight.java
 */
public class PointLight {

    /**
     * The color of this light.
     */
    private Color color;

    /**
      The position of this light.
     */
    private Vector3f position;

    /**
     * The intensity of this light. Range 0 - 1.
     */
    private float intensity;

    /**
     * The attenuation of this light.
     */
    private Attenuation attenuation;

    /**
     * Creates a new PointLight with the specified color, position and intensity.
     * Sets the attenuation to a default value of constant to 1 and the rest to 0.
     *
     * @param color - Color of this light.
     * @param position - Position of this light.
     * @param intensity - Intensity of this light.
     */
    public PointLight(Color color, Vector3f position, float intensity) {
        attenuation = new Attenuation(1, 0 , 0);
        this.color = color;
        this.position = position;
        this.intensity = intensity;
    }

    /**
     * Creates a new PointLight with the specified color, position, intensity
     * and attenuation.
     *
     * @param color - Color of this light.
     * @param position - Position of this light.
     * @param intensity - Intensity of this light.
     * @param attenuation - Attenuation of this light.
     */
    public PointLight(Color color, Vector3f position, float intensity, Attenuation attenuation) {
        this.color = color;
        this.position = position;
        this.intensity = intensity;
        this.attenuation = attenuation;
    }

    /**
     * Creates a new PointLight using the values from the
     * specified PointLight.
     *
     * @param pointLight - PointLight to use values from.
     */
    public PointLight(PointLight pointLight) {
        this(pointLight.getColor(), new Vector3f(pointLight.getPosition()),
                pointLight.getIntensity(), pointLight.getAttenuation());
    }

    /**
     * Getter for the color of this PointLight.
     *
     * @return - The color of this PointLight.
     */
    public Color getColor() {
        return color;
    }

    /**
     * Setter for the color of this PointLight.
     *
     * @param color - The color value to set for this PointLight.
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Getter for the position of this PointLight.
     *
     * @return - The position of this PointLight.
     */
    public Vector3f getPosition() {
        return position;
    }

    /**
     * Setter for the position of this PointLight.
     *
     * @param position - The position value to set for this PointLight.
     */
    public void setPosition(Vector3f position) {
        this.position = position;
    }

    /**
     * Getter for the intensity of this PointLight.
     *
     * @return - The intensity of this PointLight.
     */
    public float getIntensity() {
        return intensity;
    }

    /**
     * Setter for the intensity of this PointLight.
     *
     * @param intensity - The intensity value to set to this PointLight.
     */
    public void setIntensity(float intensity) {
        this.intensity = intensity;
    }

    /**
     * Getter for the Attenuation of this PointLight.
     *
     * @return - The Attenuation of this PointLight.
     */
    public Attenuation getAttenuation() {
        return attenuation;
    }

    /**
     * Setter for the Attenuation of this PointLight.
     *
     * @param attenuation - The attenuation value to set to this PointLight.
     */
    public void setAttenuation(Attenuation attenuation) {
        this.attenuation = attenuation;
    }


    /**
     * This inner class represents the Attenuation of a point light.
     *
     * A point lights attenuation defines how bright the light should be
     * with respect to the distance of the objects.
     *
     * The attenuation is calculated using this formula:
     * 1.0 / (constant + linear * distance + exponent * distance^2).
     */
    public static class Attenuation {

        /**
         * Constant value for this Attenuation.
         */
        private float constant;

        /**
         * Linear value for this Attenuation.
         */
        private float linear;

        /**
         * Exponent value for this Attenuation.
         */
        private float exponent;

        /**
         * Creates a new Attenuation with the specified constant,
         * linear and exponent values.
         *
         * @param constant - Constant value for this Attenuation.
         * @param linear - Linear value for this Attenuation.
         * @param exponent - Exponent value for this Attenuation.
         */
        public Attenuation(float constant, float linear, float exponent) {
            this.constant = constant;
            this.linear = linear;
            this.exponent = exponent;
        }

        /**
         * Getter for the constant value of this Attenuation.
         *
         * @return - The constant value of this Attenuation.
         */
        public float getConstant() {
            return constant;
        }

        /**
         * Setter for the constant value of this Attenuation.
         *
         * @param constant - Constant value to set.
         */
        public void setConstant(float constant) {
            this.constant = constant;
        }

        /**
         * Getter for the linear value of this Attenuation.
         *
         * @return - The constant value of this Attenuation.
         */
        public float getLinear() {
            return linear;
        }

        /**
         * Setter for the linear value of this Attenuation.
         *
         * @param linear - Linear value to set.
         */
        public void setLinear(float linear) {
            this.linear = linear;
        }

        /**
         * Getter for the exponent value of this Attenuation.
         *
         * @return - The constant value of this Attenuation.
         */
        public float getExponent() {
            return exponent;
        }

        /**
         * Setter for the exponent value of this Attenuation.
         *
         * @param exponent - Exponent value to set.
         */
        public void setExponent(float exponent) {
            this.exponent = exponent;
        }
    }
}

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

import org.joml.Vector3f;
import org.joml.Vector4f;

/**
 * This class represents a RGBA color.
 *
 * @author Oskar Mendel
 * @version 0.00.00
 * @name Color.java
 */
public class Color {

    /**
     * Static color templates for commonly used colors.
     */
    public static final Color WHITE = new Color(1f, 1f, 1f);
    public static final Color BLACK = new Color(0f, 0f, 0f);

    /**
     * Red specifies the red value of the RGBA color.
     */
    private float red;

    /**
     * Blue specifies the blue value of the RGBA color.
     */
    private float blue;

    /**
     * Green specifies the green value of the RGBA color.
     */
    private float green;

    /**
     * Alpha specifies the transparency value of the RGBA color.
     */
    private float alpha;

    /**
     * Default constructor creating a new Color object
     * and sets the color to black.
     */
    public Color() {
        this(0f, 0f, 0f);
    }

    /**
     * Creates a RGB Color with the specified red, blue and green
     * values. Alpha value is set to 1.
     *
     * @param red - Red value. Range from 0f - 1f.
     * @param blue - Blue value. Range from 0f - 1f.
     * @param green - Green value. Range from 0f - 1f.
     */
    public Color(float red, float blue, float green) {
        this(red, blue, green, 1f);
    }

    /**
     * Creates a RGBA color with the specified red, blue, green
     * and alpha values.
     *
     * @param red - Red value. Range from 0f - 1f.
     * @param blue - Blue value. Range from 0f - 1f.
     * @param green - Green value. Range from 0f - 1f.
     * @param alpha - Alpha value for transparency. Range from 0f - 1f.
     */
    public Color(float red, float blue, float green, float alpha) {
        this.red = red;
        this.blue = blue;
        this.green = green;
        this.alpha = alpha;
    }

    /**
     * Getter of the red value for this color.
     *
     * @return - The red value for this color.
     */
    public float getRed() {
        return this.red;
    }

    /**
     * Setter for the red value of this color.
     *
     * @param red - Red value to set.
     */
    public void setRed(float red) {
        if (red < 0f) {
            red = 0f;
        } else if (red > 1f) {
            red = 1f;
        }

        this.red = red;
    }

    /**
     * Getter of the blue value for this color.
     *
     * @return - The blue value for this color.
     */
    public float getBlue() {
        return this.blue;
    }

    /**
     * Setter for the blue value of this color.
     *
     * @param blue - Blue value to set.
     */
    public void setBlue(float blue) {
        if (blue < 0f) {
            blue = 0f;
        } else if (blue > 1f) {
            blue = 1f;
        }

        this.blue = blue;
    }

    /**
     * Getter of the green value for this color.
     *
     * @return - The green value for this color.
     */
    public float getGreen() {
        return this.green;
    }

    /**
     * Setter for the green value of this color.
     *
     * @param green - Green value to set.
     */
    public void setGreen(float green) {
        if (green < 0f) {
            green = 0f;
        } else if (green > 1f) {
            green = 1f;
        }

        this.green = green;
    }

    /**
     * Getter of the alpha value for this color.
     *
     * @return - The alpha value for this color.
     */
    public float getAlpha() {
        return this.alpha;
    }

    /**
     * Setter for the alpha value of this color.
     *
     * @param alpha - Alpha value to set.
     */
    public void setAlpha(float alpha) {
        if (alpha < 0f) {
            alpha = 0f;
        } else if (alpha > 1f) {
            alpha = 1f;
        }

        this.alpha = alpha;
    }

    /**
     * Return this color as a 3D vector.
     * Note: the transparency value will be left out.
     * Format: (red, blue, green)
     *
     * @return - This color as a 3D vector object.
     */
    public Vector3f toVector3f() {
        return new Vector3f(red, blue, green);
    }

    /**
     * Return this color as a 4D vector.
     * Format: (red, blue, green, alpha)
     *
     * @return - This color as a 4D vector object.
     */
    public Vector4f toVector4f() {
        return new Vector4f(red, blue, green, alpha);
    }
}

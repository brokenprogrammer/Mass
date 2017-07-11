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

package me.oskarmendel.mass.hud

import me.oskarmendel.mass.gfx.Color;

/**
 * Builder class for the Crosshair class.
 *
 * @author Oskar Mendel
 * @version 0.00.00
 * @name CrosshairBuilder.java
 */
class CrosshairBuilder {
	
	/**
	 * The Alpha value of the Crosshair to build.
	 */
	var alpha : Int = 255
		private set
	
	/**
	 * The Thickness value of the Crosshair to build.
	 */
	var thickness : Float = 0.5f
		private set
	
	/**
	 * The Size value of the Crosshair to build.
	 */
	var size : Int = 5
		private set
	
	/**
	 * The Gap value of the Crosshair to build.
	 */
	var gap : Int = 0
		private set
	
	/**
	 * The Outline value of the Crosshair to build.
	 */
	var outline : Int = 0
		private set
	
	/**
	 * The Color value of the Crosshair to build.
	 */
	var color : Color = Color.WHITE
		private set
	
	/**
	 * The Dot value of the Crosshair to build.
	 */
	var dot : Boolean = false
		private set
	
	/**
     * Sets the alpha value of the Crosshair to build.
     *
     * @param alpha - Integer value to set for the alpha value.
     */
	fun alpha(alpha : Int) = apply { this.alpha = alpha }
	
	/**
     * Sets the thickness value of the Crosshair to build.
     *
     * @param thickness - Float value to set for the thickness value.
     */
	fun thickness(thickness : Float) = apply { this.thickness = thickness }
	
	/**
     * Sets the size value of the Crosshair to build.
     *
     * @param size - Integer value to set for the size value.
     */
	fun size(size : Int) = apply { this.size = size }
	
	/**
     * Sets the gap value of the Crosshair to build.
     *
     * @param gap - Integer value to set for the gap value.
     */
	fun gap(gap : Int) = apply { this.gap = gap }
	
	/**
     * Sets the outline value of the Crosshair to build.
     *
     * @param outline - Integer value to set for the outline value.
     */
	fun outline(outline : Int) = apply { this.outline = outline }
	
	/**
     * Sets the color value of the Crosshair to build.
     *
     * @param color - Color value to set for the color value.
     */
	fun color(color : Color) = apply { this.color = color }
	
	/**
     * Builds the Crosshair object and returns the newly created Crosshair object.
     *
     * @return - Crosshair object built from values entered in this CrosshairBuilder.
     */
	fun build() = Crosshair(this.alpha, this.thickness, this.size,
			this.gap, this.outline, this.color, this.dot)
}
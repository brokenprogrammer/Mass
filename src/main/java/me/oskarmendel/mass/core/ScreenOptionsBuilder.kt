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

package me.oskarmendel.mass.core

/**
 * Builder class for the ScreenOptions data class.
 *
 * @author Oskar Mendel
 * @version 0.00.00
 * @name ScreenOptionsBuilder.java
 */
class ScreenOptionsBuilder {

    /**
     * Boolean value to switch the OpenGL cull face option on or off.
     */
    var cullFace : Boolean = false
        private set

    /**
     * Boolean value to switch the OpenGL polygon mode option on or off.
     */
    var showTriangles : Boolean = false
        private set

    /**
     * Boolean value to switch the show fps option on or off.
     */
    var showFPS : Boolean = false
        private set

    /**
     * Boolean value to switch the OpenGL compatible profile option on or off.
     */
    var compatibleProfile : Boolean = false
        private set

    /**
     * Boolean value to switch the Anti-Aliasing option on or off.
     */
    var antialiasing : Boolean = false
        private set

    /**
     * Boolean value to switch the frustum culling option on or off.
     */
    var frustumCulling : Boolean = false
        private set

    /**
     * Sets the cullFace value of the ScreenOptions object to build.
     *
     * @param cullFace - Boolean value to set for the cullFace option.
     */
    fun cullFace(cullFace : Boolean) = apply { this.cullFace = cullFace }

    /**
     * Sets the showTriangles value of the ScreenOptions object to build.
     *
     * @param showTriangles - Boolean value to set for the showTriangles option.
     */
    fun showTriangles(showTriangles : Boolean) = apply { this.showTriangles = showTriangles }

    /**
     * Sets the showFPS value of the ScreenOptions object to build.
     *
     * @param showFPS - Boolean value to set for the showFPS option.
     */
    fun showFPS(showFPS : Boolean) = apply { this.showFPS = showFPS }

    /**
     * Sets the compatibleProfile value of the ScreenOptions object to build.
     *
     * @param compatibleProfile - Boolean value to set for the compatibleProfile option.
     */
    fun compatibleProfile(compatibleProfile : Boolean) = apply { this.compatibleProfile = compatibleProfile }

    /**
     * Sets the antialiasing value of the ScreenOptions object to build.
     *
     * @param antialiasing - Boolean value to set for the antialiasing option.
     */
    fun antialiasing(antialiasing : Boolean) = apply { this.antialiasing = antialiasing }

    /**
     * Sets the frustumCulling value of the ScreenOptions object to build.
     *
     * @param frustumCulling - Boolean value to set for the frustumCulling option.
     */
    fun frustumCulling(frustumCulling : Boolean) = apply { this.frustumCulling = frustumCulling }

    /**
     * Builds the ScreenOptions object and returns the newly created ScreenOptions object.
     *
     * @return - ScreenOptions object built from values entered in this ScreenOptionsBuilder.
     */
    fun build() = ScreenOptions(this.cullFace, this.showTriangles, this.showFPS,
            this.compatibleProfile, this.antialiasing, this.frustumCulling)
}
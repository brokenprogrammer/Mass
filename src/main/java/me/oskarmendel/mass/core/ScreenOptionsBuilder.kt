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
    var cullFace : Boolean = false
        private set

    var showTriangles : Boolean = false
        private set

    var showFPS : Boolean = false
        private set

    var compatibleProfile : Boolean = false
        private set

    var antialiasing : Boolean = false
        private set

    var frustumCulling : Boolean = false
        private set

    fun cullFace(cullFace : Boolean) = apply { this.cullFace = cullFace }

    fun showTriangles(showTriangles : Boolean) = apply { this.showTriangles = showTriangles }

    fun showFPS(showFPS : Boolean) = apply { this.showFPS = showFPS }

    fun compatibleProfile(compatibleProfile : Boolean) = apply { this.compatibleProfile = compatibleProfile }

    fun antialiasing(antialiasing : Boolean) = apply { this.antialiasing = antialiasing }

    fun frustumCulling(frustumCulling : Boolean) = apply { this.frustumCulling = frustumCulling }

    fun build() = ScreenOptions(this.cullFace, this.showTriangles, this.showFPS,
            this.compatibleProfile, this.antialiasing, this.frustumCulling)
}
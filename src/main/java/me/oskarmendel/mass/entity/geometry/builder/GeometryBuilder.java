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

package me.oskarmendel.mass.entity.geometry.builder;

import org.joml.Vector3f;

import me.oskarmendel.mass.entity.geometry.Geometry;

/**
 * Interface for all geometric builders containing 
 * methods shared between all the builders.
 *
 * @author Oskar Mendel
 * @version 0.00.00
 * @name GeometryBuilder.java
 */
public interface GeometryBuilder {
	
	/**
	 * Sets the position of the Geometric object to build.
	 * 
	 * @param position - Position of the geometric object.
	 * 
	 * @return This builder object.
	 */
	GeometryBuilder setPosition(Vector3f position);
	
	/**
	 * Sets the rotation of the Geometric object to build.
	 * 
	 * @param rotation - Rotation of the geometric object.
	 * 
	 * @return This builder object.
	 */
	GeometryBuilder setRotation(Vector3f rotation);
	
	/**
	 * Sets the scale of the Geometric object to build.
	 * 
	 * @param scale - Scale of the geometric object.
	 * 
	 * @return This builder object.
	 */
	GeometryBuilder setScale(float scale);
	
	/**
	 * Builds the object using all the specified values.
	 * 
	 * @return Geometric object built using the values given to the builder.
	 */
	Geometry build();
}

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

package me.oskarmendel.mass.phys;

import com.bulletphysics.dynamics.RigidBody;

/**
 * Interface for all collidable objects.
 * Classes that implements this interface will be counted as 
 * collidable and that they support collision detection.
 *
 * @author Oskar Mendel
 * @version 0.00.00
 * @name Collidable.java
 */
public interface Collidable {
	
	/**
	 * Method to initialize all physics related
	 * values.
	 */
	void initPhysics();
	
	/**
	 * Method to update logic for this collidable.
	 */
	void update();
	
	/**
	 * Getter for the collidables RigidBody.
	 * 
	 * @return RigidBody of the collidable.
	 */
	RigidBody getRigidBody();
}

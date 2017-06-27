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

package me.oskarmendel.mass.util;

/**
 * 
 *
 * @author Oskar Mendel
 * @version 0.00.00
 * @name Timer.java
 */
public class Timer {
	
	private double lastTime;
	
	/**
	 * Initializes the timer by setting the last updated time
	 * to the current time in seconds.
	 */
	public void init() {
		this.lastTime = getTime();
	}
	
	/**
	 * Getter for the current time in seconds.
	 * 
	 * @return Current time in seconds.
	 */
	public double getTime() {
		return System.nanoTime() / 1000000000;
	}
	
	/**
	 * Getter for the elapsed time since the last update.
	 * 
	 * @return Elapsed time since the last update.
	 */
	public float getElapsedTime() {
		double time = getTime();
		float elapsedTime = (float) (time - lastTime);
		
		this.lastTime = time;
		
		return elapsedTime;
	}
	
	public void updateLastTime() {
		this.lastTime = getTime();
	}
	
	/**
	 * Getter for the last updated time.
	 * 
	 * @return Last updated time.
	 */
	public double getLastTime() {
		return this.lastTime;
	}
}

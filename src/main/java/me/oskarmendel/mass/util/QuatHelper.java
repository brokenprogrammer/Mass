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

import javax.vecmath.Quat4f;

/**
 * 
 *
 * @author Oskar Mendel
 * @version 0.00.00
 * @name QuatHelper.java
 */
public class QuatHelper {
	
	public static float getPitch(Quat4f q) {
		return (float) (Math.atan2(2.0 * (q.y * q.z + q.w * q.w), 
				q.w * q.w - q.x * q.x - q.y * q.y + q.z * q.z));
	}
	
	public static float getYaw(Quat4f q) {
		return (float)(Math.asin(-2.0 * (q.x * q.z - q.w * q.y))) ;
	}
	
	public static float getRoll(Quat4f q) {
		return (float)(Math.atan2(2.0 * (q.x * q.y + q.w * q.z), 
				q.w * q.w + q.x * q.x - q.y * q.y - q.z * q.z)) ;
	}
}

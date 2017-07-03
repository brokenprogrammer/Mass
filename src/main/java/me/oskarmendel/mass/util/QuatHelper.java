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

import org.joml.Quaternionf;

/**
 * Helper class for working with Quaternions.  
 *
 * @author Oskar Mendel
 * @version 0.00.00
 * @name QuatHelper.java
 */
public class QuatHelper {
	
	/**
	 * 
	 * @param pitch
	 * @param yaw
	 * @param roll
	 * @return
	 */
	public static Quaternionf toQuat(float pitch, float yaw, float roll) {
		Quaternionf quat = new Quaternionf();
		
		float t0 = (float) Math.cos(yaw * 0.5f);
		float t1 = (float) Math.sin(yaw * 0.5f);
		float t2 = (float) Math.cos(roll * 0.5f);
		float t3 = (float) Math.sin(roll * 0.5f);
		float t4 = (float) Math.cos(pitch * 0.5f);
		float t5 = (float) Math.sin(pitch * 0.5f);
		
		quat.w = t0 * t2 * t4 + t1 * t3 * t5;
		quat.x = t0 * t3 * t4 - t1 * t2 * t5;
		quat.y = t0 * t2 * t5 + t1 * t3 * t4;
		quat.z = t1 * t2 * t4 - t0 * t3 * t5;
		
		return quat;
	}
	
	/**
	 * Getter for the pitch value of the specified quaternion.
	 * 
	 * @param q - Quaternion to retrieve pitch value from.
	 *  
	 * @return Pitch value from specified quaternion.
	 */
	public static float getPitch(Quat4f q) {
		return (float) (Math.atan2(2.0 * (q.x * q.y + q.w * q.z), q.w * q.w + q.x * q.x - q.y * q.y - q.z * q.z));
	}
	
	/**
	 * Getter for the yaw value of the specified quaternion.
	 * 
	 * @param q - Quaternion to retrieve yaw value from.
	 * 
	 * @return Yaw value from specified quaternion.
	 */
	public static float getYaw(Quat4f q) {
		return (float)(Math.asin(-2.0 * (q.x * q.z - q.w * q.y))) ;
	}
	
	/**
	 * Getter for the roll value of the specified quaternion.
	 * 
	 * @param q - Quaternion to retrieve roll value from.
	 * 
	 * @return Roll value from specified quaternion.
	 */
	public static float getRoll(Quat4f q) {
		return (float)(Math.atan2(2.0 * (q.y * q.z + q.w * q.x), q.w * q.w - q.x * q.x - q.y * q.y + q.z * q.z)) ;
	}
}

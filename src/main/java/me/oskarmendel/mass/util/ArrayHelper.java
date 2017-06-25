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

import java.util.List;

/**
 * Helper class for working with Arrays.  
 *
 * @author Oskar Mendel
 * @version 0.00.00
 * @name ArrayHelper.java
 */
public class ArrayHelper {
	
	/**
	 * Converts a list of integers into an integer array.
	 * 
	 * @param list - List of integers to convert.
	 * 
	 * @return Array containing all the integers from the list.
	 */
	public static int[] listToArrayInt(List<Integer> list) {
		int[] res = list.stream().mapToInt((Integer i) -> i).toArray();
		
		return res;
	}
	
	/**
	 * Converts a list of floats into an float array.
	 * 
	 * @param list - List of floats to convert.
	 * 
	 * @return Array containing all the floats from the list.
	 */
	public static float[] listToArrayFloat(List<Float> list) {
		float[] res = new float[list.size()];
		
		int i = 0;
		for (float f : list) {
			res[i++] = f;
		}
		
		return res;
	}
}

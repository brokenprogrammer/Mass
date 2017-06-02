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

package me.oskarmendel.mass.core;

import org.joml.Vector3f;

import java.util.Vector;

/**
 * The camera class is used to simmulate a camera moving around
 * in our 3D world and all objects are placed relative to the camera.
 *
 * @author Oskar Mendel
 * @version 0.00.00
 * @name Camera.java
 */
public class Camera {

    private final Vector3f position;

    private final Vector3f rotation;

    public Camera() {
        this.position = new Vector3f(0, 0, 0);
        this.rotation = new Vector3f(0, 0, 0);
    }

    public Camera(Vector3f position, Vector3f rotation) {
        this.position = position;
        this.rotation = rotation;
    }

    public Vector3f getPosition() {
        return this.position;
    }

    public void setPosition(float x, float y, float z) {
        this.position.x = x;
        this.position.y = y;
        this.position.z = z;
    }

    public void movePosition(float deltaX, float deltaY, float deltaZ) {
        if (deltaZ != 0) {
            position.x += (float)Math.sin(Math.toRadians(rotation.y)) * -1.0f * deltaZ;
            position.z += (float)Math.cos(Math.toRadians(rotation.y)) * deltaZ;
        }

        if (deltaX != 0) {
            position.x += (float)Math.sin(Math.toRadians(rotation.y - 90)) * -1.0f * deltaX;
            position.z += (float)Math.sin(Math.toRadians(rotation.y - 90)) *  deltaX;

        }

        position.y += deltaY;
    }

    public Vector3f getRotation() {
        return this.rotation;
    }

    public void setRotation(float x, float y, float z) {
        this.rotation.x = x;
        this.rotation.y = y;
        this.rotation.z = z;
    }

    public void moveRotation(float deltaX, float deltaY, float deltaZ) {
        this.rotation.x += deltaX;
        this.rotation.y += deltaY;
        this.rotation.z += deltaZ;
    }
}

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

import me.oskarmendel.mass.gfx.Transformation;
import org.joml.Matrix4f;
import org.joml.Vector3f;

/**
 * The camera class is used to simmulate a camera moving around
 * in our 3D world and all objects are placed relative to the camera.
 *
 * @author Oskar Mendel
 * @version 0.00.00
 * @name Camera.java
 */
public class Camera {

    /**
     * 3D vector for the Camera position.
     */
    private final Vector3f position;

    /**
     * 3D Vector for the Camera rotation.
     */
    private final Vector3f rotation;

    /**
     * The camera view matrix.
     */
    private Matrix4f viewMatrix;

    /**
     * Default constructor for the Camera class initializing
     * the position and rotation vectors to 0's.
     */
    public Camera() {
        this.position = new Vector3f(0, 0, 0);
        this.rotation = new Vector3f(0, 0, 0);
        this.viewMatrix = new Matrix4f();
    }

    /**
     * Constructor for a new Camera that uses specified position
     * and rotation vectors.
     *
     * @param position - Position vector for this camera to use.
     * @param rotation - Rotation vector for this camera to use.
     */
    public Camera(Vector3f position, Vector3f rotation) {
        this.position = position;
        this.rotation = rotation;
    }

    /**
     * Getter for the camera position.
     *
     * @return - The vector with the camera position.
     */
    public Vector3f getPosition() {
        return this.position;
    }

    /**
     * Setter for the position of the camera. Sets the position
     * vector to the specified x, y and z coordinates.
     *
     * @param x - X Coordinate.
     * @param y - Y Coordinate.
     * @param z - Z Coordinate.
     */
    public void setPosition(float x, float y, float z) {
        this.position.x = x;
        this.position.y = y;
        this.position.z = z;
    }

    /**
     *  Moves the position of the camera by specified deltaX,
     *  deltaY and deltaZ.
     *
     * @param deltaX - Value to move the x position by.
     * @param deltaY - Value to move the y position by.
     * @param deltaZ - Value to move the z position by.
     */
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

    /**
     * Getter for the rotation of the camera.
     *
     * @return - Rotation of the camera.
     */
    public Vector3f getRotation() {
        return this.rotation;
    }

    /**
     * Setter for the rotation of the camera. Sets the rotation
     * vector to the specified x, y and z.
     *
     * @param x - X Rotation.
     * @param y - Y Rotation.
     * @param z - Z Rotation.
     */
    public void setRotation(float x, float y, float z) {
        this.rotation.x = x;
        this.rotation.y = y;
        this.rotation.z = z;
    }

    /**
     * Moves the rotation of the camera by specified deltaX,
     * deltaY and deltaZ.
     *
     * @param deltaX - Value to move the x rotation by.
     * @param deltaY - Value to move the x rotation by.
     * @param deltaZ - Value to move the x rotation by.
     */
    public void moveRotation(float deltaX, float deltaY, float deltaZ) {
        this.rotation.x += deltaX;
        this.rotation.y += deltaY;
        this.rotation.z += deltaZ;
    }

    /**
     * Getter for the view matrix of the camera.
     *
     * @return - The cameras view matrix.
     */
    public Matrix4f getViewMatrix() {
        return this.viewMatrix;
    }

    /**
     * Updates the view matrix based on the position and
     * rotation of the camera.
     *
     * @return - The updated view matrix based on the position and rotation.
     */
    public Matrix4f updateViewMatrix() {
        return Transformation.updateGenericViewMatrix(position, rotation, viewMatrix);
    }
}

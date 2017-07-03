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

package me.oskarmendel.mass.entity.geometry;

import me.oskarmendel.mass.entity.Entity;
import me.oskarmendel.mass.gfx.Color;
import me.oskarmendel.mass.gfx.Material;
import me.oskarmendel.mass.gfx.Mesh;
import me.oskarmendel.mass.gfx.Texture;

import org.joml.Quaternionf;
import org.joml.Vector3f;

/**
 * This class represents a solid cube.
 *
 * @author Oskar Mendel
 * @version 0.00.00
 * @name Cube.java
 */
public class Cube extends Entity {

    /**
     * Vertex positions for a cube.
     */
    private static final float[] CUBE_POSITIONS = {
            1.0f, -1.0f, -1.0f,
            1.0f, -1.0f, 1.0f,
            -1.0f, -1.0f, 1.0f,
            -1.0f, -1.0f, -1.0f,
            1.0f, 1.0f, -1.0f,
            1.0f, 1.0f, 1.0f,
            -1.0f, 1.0f, 1.0f,
            -1.0f, 1.0f, -1.0f,
            1.0f, -1.0f, -1.0f,
            1.0f, -1.0f, -1.0f,
            1.0f, -1.0f, 1.0f,
            1.0f, -1.0f, 1.0f,
            -1.0f, -1.0f, -1.0f,
            -1.0f, -1.0f, -1.0f,
            1.0f, 1.0f, -1.0f,
            1.0f, 1.0f, -1.0f,
            -1.0f, -1.0f, 1.0f,
            -1.0f, -1.0f, 1.0f,
            1.0f, 1.0f, 1.0f,
            1.0f, 1.0f, 1.0f,
            -1.0f, 1.0f, 1.0f,
            -1.0f, 1.0f, 1.0f,
            -1.0f, 1.0f, -1.0f,
            -1.0f, 1.0f, -1.0f
    };

    /**
     * Texture coordinates for a cube.
     */
    private static final float[] CUBE_TEXTURE_COORDINATES = {
            0.5f, 1.0f,
            0.5f, 0.5f,
            1.0f, 0.5f,
            0.0f, 0.5f,
            0.0f, 0.0f,
            0.5f, 0.0f,
            0.0f, 1.0f,
            0.0002f, 1.0f,
            0.5f, 1.0f,
            1.0f, 1.0f
    };

    /**
     * Normals for a cube.
     */
    private static final float[] CUBE_NORMALS = {
            0.0f, 0.0f, -1.0f,
            0.0f, 0.0f, 1.0f,
            -1.0f, 0.0f, 0.0f,
            0.0f, 0.0f, -1.0f,
            0.0f, 0.0f, -1.0f,
            0.0f, 0.0f, 1.0f,
            -1.0f, 0.0f, 0.0f,
            0.0f, 0.0f, -1.0f,
            0.0f, -1.0f, 0.0f,
            1.0f, 0.0f, 0.0f,
            0.0f, -1.0f, 0.0f,
            1.0f, 0.0f, 0.0f,
            0.0f, -1.0f, 0.0f,
            -1.0f, 0.0f, 0.0f,
            1.0f, 0.0f, 0.0f,
            0.0f, 1.0f, 0.0f,
            0.0f, -1.0f, 0.0f,
            0.0f, 0.0f, 1.0f,
            1.0f, 0.0f, 0.0f,
            0.0f, 1.0f, 0.0f,
            0.0f, 0.0f, 1.0f,
            0.0f, 1.0f, 0.0f,
            -1.0f, 0.0f, 0.0f,
            0.0f, 1.0f, 0.0f
    };

    /**
     * Indices for a cube.
     */
    private static final int[] CUBE_INDICES = {
            10, 16, 12, 23,
            21, 19, 14, 18,
            11, 5, 20, 17,
            2, 6, 22, 0,
            3, 7, 8, 10,
            12, 15, 23, 19,
            9, 14, 11, 1,
            5, 17, 13, 2,
            22, 4, 0, 7
    };

    /**
     * Creates a new cube at the specified position
     * using the specified rotation and scale.
     *
     * @param position - Position of the cube.
     * @param rotation - Rotation of the cube.
     * @param scale - Scale of the cube.
     */
    public Cube(Vector3f position, Quaternionf rotation, float scale) {
        super();

        Mesh mesh = new Mesh(CUBE_POSITIONS, CUBE_TEXTURE_COORDINATES, CUBE_NORMALS, CUBE_INDICES);
        Material mat = new Material();

        mesh.setMaterial(mat);
        setMesh(mesh);

        setPosition(position.x, position.y, position.z);
        setRotation(rotation);
        setScale(scale);
    }

    /**
     * Creates a new cube at the specified position
     * using the specified rotation, scale and color.
     *
     * @param position - Position of the cube.
     * @param rotation - Rotation of the cube.
     * @param scale - Scale of the cube.
     * @param color - Color of the cube.
     */
    public Cube(Vector3f position, Quaternionf rotation, float scale, Color color) {
        super();

        Mesh mesh = new Mesh(CUBE_POSITIONS, CUBE_TEXTURE_COORDINATES, CUBE_NORMALS, CUBE_INDICES);
        Material mat = new Material();

        mat.setAmbientColor(color.toVector4f());
        mat.setDiffuseColor(color.toVector4f());
        mat.setSpecularColor(color.toVector4f());

        mesh.setMaterial(mat);
        setMesh(mesh);

        setPosition(position.x, position.y, position.z);
        setRotation(rotation);
        setScale(scale);
    }

    /**
     * Creates a new cube at the specified position
     * using the specified rotation, scale and texture.
     *
     * @param position - Position of the cube.
     * @param rotation - Rotation of the cube.
     * @param scale - Scale of the cube.
     * @param texture - Texture of the cube.
     */
    public Cube(Vector3f position, Quaternionf rotation, float scale, Texture texture) {
        super();

        Mesh mesh = new Mesh(CUBE_POSITIONS, CUBE_TEXTURE_COORDINATES, CUBE_NORMALS, CUBE_INDICES);
        Material mat = new Material(texture);

        mesh.setMaterial(mat);
        setMesh(mesh);

        setPosition(position.x, position.y, position.z);
        setRotation(rotation);
        setScale(scale);
    }
}

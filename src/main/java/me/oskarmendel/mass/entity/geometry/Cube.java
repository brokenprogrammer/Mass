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
     *
     */
    private static final float[] positions = {
            -0.5f,  0.5f,  0.5f,
            -0.5f, -0.5f,  0.5f,
            0.5f, -0.5f,  0.5f,
            0.5f,  0.5f,  0.5f,
            -0.5f,  0.5f, -0.5f,
            0.5f,  0.5f, -0.5f,
            -0.5f, -0.5f, -0.5f,
            0.5f, -0.5f, -0.5f

    };

    /**
     *
     */
    private static final float[] textureCoordinates = {
            0.5f, 0.0f, 0.0f,
            0.0f, 0.5f, 0.0f,
            0.0f, 0.0f, 0.5f,
            0.0f, 0.5f, 0.5f,
            0.5f, 0.0f, 0.0f,
            0.0f, 0.5f, 0.0f,
            0.0f, 0.0f, 0.5f,
            0.0f, 0.5f, 0.5f
    };

    /**
     *
     */
    private static final float[] normals = {
            0.0f, -0.5f, 0.0f,
            0.0f, 0.5f, 0.0f,
            0.5f, 0.0f, 0.0f,
            0.0f, 0.0f, 0.5f,
            -0.5f, 0.0f, 0.0f,
            0.0f, 0.0f, -0.5f
    };

    /**
     *
     */
    private static final int[] indices = {
            0, 1, 3, 3, 1, 2,
            4, 0, 3, 5, 4, 3,
            3, 2, 7, 5, 3, 7,
            6, 1, 0, 6, 0, 4,
            2, 1, 6, 2, 6, 7,
            7, 6, 4, 7, 4, 5
    };

    /**
     *
     * @param position
     * @param rotation
     * @param scale
     */
    public Cube(Vector3f position, Vector3f rotation, float scale) {
        super();

        Mesh mesh = new Mesh(positions, textureCoordinates, normals, indices);
        Material mat = new Material();

        mesh.setMaterial(mat);
        setMesh(mesh);

        setPosition(position.x, position.y, position.z);
        setRotation(rotation.x, rotation.y, rotation.z);
        setScale(scale);
    }

    /**
     *
     * @param position
     * @param rotation
     * @param scale
     * @param color
     */
    public Cube(Vector3f position, Vector3f rotation, float scale, Color color) {
        super();

        Mesh mesh = new Mesh(positions, textureCoordinates, normals, indices);
        Material mat = new Material();

        mat.setAmbientColor(color.toVector4f());
        mat.setDiffuseColor(color.toVector4f());
        mat.setSpecularColor(color.toVector4f());

        mesh.setMaterial(mat);
        setMesh(mesh);

        setPosition(position.x, position.y, position.z);
        setRotation(rotation.x, rotation.y, rotation.z);
        setScale(scale);
    }

    /**
     * 
     * @param position
     * @param rotation
     * @param scale
     * @param texture
     */
    public Cube(Vector3f position, Vector3f rotation, float scale, Texture texture) {
        super();

        Mesh mesh = new Mesh(positions, textureCoordinates, normals, indices);
        Material mat = new Material(texture);

        mesh.setMaterial(mat);
        setMesh(mesh);

        setPosition(position.x, position.y, position.z);
        setRotation(rotation.x, rotation.y, rotation.z);
        setScale(scale);
    }
}

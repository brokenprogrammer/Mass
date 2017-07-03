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

package me.oskarmendel.mass.gfx;

import org.joml.Vector4f;

/**
 * This class represents a Material used over a texture
 * to manipulate the textures color depending on the material.
 *
 * @author Oskar Mendel
 * @version 0.00.00
 * @name Material.java
 */
public class Material {

    public static final Color DEFAULT_COLOR = Color.WHITE;

    /**
     * The ambient color of this Material.
     * The ambient color defines the color of a material where it
     * is in shadow.
     */
    private Vector4f ambientColor;

    /**
     * The diffuse color of this Material.
     * The diffuse color defines the color of an object which is
     * revealed under pure white light, it is perceived as the color
     * of the object itself.
     */
    private Vector4f diffuseColor;

    /**
     * The specular color of this Material.
     * The specular color defines the color of the light of a
     * specular reflection.
     */
    private Vector4f specularColor;

    /**
     * Reflectance value for this material.
     */
    private float reflectance;

    /**
     * Texture for this material to use.
     */
    private Texture texture;
    
    /**
     * Texture normal map for this material to use.
     */
    private Texture normalMap;

    /**
     * Default constructor for creating a new Material.
     * Sets ambient, diffuse and specular color to the
     * default color of white. Sets the reflectance to zero
     * and texture to null.
     */
    public Material() {
        this.ambientColor = DEFAULT_COLOR.toVector4f();
        this.diffuseColor = DEFAULT_COLOR.toVector4f();
        this.specularColor = DEFAULT_COLOR.toVector4f();
        this.reflectance = 0;
        this.texture = null;
    }

    /**
     * Creates a new Material with the specified texture.
     * Sets ambient, diffuse and specular color to the
     * default color of white. Sets the reflectance to zero.
     *
     * @param texture - Texture of this Material.
     */
    public Material(Texture texture) {
        this.ambientColor = DEFAULT_COLOR.toVector4f();
        this.diffuseColor = DEFAULT_COLOR.toVector4f();
        this.specularColor = DEFAULT_COLOR.toVector4f();
        this.reflectance = 0;
        this.texture = texture;
    }

    /**
     * Creates a new Material with the specified texture and reflectance.
     * Sets ambient, diffuse and specular color to the
     * default color of white.
     *
     * @param texture - Texture of this Material.
     * @param reflectance - Reflectance value of this Material.
     */
    public Material(Texture texture, float reflectance) {
        this.ambientColor = DEFAULT_COLOR.toVector4f();
        this.diffuseColor = DEFAULT_COLOR.toVector4f();
        this.specularColor = DEFAULT_COLOR.toVector4f();
        this.reflectance = reflectance;
        this.texture = texture;
    }
    
    /**
     * Creates a new Material with the specified reflectance
     * and ambient, diffuse and specular colors.
     *
     * @param ambientColor - Ambient color of this Material.
     * @param diffuseColor - Diffuse color of this Material.
     * @param specularColor - Specular color of this Material.
     * @param reflectance - Reflectance value of this Material.
     */
    public Material(Vector4f ambientColor, Vector4f diffuseColor,
            Vector4f specularColor, float reflectance) {
    	
    	this.ambientColor = ambientColor;
        this.diffuseColor = diffuseColor;
        this.specularColor = specularColor;
        this.reflectance = reflectance;
        this.texture = null;
    }

    /**
     * Creates a new Material with the specified texture, reflectance
     * and ambient, diffuse and specular colors.
     *
     * @param ambientColor - Ambient color of this Material.
     * @param diffuseColor - Diffuse color of this Material.
     * @param specularColor - Specular color of this Material.
     * @param texture - Texture of this Material.
     * @param reflectance - Reflectance value of this Material.
     */
    public Material(Vector4f ambientColor, Vector4f diffuseColor,
                    Vector4f specularColor, Texture texture, float reflectance) {

        this.ambientColor = ambientColor;
        this.diffuseColor = diffuseColor;
        this.specularColor = specularColor;
        this.reflectance = reflectance;
        this.texture = texture;
    }
    
    /**
     * Creates a new Material with the specified color and reflectance.
     * 
     * @param color - Color of the Material.
     * @param reflectance - Reflectance value of this Material.
     */
    public Material(Color color, float reflectance) {
    	this(color.toVector4f(), color.toVector4f(), color.toVector4f(), null, reflectance);
    }

    /**
     * Getter for the ambient color of this Material.
     *
     * @return - The ambient color of this Material.
     */
    public Vector4f getAmbientColor() {
        return ambientColor;
    }

    /**
     * Setter for the ambient color of this Material.
     *
     * @param ambientColor - Ambient color to set.
     */
    public void setAmbientColor(Vector4f ambientColor) {
        this.ambientColor = ambientColor;
    }

    /**
     * Getter for the diffuse color of this Material.
     *
     * @return - The diffuse color of this Material.
     */
    public Vector4f getDiffuseColor() {
        return diffuseColor;
    }

    /**
     * Setter for the diffuse color of this Material.
     *
     * @param diffuseColor - Diffuse color to set.
     */
    public void setDiffuseColor(Vector4f diffuseColor) {
        this.diffuseColor = diffuseColor;
    }

    /**
     * Getter for the specular color of this Material.
     *
     * @return - The specular color of this Material.
     */
    public Vector4f getSpecularColor() {
        return specularColor;
    }

    /**
     * Setter for the specular color of this Material.
     *
     * @param specularColor - Specular color to set.
     */
    public void setSpecularColor(Vector4f specularColor) {
        this.specularColor = specularColor;
    }

    /**
     * Getter for the reflectance value of this Material.
     *
     * @return - The reflectance value of this Material.
     */
    public float getReflectance() {
        return reflectance;
    }

    /**
     * Setter for the reflectance value of this Material.
     *
     * @param reflectance - Reflectance value to set.
     */
    public void setReflectance(float reflectance) {
        this.reflectance = reflectance;
    }

    /**
     * Checks if this Material is textured and returns
     * true if it is.
     *
     * @return - True of this Material is textured, false otherwise.
     */
    public boolean isTextured() {
        return texture != null;
    }

    /**
     * Getter for the texture of this Material.
     *
     * @return - The texture of this Material.
     */
    public Texture getTexture() {
        return texture;
    }

    /**
     * Setter for the texture of this Material.
     *
     * @param texture - The texture value to set.
     */
    public void setTexture(Texture texture) {
        this.texture = texture;
    }
    
    /**
     * Checks if this Material has a normal map and 
     * returns true if it has, false otherwise.
     * 
     * @return - True if this Material has a normal map; False otherwise.
     */
    public boolean hasNormalMap() {
    	return this.normalMap != null;
    }
    
    /**
     * Getter for the normal map of this Material.
     * 
     * @return - The normal map of this Material.
     */
    public Texture getNormalMap() {
    	return this.normalMap;
    }
    
    /**
     * Setter for the normal map of this Material.
     * 
     * @param normalMap - The normal map value to set.
     */
    public void setNormalMap(Texture normalMap) {
    	
    }
}

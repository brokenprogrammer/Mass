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

package me.oskarmendel.mass.entity;

import me.oskarmendel.mass.gfx.Mesh;
import org.joml.Vector3f;

/**
 * This class represents an Entity in the game.
 * It is meant to extend this class to represent entities in the game.
 *
 * An entity is a way to re-use game models for many game entities.
 * One VAO and a set of VBOs should be loaded for each Entity.
 *
 * @author Oskar Mendel
 * @version 0.00.00
 * @name Entity.java
 */
public abstract class Entity {
	
	/**
	 * Boolean to represent if the entity is selected or not.
	 */
	private boolean selected;
	
    /**
     * The mesh for this Entity.
     */
    private Mesh[] meshes;

    /**
     * The position for this Entity.
     */
    private final Vector3f position;

    /**
     * The scale of this Entity.
     */
    private float scale;

    /**
     * The rotation of this Entity.
     */
    private final Vector3f rotation;
    
    /**
     * 
     */
    private int texturePosition;
    
    /**
     * 
     */
    private boolean disableFrustrumCulling;
    
    /**
     * 
     */
    private boolean insideFrustrumCulling;

    /**
     * Default constructor for the Entity class.
     * Sets the mesh to null, position and rotation to vectors of 0s
     * and scale to 1.
     */
    protected  Entity() {
    	selected = false;
    	
        position = new Vector3f(0, 0, 0);
        rotation = new Vector3f(0, 0, 0);
        
        scale = 1;
        
        texturePosition = 0;
        insideFrustrumCulling = true;
        disableFrustrumCulling = false;
    }

    /**
     * Protected construtor for the Entity class.
     * This constructor is meant to be called first in every child class.
     *
     * @param mesh - Mesh instance this Entity should hold.
     */
    protected Entity(Mesh mesh) {
        this();
        
    	this.meshes = new Mesh[]{mesh};
    }
    
    /**
     * Protected construtor for the Entity class.
     * This constructor is meant to be called first in every child class.
     * 
     * @param meshes - Array of meshes to use for this Entity.
     */
    protected Entity(Mesh[] meshes) {
    	this();
    	
    	this.meshes = meshes;
    }
    
    /**
     * Getter for the selected value of this Entity.
     * 
     * @return - True of the Entity is selected; false otherwise.
     */
    public boolean isSelected() {
    	return this.selected;
    }
    
    /**
     * Setter for the selected value of this Entity.
     * 
     * @param selected - The selected value to set.
     */
    public void setSelected(boolean selected) {
    	this.selected = selected;
    }
    
    /**
     * Getter for the position of this Entity.
     *
     * @return - Position of this Entity.
     */
    public Vector3f getPosition() {
        return this.position;
    }

    /**
     * Setter for the position of this Entity. Sets the position
     * vector to the specified x, y and z coordinates.
     *
     * @param x - X coordinate to set.
     * @param y - Y coordinate to set.
     * @param z - Z coordinate to set.
     */
    public void setPosition(float x, float y, float z) {
        this.position.x = x;
        this.position.y = y;
        this.position.z = z;
    }

    /**
     * Getter for the scale of this Entity.
     *
     * @return - Scale of this Entity.
     */
    public float getScale() {
        return this.scale;
    }

    /**
     * Setter for the scale of this Entity.
     *
     * @param scale - Scale value to set.
     */
    public void setScale(float scale) {
        this.scale = scale;
    }

    /**
     * Getter for the rotation of this Entity.
     *
     * @return - Rotation of this Entity.
     */
    public Vector3f getRotation() {
        return this.rotation;
    }

    /**
     * Setter for the rotation of this Entity. Sets the rotation
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
     * Getter for the Mesh instance held by this Entity.
     *
     * @return - Mesh instance for this Entity.
     */
    public Mesh getMesh() {
        return this.meshes[0];
    }
    
    /**
     * 
     * @return
     */
    public Mesh[] getMeshes() {
    	return this.meshes;
    }

    /**
     * Setter for the Mesh instance held by this Entity.
     *
     * @param mesh - Mesh to set.
     */
    public void setMesh(Mesh mesh) {
        this.meshes = new Mesh[]{mesh};
    }
    
    /**
     * 
     * @param meshes
     */
    public void setMeshes(Mesh[] meshes) {
    	this.meshes = meshes;
    }
    
    /**
     * 
     * @return
     */
    public int getTexturePos() {
    	return this.texturePosition;
    }
    
    /**
     * 
     * @param texturePosition
     */
    public void setTexturePos(int texturePosition) {
    	this.texturePosition = texturePosition;
    }
    
    /**
     * 
     * @return
     */
    public boolean insideFrustrum() {
    	return this.insideFrustrumCulling;
    }
    
    /**
     * 
     * @param insideFrustrum
     * @return
     */
    public boolean setInsideFrtustrum(boolean insideFrustrum) {
    	return this.insideFrustrumCulling = insideFrustrum;
    }
    
    /**
     * 
     * @return
     */
    public boolean isDisableFrustrumCulling() {
    	return this.disableFrustrumCulling;
    }
    
    /**
     * 
     * @param disableFrustrumCulling
     * @return
     */
    public boolean setDisableFrustrumCulling(boolean disableFrustrumCulling) {
    	return this.disableFrustrumCulling = disableFrustrumCulling;
    }
}

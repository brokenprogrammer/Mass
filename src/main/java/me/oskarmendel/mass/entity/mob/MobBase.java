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

package me.oskarmendel.mass.entity.mob;

import me.oskarmendel.mass.entity.Entity;
import me.oskarmendel.mass.gfx.Mesh;
import me.oskarmendel.mass.phys.Collidable;

/**
 * This class represents an Mob in the game.
 * It is meant to extend this class to represent mobs in the game.
 *
 * @author Oskar Mendel
 * @version 0.00.00
 * @name MobBase.java
 */
public abstract class MobBase extends Entity implements Collidable {
	
	private int health;
	private int armor;
	private boolean alive;
	
	protected MobBase(Mesh mesh) {
        super(mesh);
        
        this.health = 100;
        this.armor = 0;
        this.alive = true;
    }
	
	public int getHealth() {
		return this.health;
	}
	
	public void setHealth(int health)  {
		this.health = health;
	}
	
	public int getArmor() {
		return this.armor;
	}
	
	public void setArmor(int armor)  {
		this.armor = armor;
	}
	
	public boolean isAlive() {
		return this.alive;
	}
	
	public void giveHealth(int health) {
		this.health += health;
		
		if (this.health > 100) {
			this.health = 100;
		}
	}
	
	public void giveDamage(int damage) {
		this.health -= damage;
		
		if (this.health <= 0) {
			this.alive = false;
		}
	}
	
	public void giveArmor(int armor) {
		this.health += health;
		
		if (this.health > 100) {
			this.health = 100;
		}
	}
	
	abstract void destroy();
}

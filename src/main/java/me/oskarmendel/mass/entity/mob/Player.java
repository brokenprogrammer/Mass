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

import javax.vecmath.Matrix4f;
import javax.vecmath.Quat4f;
import javax.vecmath.Vector3f;

import com.bulletphysics.collision.dispatch.CollisionFlags;
import com.bulletphysics.collision.dispatch.CollisionObject;
import com.bulletphysics.collision.shapes.CollisionShape;
import com.bulletphysics.collision.shapes.ConvexHullShape;
import com.bulletphysics.dynamics.RigidBody;
import com.bulletphysics.dynamics.RigidBodyConstructionInfo;
import com.bulletphysics.linearmath.DefaultMotionState;
import com.bulletphysics.linearmath.Transform;
import com.bulletphysics.util.ObjectArrayList;

import me.oskarmendel.mass.gfx.Mesh;

/**
 * This class represents an Mob in the game.
 * It is meant to extend this class to represent mobs in the game.
 *
 * @author Oskar Mendel
 * @version 0.00.00
 * @name MobBase.java
 */
public class Player extends MobBase {
	
	private static int WALK_SPEED = 5;
	
	// Component manager
		// Updateable component
		// Drawable component
	// State parrent class
	
	// Health Component
	// Armor component
	// Movement component
	// Physics component
	// Animation component
	// Inventory component
	// Weapon component
	// Run state
	// Walk state
	// Attack state
	// IDLE state
	
	/**
	 * Shape of the Player entity.
	 */
	private CollisionShape collisionShape;
	
	/**
	 * RigidBody for the Player entity.
	 */
	private RigidBody rigidBody;
	
	/**
	 * MotionState for the Player entity.
	 * Bullet physics uses the MotionState to handle frame 
	 * of simulation.
	 */
	private DefaultMotionState motionState;
	
	/**
	 * The fall innertia for the Player entity.
	 */
	private Vector3f fallInertia;
	
	/**
	 * The mass of the Player entity in kilo's.
	 */
	private int mass = 1;
	
	/**
	 * Constructs a new Player using the specified Mesh.
	 * 
	 * @param mesh - - Mesh instance this Plyer should use.
	 */
	public Player(Mesh mesh) {
		super(mesh);
		
		initPhysics();
	}
	
	/**
	 * Move the position of the player by the specified deltaX,
	 * deltaY and deltaZ using the specified camera rotation.
	 * 
	 * @param deltaX - Value to move the x position by.
	 * @param deltaY - Value to move the y position by.
	 * @param deltaZ - Value to move the z position by.
	 * @param camY - Camera y rotation to use for movement.
	 */
	public void movePosition(float deltaX, float deltaY, float deltaZ, float camY) {
		
		deltaX *= WALK_SPEED;
		deltaY *= WALK_SPEED;
		deltaZ *= WALK_SPEED;
		
		Transform controllTransform = new Transform();
		this.rigidBody.getMotionState().getWorldTransform(controllTransform);
		//Vector3f position = controllTransform.origin;
		Vector3f position = new Vector3f(0, 0, 0);
		
		if (deltaZ != 0) {
            position.x += (float)Math.sin(Math.toRadians(camY)) * -1.0f * deltaZ;
            position.z += (float)Math.cos(Math.toRadians(camY)) * deltaZ;
        }

        if (deltaX != 0) {
            position.x += (float)Math.sin(Math.toRadians(camY - 90)) * -1.0f * deltaX;
            position.z += (float)Math.cos(Math.toRadians(camY - 90)) *  deltaX;

        }

        position.y += deltaY;
        
        this.rigidBody.setLinearVelocity(position);
        this.rigidBody.activate();
	}
	
	/**
	 * Moves the rotation of the player by the specified deltaX,
	 * deltaY and deltaZ.
	 * 
	 * @param x - Value to move the x rotation by.
	 * @param y - Value to move the y rotation by.
	 * @param z - Value to move the z rotation by.
	 */
	public void moveRotation(float x, float y, float z) {
		this.rigidBody.activate();
		this.rigidBody.setAngularVelocity(new Vector3f(0, -x, 0));
	}

	@Override
	public void initPhysics() {
		motionState = new DefaultMotionState(new Transform(new Matrix4f(new Quat4f(0, 0, 0, 1), new Vector3f(5, -35, -4), 1.0f)));
		
		// Construct collision shape based on the mesh vertices in the Mesh.
		ObjectArrayList<Vector3f> points = new ObjectArrayList<Vector3f>();
		float[] positions = getMesh().getPositions();
		
		for (int i = 0; i < positions.length; i+= 3) {
			Vector3f v = new Vector3f(positions[i], positions[i + 1], positions[i + 2]);
			points.add(v);
		}
		
		fallInertia = new Vector3f(0,1,0); 
		collisionShape = new ConvexHullShape(points);
		collisionShape.calculateLocalInertia(mass,fallInertia);
		
		// Construct the RigidBody.
		RigidBodyConstructionInfo rigidBodyCI = new RigidBodyConstructionInfo(mass, motionState, collisionShape, fallInertia); 
		rigidBody = new RigidBody(rigidBodyCI);
		rigidBody.setCollisionFlags(rigidBody.getCollisionFlags() | CollisionFlags.CHARACTER_OBJECT);
		rigidBody.setActivationState(CollisionObject.DISABLE_DEACTIVATION);
		//rigidBody.setDamping(0.999f, 1);
	}

	@Override
	public void updatePhysics() {
		Transform t = new Transform();
		this.rigidBody.getWorldTransform(t);
		Vector3f v = t.origin;
		Quat4f r = new Quat4f(); 
		r = t.getRotation(r);

		this.setPosition(v.x, v.y, v.z);
		this.setRotation(r.w, r.x, r.y, r.z);
	}

	@Override
	public RigidBody getRigidBody() {
		return this.rigidBody;
	}

	@Override
	void destroy() {
		// TODO Auto-generated method stub
		
	}
}

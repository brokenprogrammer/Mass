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

package me.oskarmendel.mass.phys;

import javax.vecmath.Vector3f;

import com.bulletphysics.collision.broadphase.BroadphaseInterface;
import com.bulletphysics.collision.broadphase.DbvtBroadphase;
import com.bulletphysics.collision.dispatch.CollisionDispatcher;
import com.bulletphysics.collision.dispatch.DefaultCollisionConfiguration;
import com.bulletphysics.dynamics.DiscreteDynamicsWorld;
import com.bulletphysics.dynamics.RigidBody;
import com.bulletphysics.dynamics.constraintsolver.ConstraintSolver;
import com.bulletphysics.dynamics.constraintsolver.SequentialImpulseConstraintSolver;

/**
 * This class manages the physics of the game world.
 *
 * @author Oskar Mendel
 * @version 0.00.00
 * @name PhysicsSpace.java
 */
public class PhysicsSpace {

	//private static final int MAX_PROXIES = 1024;
	
	//private Vector3f worldAABBMin = new Vector3f(-1000, -1000, -1000);
	//private Vector3f worldAABBMax = new Vector3f(1000, 1000, 1000);
	
	/**
	 * Container for the JBullet physics world.
	 */
	private DiscreteDynamicsWorld dynamicsWorld;
	
	/**
	 * Broadphase finds out which objects that could be colliding
	 * so that only those objects are checked.
	 */
	private BroadphaseInterface broadPhase;

	/**
	 * Configuration for the collision.
	 */
	private DefaultCollisionConfiguration collisionConfiguration;

	/**
	 * The object that finds out if objects are colliding.
	 */
	private CollisionDispatcher dispatcher;

	/**
	 * Solves constraints.
	 */
	private ConstraintSolver solver;
	
	// Ground shape to simulate the ground of the physics space.
	//private CollisionShape groundShape;
	
	/**
	 * 
	 */
	public PhysicsSpace() {
		// TODO: Comments on stuff.
		broadPhase = new DbvtBroadphase();
		collisionConfiguration = new DefaultCollisionConfiguration();
		dispatcher = new CollisionDispatcher(collisionConfiguration);
		
		solver = new SequentialImpulseConstraintSolver();
		
		dynamicsWorld = new DiscreteDynamicsWorld(dispatcher, broadPhase, solver, collisionConfiguration);
		dynamicsWorld.setGravity(new Vector3f(0, -10, 0));
	}
	
	/**
	 * 
	 */
	public void initPhysics() {
		
	}
	
	/**
	 * Adds a RigidBody to the physics world.
	 * 
	 * @param rigidBody - RigidBody to add to the physics world.
	 */
	public void addRigidBody(RigidBody rigidBody) {
		this.dynamicsWorld.addRigidBody(rigidBody);
	}
	
	/**
	 * Steps the physics simulation one tick.
	 */
	public void tick() {
		dynamicsWorld.stepSimulation(1/60.f, 10);
	}
}

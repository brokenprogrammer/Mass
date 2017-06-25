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

package me.oskarmendel.mass.entity.masster;

import javax.vecmath.Matrix4f;
import javax.vecmath.Quat4f;
import javax.vecmath.Vector3f;

import com.bulletphysics.collision.shapes.BoxShape;
import com.bulletphysics.collision.shapes.CollisionShape;
import com.bulletphysics.dynamics.RigidBody;
import com.bulletphysics.dynamics.RigidBodyConstructionInfo;
import com.bulletphysics.linearmath.DefaultMotionState;
import com.bulletphysics.linearmath.Transform;

import me.oskarmendel.mass.entity.Entity;
import me.oskarmendel.mass.gfx.Mesh;
import me.oskarmendel.mass.phys.Collidable;
import me.oskarmendel.mass.util.QuatHelper;

/**
 * This class represents the MassterBall used by the Masster.
 *
 * @author Oskar Mendel
 * @version 0.00.00
 * @name MassterBall.java
 */
public class MassterBall extends Entity implements Collidable{

	/**
	 * Shape of the MassterBall entity.
	 */
	private CollisionShape collisionShape;
	
	/**
	 * RigidBody for the MassterBall entity.
	 */
	private RigidBody rigidBody;
	
	/**
	 * MotionState for the MassterBall entity.
	 * Bullet physics uses the MotionState to handle frame 
	 * of simulation.
	 */
	private DefaultMotionState motionState;
	
	/**
	 * The fall innertia for the MassterBall entity.
	 */
	private Vector3f fallInertia;
	
	/**
	 * The mass of the massterBall entity in kilo's.
	 */
	private int mass = 1;
	
    /**
     * Constructs a new MassterBall using the specified Mesh.
     *
     * @param mesh - Mesh instance this MassterBall should use.
     */
    public MassterBall(Mesh mesh) {
        super(mesh);
        
        initPhysics();
    }

    /**
	 * Method to initialize all physics related
	 * values.
	 */
	@Override
	public void initPhysics() {
		motionState = new DefaultMotionState(new Transform(new Matrix4f(new Quat4f(0, 0, 0, 1), new Vector3f(-2, 55, 0), 1.0f)));
		
		// Construct collision shape based on the mesh vertices in the Mesh.
		fallInertia = new Vector3f(0,0,0); 
		collisionShape = new BoxShape(new Vector3f(1, 1, 1));
		collisionShape.calculateLocalInertia(mass,fallInertia);
		
		// Construct the RigidBody.
		RigidBodyConstructionInfo rigidBodyCI = new RigidBodyConstructionInfo(mass, motionState, collisionShape, fallInertia); 
		rigidBody = new RigidBody(rigidBodyCI);
	}
	
	/**
	 * Method to update physics logic for this MassterBall.
	 */
	@Override
	public void updatePhysics() {
		Vector3f v = this.rigidBody.getWorldTransform(new Transform()).origin;
		Quat4f r = new Quat4f(); 
		this.rigidBody.getWorldTransform(new Transform()).getRotation(r);
		
		this.setPosition(v.x, v.y, v.z);
		this.setRotation((float) Math.toDegrees(QuatHelper.getPitch(r)), 
				(float) Math.toDegrees(QuatHelper.getYaw(r)), 
				(float) Math.toDegrees(QuatHelper.getRoll(r)));
	}

	/**
	 * Getter for the MassterBall RigidBody.
	 * 
	 * @return RigidBody of the MassterBall.
	 */
	@Override
	public RigidBody getRigidBody() {
		return this.rigidBody;
	}
}

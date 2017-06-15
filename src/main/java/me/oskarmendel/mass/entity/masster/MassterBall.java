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

import com.bulletphysics.collision.shapes.CollisionShape;
import com.bulletphysics.dynamics.RigidBody;
import com.bulletphysics.dynamics.RigidBodyConstructionInfo;
import com.bulletphysics.linearmath.DefaultMotionState;
import com.bulletphysics.linearmath.Transform;

import me.oskarmendel.mass.entity.Entity;
import me.oskarmendel.mass.gfx.Mesh;
import me.oskarmendel.mass.phys.Collidable;

/**
 * This class represents the MassterBall used by the Masster.
 *
 * @author Oskar Mendel
 * @version 0.00.00
 * @name MassterBall.java
 */
public class MassterBall extends Entity implements Collidable{

	private CollisionShape collisionShape;
	private RigidBody rigidBody;
	
	private DefaultMotionState motionState;
	private Vector3f fallInertia;
	
    /**
     *
     *
     * @param mesh - Mesh instance this MassterBall should use.
     */
    public MassterBall(Mesh mesh) {
        super(mesh);
        
        initPhysics();
    }

	@Override
	public void initPhysics() {
		motionState = new DefaultMotionState(new Transform(new Matrix4f(new Quat4f(0, 0, 0, 1), new Vector3f(0, 50, 0), 1.0f)));
		
		int mass = 1;
		fallInertia = new Vector3f(0,0,0); 
		
		collisionShape.calculateLocalInertia(mass,fallInertia);
		
		RigidBodyConstructionInfo rigidBodyCI = new RigidBodyConstructionInfo(mass, motionState, collisionShape, fallInertia); 
		rigidBody = new RigidBody(rigidBodyCI); 
	}

	@Override
	public RigidBody getRigidBody() {
		return this.rigidBody;
	}
}
